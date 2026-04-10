/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.DetalleProductoIngrediente;
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
 * de los productos en la base de datos utilizando JPA.
 *
 * @author regina, mariana e isaac
 */
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

    /**
     * Guarda un nuevo producto en la base de datos previa validación de duplicado activo.
     *
     * @param producto Producto a persistir.
     * @return Producto guardado con su ID generado.
     * @throws PersistenciaException Si ocurre un error al guardar o si ya existe
     * un producto activo con el mismo nombre.
     */
    @Override
    public Producto guardar(Producto producto) throws PersistenciaException {
        if (producto == null) {
            throw new PersistenciaException("El producto no puede ser nulo.");
        }

        if (existeDuplicadoActivo(producto.getNombre(), null)) {
            throw new PersistenciaException("Ya existe un producto activo con el nombre '" 
                    + producto.getNombre() + "'.");
        }

        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            if (producto.getDetallesIngredientes() != null) {
                for (DetalleProductoIngrediente detalle : producto.getDetallesIngredientes()) {
                    detalle.setProducto(producto);
                }
            }

            em.persist(producto);
            em.getTransaction().commit();
            return producto;

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
     * Edita un producto existente en la base de datos.
     *
     * @param producto Producto con información actualizada.
     * @return Producto actualizado.
     * @throws PersistenciaException Si ocurre un error al editar o si ya existe
     * otro producto activo con el mismo nombre.
     */
    @Override
    public Producto editar(Producto producto) throws PersistenciaException {
        if (producto == null) {
            throw new PersistenciaException("El producto no puede ser nulo.");
        }

        if (producto.getIdProducto() == null) {
            throw new PersistenciaException("El ID del producto es obligatorio para editar.");
        }

        if (existeDuplicadoActivo(producto.getNombre(), producto.getIdProducto())) {
            throw new PersistenciaException("Ya existe otro producto activo con el nombre '" 
                    + producto.getNombre() + "'.");
        }

        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            if (producto.getDetallesIngredientes() != null) {
                for (DetalleProductoIngrediente detalle : producto.getDetallesIngredientes()) {
                    detalle.setProducto(producto);
                }
            }

            Producto actualizado = em.merge(producto);
            em.getTransaction().commit();
            return actualizado;

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
     * Busca un producto por su ID.
     *
     * @param id Identificador único del producto.
     * @return Producto encontrado o null si no existe.
     * @throws PersistenciaException Si ocurre un error en la búsqueda.
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
     * Elimina un producto por su ID.
     *
     * @param id Identificador único del producto.
     * @return true si se eliminó, false si no se encontró.
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
     * Obtiene todos los productos registrados.
     *
     * @return Lista de productos.
     * @throws PersistenciaException Si ocurre un error en la consulta.
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
     * Busca productos por nombre parcial y/o tipo.
     *
     * @param nombre Nombre o parte del nombre del producto.
     * @param tipo Tipo de producto.
     * @return Lista de productos que coinciden con los filtros.
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
     * Verifica si existe otro producto activo con el mismo nombre.
     *
     * @param nombre Nombre del producto.
     * @param idExcluir ID a excluir de la validación en caso de edición.
     * @return true si existe duplicado activo, false en caso contrario.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public boolean existeDuplicadoActivo(String nombre, Long idExcluir) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            StringBuilder jpql = new StringBuilder(
                    "SELECT COUNT(p) FROM Producto p "
                    + "WHERE LOWER(p.nombre) = :nombre "
                    + "AND p.activo = true"
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
            throw new PersistenciaException("Error al validar duplicado activo del producto.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Cambia el estado activo/inactivo de un producto.
     *
     * @param idProducto ID del producto.
     * @param activo Nuevo estado activo.
     * @throws PersistenciaException Si ocurre un error en la actualización.
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
     * Actualiza la disponibilidad de un producto.
     *
     * @param idProducto ID del producto.
     * @param disponibilidad Nueva disponibilidad.
     * @throws PersistenciaException Si ocurre un error en la actualización.
     */
    @Override
    public void actualizarDisponibilidad(Long idProducto, DisponibilidadProducto disponibilidad) throws PersistenciaException {
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
