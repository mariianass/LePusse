/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.Cliente;
import excepciones.PersistenciaException;
import interfaces.IClienteDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author regin
 */
public class ClienteDAO implements IClienteDAO {
    
    private static ClienteDAO instanciaCliente;
    
    private ClienteDAO(){
        
    }
    
    public static ClienteDAO getInstance(){
        if(instanciaCliente == null){
            instanciaCliente = new ClienteDAO();
        }
        return instanciaCliente;
    }


    @Override
    public Cliente guardar(Cliente cliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return cliente;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al guardar el cliente en la BD", e);
        } finally {
            em.close();
        }

    }

    @Override
    public Cliente buscarPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        
        try {
            return em.find(Cliente.class, id);
        } catch (Exception e){
            throw new PersistenciaException("No se encontró el cliente en la base de datos. "+e.getMessage());
        } finally {
            em.close();
        }
        
    }

    @Override
    public Cliente editar(Cliente cliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();
            Cliente clienteActualizado = em.merge(cliente);
            em.getTransaction().commit();
            return clienteActualizado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al editar el cliente en la base de datos.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminar(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            Cliente cliente = em.find(Cliente.class, id);

            if (cliente == null) {
                return false;
            }

            em.getTransaction().begin();
            em.remove(cliente);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al eliminar el cliente de la base de datos.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> buscarPorFiltros(String filtro) throws PersistenciaException {
        
        EntityManager em =  ConexionBD.crearConexion();
        
        try {
            
            String jpql = " SELECT c FROM Cliente c "
                    + "WHERE LOWER(c.nombre) LIKE LOWER(:filtro) "
                    + "OR LOWER(c.apellidoPaterno) LIKE LOWER(:filtro) "
                    + "OR LOWER(c.apellidoMaterno) LIKE LOWER(:filtro) "
                    + "OR LOWER(CONCAT(CONCAT(CONCAT(CONCAT(c.nombre, ' '), c.apellidoPaterno), ' '), c.apellidoMaterno)) LIKE LOWER(:filtro) "
                    + "OR LOWER(c.telefono) LIKE LOWER(:filtro) "
                    + "OR LOWER(c.correoElectronico) LIKE LOWER(:filtro) ";
            
            TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
            query.setParameter("filtro", "%" + filtro.trim() + "%");
            
            return query.getResultList();
            
        } catch (Exception e){
            throw new PersistenciaException("Error al buscar clientes por filtro, "+e);
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public List<Cliente> obtenerTodos() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            String jpql = "SELECT c FROM Cliente c";
            TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener todos los clientes.", e);
        } finally {
            em.close();
        }
    }

}
