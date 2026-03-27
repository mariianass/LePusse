/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.ClienteDAO;
import dtos.ClienteDTO;
import entidades.Cliente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IClienteBO;
import interfaces.IClienteDAO;
import java.util.ArrayList;
import java.util.List;
import static validadores.ValidadorCliente.validar;

/**
 * Implementa la lógica de negocio de la entidad Cliente. Esta clase se encarga
 * de convertir entre DTO y entidad, y delegar las
 * operaciones correspondientes a la capa de persistencia.
 *
 * @author regina, mariana e isaac.
 */
public class ClienteBO implements IClienteBO {

    private static ClienteBO instanciaClienteBO;
    private IClienteDAO clienteDAO;

    /**
     * Constructor privado de ClienteBO.
     */
    private ClienteBO() {
        this.clienteDAO = ClienteDAO.getInstance();
    }

    /**
     * Obtiene la instancia única de ClienteBO.
     *
     * @return Instancia única de ClienteBO.
     */
    public static ClienteBO getInstance() {
        if (instanciaClienteBO == null) {
            instanciaClienteBO = new ClienteBO();
        }
        return instanciaClienteBO;
    }

    /**
     * Guarda un nuevo cliente en el sistema.
     *
     * @param clienteDTO DTO con la información del cliente.
     * @return Cliente guardado.
     * @throws NegocioException Si ocurre un error durante el guardado.
     */
    @Override
    public ClienteDTO guardar(ClienteDTO clienteDTO) throws NegocioException {
        validar(clienteDTO);

        try {
            Cliente cliente = convertirEntidad(clienteDTO);
            Cliente clienteGuardado = clienteDAO.guardar(cliente);
            return convertirDTO(clienteGuardado);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al guardar el cliente en negocio.", e);
        }
    }

    /**
     * Busca un cliente por su identificador único.
     *
     * @param id Identificador único del cliente.
     * @return Cliente encontrado.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    @Override
    public ClienteDTO buscarPorId(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El id del cliente es obligatorio.");
        }

        try {
            Cliente cliente = clienteDAO.buscarPorId(id);

            if (cliente == null) {
                throw new NegocioException("No se encontró el cliente.");
            }

            return convertirDTO(cliente);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar el cliente en negocio.", e);
        }
    }

    /**
     * Edita la información de un cliente existente.
     *
     * @param clienteDTO DTO con la información actualizada del cliente.
     * @return Cliente actualizado.
     * @throws NegocioException Si ocurre un error durante la edición.
     */
    @Override
    public ClienteDTO editar(ClienteDTO clienteDTO) throws NegocioException {
        if (clienteDTO == null) {
            throw new NegocioException("El cliente no puede ser nulo.");
        }

        if (clienteDTO.getIdCliente() == null) {
            throw new NegocioException("El id del cliente es obligatorio para editar.");
        }

        validar(clienteDTO);

        try {
            Cliente cliente = convertirEntidad(clienteDTO);
            Cliente clienteEditado = clienteDAO.editar(cliente);
            return convertirDTO(clienteEditado);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al editar el cliente en negocio.", e);
        }
    }

    /**
     * Elimina un cliente del sistema.
     *
     * @param id Identificador único del cliente a eliminar.
     * @return true si el cliente fue eliminado correctamente, false en caso
     * contrario.
     * @throws NegocioException Si ocurre un error durante la eliminación.
     */
    @Override
    public boolean eliminar(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El id del cliente es obligatorio.");
        }

        try {
            return clienteDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al eliminar el cliente en negocio.", e);
        }
    }

    /**
     * Busca clientes que coincidan con el filtro proporcionado.
     *
     * @param filtro Texto de búsqueda para filtrar clientes.
     * @return Lista de clientes que coinciden con el filtro.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    @Override
    public List<ClienteDTO> buscarPorFiltros(String filtro) throws NegocioException {
        try {
            List<Cliente> clientes = clienteDAO.buscarPorFiltros(filtro);
            List<ClienteDTO> clientesDTO = new ArrayList<>();

            if (clientes != null) {
                for (Cliente cliente : clientes) {
                    clientesDTO.add(convertirDTO(cliente));
                }
            }

            return clientesDTO;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar clientes por filtros en negocio.", e);
        }
    }

    /**
     * Convierte un DTO de Cliente a su representación en entidad.
     *
     * @param clienteDTO DTO del cliente.
     * @return Entidad cliente.
     */
    private Cliente convertirEntidad(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        }

        return new Cliente(
                clienteDTO.getIdCliente(),
                clienteDTO.getNombre(),
                clienteDTO.getApellidoPaterno(),
                clienteDTO.getApellidoMaterno(),
                clienteDTO.getTelefono(),
                clienteDTO.getCorreoElectronico()
        );
    }

    /**
     * Convierte una entidad Cliente a su representación en DTO.
     *
     * @param cliente Entidad cliente.
     * @return DTO del cliente.
     */
    private ClienteDTO convertirDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellidoPaterno(),
                cliente.getApellidoMaterno(),
                cliente.getTelefono(),
                cliente.getCorreoElectronico()
        );
    }
}
