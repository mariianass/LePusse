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
import java.util.List;
import javax.persistence.EntityManager;

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
    public Ingrediente buscarPorId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Ingrediente> buscarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Ingrediente> buscarPorNombre(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Ingrediente> buscarPorUnidad(UnidadMedida unidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
}
