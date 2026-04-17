/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import interfaces.IClienteDAO;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * Clase que implementa el patrón DAO para gestionar la persistencia de la entidad Cliente.
 * Utiliza JPA para interactuar con la base de datos y sigue el patrón Singleton.
 * @author regina, mariana e isaac
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

    /**
     * Guarda un nuevo cliente en la base de datos.
     * @param cliente El objeto cliente a persistir.
     * @return El cliente guardado.
     * @throws PersistenciaException Si ocurre un error durante la transacción.
     */
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

    /**
     * Busca un cliente por su identificador único.
     * @param id Identificador del cliente.
     * @return El cliente encontrado o null si no existe.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
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

    /**
     * Actualiza la información de un cliente existente.
     * @param cliente Cliente con los datos actualizados.
     * @return El cliente actualizado.
     * @throws PersistenciaException Si ocurre un error al intentar actualizar.
     */
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

    /**
     * Elimina a un cliente de la base de datos mediante su ID.
     * @param id Identificador del cliente a eliminar.
     * @return true si se eliminó con éxito, false si el cliente no existía.
     * @throws PersistenciaException Si ocurre un error durante la eliminación.
     */
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

    /**
     * Busca clientes basándose en un filtro de texto.
     * Realiza la búsqueda en nombre, apellidos y correo electrónico.
     * @param filtro Cadena de texto a buscar.
     * @return Lista de clientes que coinciden con el criterio.
     * @throws PersistenciaException Si ocurre un error al construir o ejecutar la consulta Criteria.
     */
    @Override
    public List<Cliente> buscarPorFiltros(String filtro) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
            Root<Cliente> c = cq.from(Cliente.class);
            String f = "%" + (filtro == null ? "" : filtro.trim().toLowerCase()) + "%";

            Expression<String> nombreCompleto = cb.concat(cb.concat(c.get("nombre"), " "), 
                                                cb.concat(cb.concat(c.get("apellidoPaterno"), " "), 
                                                c.get("apellidoMaterno")));

            cq.where(cb.or(
                cb.like(cb.lower(c.get("nombre")), f),
                cb.like(cb.lower(c.get("apellidoPaterno")), f),
                cb.like(cb.lower(c.get("apellidoMaterno")), f),
                cb.like(cb.lower(nombreCompleto), f),
                cb.like(cb.lower(cb.coalesce(c.get("correoElectronico"), "")), f)
            ));

            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error en búsqueda por filtro", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Recupera todos los clientes registrados en la base de datos.
     * @return Lista con todos los clientes.
     * @throws PersistenciaException Si ocurre un error en la consulta JPQL.
     */
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
    
    /**
     * Gestiona la existencia de un "Cliente General" para ventas rápidas.
     * Si no existe, lo crea; si ya existe, lo retorna.
     * @return Objeto Cliente que representa al cliente genérico.
     * @throws PersistenciaException Si ocurre un error al verificar o persistir el cliente.
     */
    @Override
    public Cliente registrarClienteGeneral() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            String jpql = "SELECT c FROM Cliente c WHERE c.nombre = :nombre";
            List<Cliente> existentes = em.createQuery(jpql, Cliente.class).setParameter("nombre", "Cliente General").getResultList();

            if (!existentes.isEmpty()) {
                return existentes.get(0);
            }
            ClienteFrecuente clienteG = new ClienteFrecuente();
            clienteG.setNombre("Cliente General");
            clienteG.setApellidoPaterno(""); 
            clienteG.setApellidoMaterno("");
            clienteG.setTelefono("");
            clienteG.setCorreoElectronico("");
            clienteG.setFechaRegistro(LocalDateTime.now());

            em.getTransaction().begin();
            em.persist(clienteG);
            em.getTransaction().commit();

            return clienteG;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al gestionar el Cliente General", e);
        } finally {
            em.close();
        }
    }

}
