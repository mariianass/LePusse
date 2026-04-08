/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.Ingrediente;
import enums.UnidadMedida;
import excepciones.PersistenciaException;
import interfaces.IIngredienteDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Clase que implementa la interfaz IIngredienteDAO para gestionar la persistencia
 * de los ingredientes en la base de datos utilizando JPA.
 * @author regina, mariana e isaac
 */
public class IngredienteDAO implements IIngredienteDAO {
    
    private static IngredienteDAO instanciaIngrediente;
    
    private IngredienteDAO(){
        
    }
    
    public static IngredienteDAO getInstance(){
        if(instanciaIngrediente == null){
            instanciaIngrediente = new IngredienteDAO();
        }
        return instanciaIngrediente;
    }

    /**
     * Guarda un nuevo ingrediente en la base de datos previa validación de duplicados.
     * @param ingrediente El objeto ingrediente a persistir.
     * @return El ingrediente guardado con su ID generado.
     * @throws PersistenciaException Si ya existe un ingrediente con el mismo nombre 
     * y unidad, o si ocurre un error en la transacción.
     */
    @Override
    public Ingrediente guardar(Ingrediente ingrediente) throws PersistenciaException {
        if (ingrediente.getIdIngrediente() == null) {
            if (existeDuplicado(ingrediente.getNombre(), ingrediente.getUnidadMedida(), null)) {
                throw new PersistenciaException("No se puede registrar: Ya existe el ingrediente " + ingrediente.getNombre() + " con la unidad " + ingrediente.getUnidadMedida());
            }
        }
        
        EntityManager em = ConexionBD.crearConexion();
        
        try {
            em.getTransaction().begin();
            em.persist(ingrediente);
            em.getTransaction().commit();
            
            return ingrediente;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al guardar el ingrediente en la BD", e);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un ingrediente de la base de datos por su ID.
     * @param id Identificador del ingrediente.
     * @return true si fue eliminado, false si no se encontró el registro.
     * @throws PersistenciaException Si ocurre un error durante la eliminación.
     */
    @Override
    public boolean eliminar(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            Ingrediente ingrediente = em.find(Ingrediente.class, id);

            if (ingrediente == null) {
                return false;
            }

            em.getTransaction().begin();
            em.remove(ingrediente);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al eliminar el ingrediente de la base de datos.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca un ingrediente por su identificador único.
     * @param id Identificador del ingrediente.
     * @return El ingrediente encontrado o null si no existe.
     * @throws PersistenciaException Si el ID es nulo o hay error de conexión.
     */
    @Override
    public Ingrediente buscarPorId(Long id) throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("El id del ingrediente no puede ser nulo.");
        }

        EntityManager em = ConexionBD.crearConexion();

        try {
            return em.find(Ingrediente.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar el ingrediente por ID.", e);
        } finally {
            em.close();
        }
    }


    /**
     * Verifica si existe un ingrediente con el mismo nombre y unidad.
     * @param nombre Nombre a buscar.
     * @param unidad Unidad de medida a buscar.
     * @param id ID del ingrediente actual (para excluirlo en caso de edición).
     * @return true si existe un duplicado, false en caso contrario.
     * @throws PersistenciaException Si ocurre un error en la consulta JPQL.
     */
    @Override
    public boolean existeDuplicado(String nombre, UnidadMedida unidad, Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            StringBuilder jpql = new StringBuilder("SELECT COUNT(i) FROM Ingrediente i WHERE i.nombre = :nombre AND i.unidadMedida = :unidad");
            if (id != null) {
                jpql.append(" AND i.idIngrediente != :id");
            }
            var query = em.createQuery(jpql.toString(), Long.class)
                          .setParameter("nombre", nombre)
                          .setParameter("unidad", unidad);
            if (id != null) {
                query.setParameter("id", id);
            }
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar duplicados en la base de datos", e);
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza únicamente el stock de un ingrediente mediante una consulta directa.
     * @param id Identificador del ingrediente.
     * @param nuevoStock Nuevo valor para el stock actual.
     * @throws PersistenciaException Si ocurre un error en la actualización masiva.
     */
    @Override
    public void actualizarStock(Long id, Double nuevoStock) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            String jpql = "UPDATE Ingrediente i SET i.stockActual = :nuevoStock WHERE i.idIngrediente = :id";

            em.createQuery(jpql)
              .setParameter("nuevoStock", nuevoStock)
              .setParameter("id", id)
              .executeUpdate();

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al actualizar el stock del ingrediente.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca ingredientes aplicando filtros dinámicos de nombre y unidad.
     * @param nombre Filtro de nombre (parcial).
     * @param unidad Filtro por unidad de medida.
     * @return Lista de ingredientes que coinciden con los criterios.
     * @throws PersistenciaException Si ocurre un error en la Criteria Query.
     */
    @Override
    public List<Ingrediente> buscarPorNombreYUnidad(String nombre, UnidadMedida unidad) throws PersistenciaException {

        EntityManager em = ConexionBD.crearConexion();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Ingrediente> cq = cb.createQuery(Ingrediente.class);
            Root<Ingrediente> ingrediente = cq.from(Ingrediente.class);

            List<Predicate> filtros = new ArrayList<>();

            if (nombre != null && !nombre.isBlank()) {
                filtros.add(cb.like(
                        cb.lower(ingrediente.get("nombre")),
                        "%" + nombre.toLowerCase() + "%"
                ));
            }

            if (unidad != null) {
                filtros.add(cb.equal(ingrediente.get("unidadMedida"), unidad));
            }

            cq.select(ingrediente);

            if (!filtros.isEmpty()) {
                cq.where(cb.and(filtros.toArray(new Predicate[0])));
            }

            return em.createQuery(cq).getResultList();

        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar ingredientes por nombre y unidad.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza todos los datos de un ingrediente existente.
     * @param ingrediente El objeto con los nuevos datos.
     * @return El ingrediente actualizado.
     * @throws PersistenciaException Si los cambios generan conflicto de duplicidad 
     * con otro ingrediente existente.
     */
    @Override
    public Ingrediente editar(Ingrediente ingrediente) throws PersistenciaException {
        if (existeDuplicado(ingrediente.getNombre(), ingrediente.getUnidadMedida(), ingrediente.getIdIngrediente())) {
            throw new PersistenciaException("No se puede actualizar: Ya existe el ingrediente '" 
                    + ingrediente.getNombre() + "' con la unidad " + ingrediente.getUnidadMedida());
        }

        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            Ingrediente ingredienteActualizado = em.merge(ingrediente);

            em.getTransaction().commit();
            return ingredienteActualizado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al intentar editar el ingrediente.", e);
        } finally {
            em.close();
        }
    }
    
}
