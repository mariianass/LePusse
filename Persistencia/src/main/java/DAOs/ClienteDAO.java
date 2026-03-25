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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cliente editar(Cliente cliente) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(Long id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Cliente> buscarPorFiltros(String filtro) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
