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

/**
 * Clase que implementa la interfaz IProductoDAO para gestionar la persistencia 
 * de la entidad Producto en la base de datos utilizando JPA.
 * 
 * Implementa el patrón Singleton para garantizar una única instancia del DAO.
 * 
 * @author regina, mariana e isaac
 */
public class ProductoDAO implements IProductoDAO {

    /** Instancia única de la clase ProductoDAO. */
    private static ProductoDAO instanciaProducto;

    /**
     * Constructor privado para evitar la instanciación externa (Patrón Singleton).
     */
    private ProductoDAO() {
    }

    /**
     * Obtiene la instancia única de ProductoDAO.
     * * @return La instancia única de ProductoDAO.
     */
    public static ProductoDAO getInstance() {
        if (instanciaProducto == null) {
            instanciaProducto = new ProductoDAO();
        }
        return instanciaProducto;
    }

    /**
     * Guarda un nuevo producto en la base de datos.
     * Valida que el nombre no esté duplicado y vincula correctamente los 
     * ingredientes referenciados en los detalles del producto.
     * * @param producto El producto a guardar.
     * @return El producto persistido con su ID generado.
     * @throws PersistenciaException Si el producto es nulo, el nombre ya existe 
     * o ocurre un error en la transacción.
     */
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

    /**
     * Actualiza los datos de un producto existente.
     * * @param producto El producto con los datos actualizados.
     * @return El producto actualizado.
     * @throws PersistenciaException Si el producto o su ID son nulos, si el nombre 
     * coincide con otro producto existente o por errores de base de datos.
     */
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

    /**
     * Busca un producto por su identificador único.
     * * @param id Identificador del producto.
     * @return El objeto Producto encontrado o null si no existe.
     * @throws PersistenciaException Si el ID es nulo o hay un error en la consulta.
     */
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

    /**
     * Elimina un producto de la base de datos por su ID.
     * * @param id Identificador del producto a eliminar.
     * @return true si se eliminó con éxito, false si el producto no existía.
     * @throws PersistenciaException Si ocurre un error durante la eliminación.
     */
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

    /**
     * Obtiene una lista de todos los productos registrados, ordenados por nombre.
     * * @return Lista de productos.
     * @throws PersistenciaException Si ocurre un error en la consulta JPQL.
     */
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

    /**
     * Busca productos filtrando opcionalmente por nombre y tipo.
     * Utiliza la API de Criteria para construir la consulta dinámica.
     * * @param nombre Filtro parcial del nombre (insensible a mayúsculas).
     * @param tipo El TipoProducto a filtrar.
     * @return Lista de productos que coinciden con los criterios.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
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

    /**
     * Verifica si existe un producto con el nombre proporcionado, excluyendo un ID específico.
     * * @param nombre Nombre a validar.
     * @param idExcluir ID que se debe omitir en la búsqueda (útil en ediciones).
     * @return true si ya existe un duplicado, false en caso contrario.
     * @throws PersistenciaException Si el nombre es inválido o falla la consulta.
     */
    @Override
    public boolean existeDuplicadoActivo(String nombre, Long idExcluir) throws PersistenciaException {
        return existeDuplicadoPorNombre(nombre, idExcluir);
    }

    /**
     * Método privado para centralizar la lógica de validación de nombres duplicados.
     * * @param nombre Nombre a verificar.
     * @param idExcluir ID a ignorar (opcional).
     * @return true si hay coincidencia, false de lo contrario.
     * @throws PersistenciaException Si hay errores de validación o persistencia.
     */
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

    /**
     * Cambia el estado de activación de un producto mediante una actualización masiva.
     * * @param idProducto ID del producto a modificar.
     * @param activo Nuevo estado (true para activo, false para inactivo).
     * @throws PersistenciaException Si ocurre un error al ejecutar el UPDATE.
     */
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

    /**
     * Actualiza el nivel de disponibilidad de un producto específico.
     * * @param idProducto ID del producto.
     * @param disponibilidad Nuevo valor del enum DisponibilidadProducto.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
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