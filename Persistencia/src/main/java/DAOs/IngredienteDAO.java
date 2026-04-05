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
 *
 * @author regin
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

    @Override
    public void guardar(Ingrediente ingrediente) throws PersistenciaException {
        if (ingrediente.getIdIngrediente() == null) {
            if (existeDuplicado(ingrediente.getNombre(), ingrediente.getUnidadMedida())) {
                throw new PersistenciaException("No se puede registrar: Ya existe el ingrediente " + ingrediente.getNombre() + " con la unidad " + ingrediente.getUnidadMedida());
            }
        }
        
        EntityManager em = ConexionBD.crearConexion();
        
        try {
            em.getTransaction().begin();
            em.persist(ingrediente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al guardar el ingrediente en la BD", e);
        } finally {
            em.close();
        }
    }

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



    @Override
    public boolean existeDuplicado(String nombre, UnidadMedida unidad) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT COUNT(i) FROM Ingrediente i WHERE i.nombre = :nombre AND i.unidadMedida = :unidad";

            Long conteo = em.createQuery(jpql, Long.class)
                    .setParameter("nombre", nombre)
                    .setParameter("unidad", unidad)
                    .getSingleResult();

            return conteo > 0;

        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar la base de datos", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarStock(Long id, Double nuevoStock) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

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
    
}
