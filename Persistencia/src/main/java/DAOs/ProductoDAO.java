package DAOs;

import conexion.ConexionBD;
import entidades.DetalleProductoIngrediente;
import entidades.Ingrediente;
import entidades.Producto;
import enums.DisponibilidadProducto;
import enums.TipoProducto;
import excepciones.PersistenciaException;
import interfaces.IProductoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductoDAO implements IProductoDAO {

    private static ProductoDAO instanciaProducto;

    private ProductoDAO() {
    }

    public static ProductoDAO getInstance() {
        if (instanciaProducto == null) {
            instanciaProducto = new ProductoDAO();
        }
        return instanciaProducto;
    }

    @Override
    public Producto guardar(Producto producto) throws PersistenciaException {
        if (producto == null) {
            throw new PersistenciaException("El producto no puede ser nulo.");
        }

        if (existeDuplicadoPorNombre(producto.getNombre(), null)) {
            throw new PersistenciaException("Ya existe un producto con el nombre '"
                    + producto.getNombre() + "'.");
        }

        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            if (producto.getDetallesIngredientes() != null) {
                for (DetalleProductoIngrediente detalle : producto.getDetallesIngredientes()) {
                    detalle.setProducto(producto);

                    if (detalle.getIngrediente() == null
                            || detalle.getIngrediente().getIdIngrediente() == null) {
                        throw new PersistenciaException("Cada detalle debe tener un ingrediente válido.");
                    }

                    Ingrediente ingredienteRef = em.getReference(
                            Ingrediente.class,
                            detalle.getIngrediente().getIdIngrediente()
                    );
                    detalle.setIngrediente(ingredienteRef);
                }
            }

            em.persist(producto);
            em.getTransaction().commit();
            return producto;

        } catch (PersistenciaException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al guardar el producto en la base de datos.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Producto editar(Producto producto) throws PersistenciaException {
        if (producto == null) {
            throw new PersistenciaException("El producto no puede ser nulo.");
        }

        if (producto.getIdProducto() == null) {
            throw new PersistenciaException("El ID del producto es obligatorio para editar.");
        }

        if (existeDuplicadoPorNombre(producto.getNombre(), producto.getIdProducto())) {
            throw new PersistenciaException("Ya existe otro producto con el nombre '"
                    + producto.getNombre() + "'.");
        }

        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            if (producto.getDetallesIngredientes() != null) {
                for (DetalleProductoIngrediente detalle : producto.getDetallesIngredientes()) {
                    detalle.setProducto(producto);

                    if (detalle.getIngrediente() == null
                            || detalle.getIngrediente().getIdIngrediente() == null) {
                        throw new PersistenciaException("Cada detalle debe tener un ingrediente válido.");
                    }

                    Ingrediente ingredienteRef = em.getReference(
                            Ingrediente.class,
                            detalle.getIngrediente().getIdIngrediente()
                    );
                    detalle.setIngrediente(ingredienteRef);
                }
            }

            Producto actualizado = em.merge(producto);
            em.getTransaction().commit();
            return actualizado;

        } catch (PersistenciaException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al editar el producto en la base de datos.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Producto buscarPorId(Long id) throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("El id del producto no puede ser nulo.");
        }

        EntityManager em = ConexionBD.crearConexion();

        try {
            return em.find(Producto.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar el producto por ID.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminar(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            Producto producto = em.find(Producto.class, id);

            if (producto == null) {
                return false;
            }

            em.getTransaction().begin();
            em.remove(producto);
            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al eliminar el producto de la base de datos.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> obtenerTodos() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            String jpql = "SELECT p FROM Producto p ORDER BY p.nombre ASC";
            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener todos los productos.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> buscarPorNombreYTipo(String nombre, TipoProducto tipo) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Producto> cq = cb.createQuery(Producto.class);
            Root<Producto> producto = cq.from(Producto.class);

            List<Predicate> filtros = new ArrayList<>();

            if (nombre != null && !nombre.isBlank()) {
                filtros.add(cb.like(
                        cb.lower(producto.get("nombre")),
                        "%" + nombre.toLowerCase() + "%"
                ));
            }

            if (tipo != null) {
                filtros.add(cb.equal(producto.get("tipo"), tipo));
            }

            cq.select(producto);

            if (!filtros.isEmpty()) {
                cq.where(cb.and(filtros.toArray(new Predicate[0])));
            }

            cq.orderBy(cb.asc(producto.get("nombre")));

            return em.createQuery(cq).getResultList();

        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar productos por nombre y tipo.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existeDuplicadoActivo(String nombre, Long idExcluir) throws PersistenciaException {
        return existeDuplicadoPorNombre(nombre, idExcluir);
    }

    private boolean existeDuplicadoPorNombre(String nombre, Long idExcluir) throws PersistenciaException {
        if (nombre == null || nombre.isBlank()) {
            throw new PersistenciaException("El nombre del producto es obligatorio para validar duplicados.");
        }

        EntityManager em = ConexionBD.crearConexion();

        try {
            StringBuilder jpql = new StringBuilder(
                    "SELECT COUNT(p) FROM Producto p WHERE LOWER(p.nombre) = :nombre"
            );

            if (idExcluir != null) {
                jpql.append(" AND p.idProducto != :idExcluir");
            }

            TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);
            query.setParameter("nombre", nombre.trim().toLowerCase());

            if (idExcluir != null) {
                query.setParameter("idExcluir", idExcluir);
            }

            return query.getSingleResult() > 0;

        } catch (Exception e) {
            throw new PersistenciaException("Error al validar duplicado del producto.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void cambiarEstadoActivo(Long idProducto, Boolean activo) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            String jpql = "UPDATE Producto p SET p.activo = :activo WHERE p.idProducto = :idProducto";

            em.createQuery(jpql)
                    .setParameter("activo", activo)
                    .setParameter("idProducto", idProducto)
                    .executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al cambiar el estado del producto.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarDisponibilidad(Long idProducto, DisponibilidadProducto disponibilidad)
            throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            String jpql = "UPDATE Producto p SET p.disponibilidad = :disponibilidad "
                    + "WHERE p.idProducto = :idProducto";

            em.createQuery(jpql)
                    .setParameter("disponibilidad", disponibilidad)
                    .setParameter("idProducto", idProducto)
                    .executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al actualizar la disponibilidad del producto.", e);
        } finally {
            em.close();
        }
    }
}