/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.ClienteDAO;
import dtos.ClienteDTO;
import entidades.Cliente;
import excepciones.PersistenciaException;
import interfaces.IClienteBO;
import interfaces.IClienteDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa la lógica de negocio de la entidad Cliente. Esta clase se encarga
 * de validar la información, convertir entre DTO y entidad, y delegar las
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
     * @throws Exception Si ocurre un error durante el guardado.
     */
    @Override
    public ClienteDTO guardar(ClienteDTO clienteDTO) throws Exception {
        validarDatos(clienteDTO);

        try {
            Cliente cliente = convertirEntidad(clienteDTO);
            Cliente clienteGuardado = clienteDAO.guardar(cliente);
            return convertirDTO(clienteGuardado);
        } catch (PersistenciaException e) {
            throw new Exception("Error al guardar el cliente en negocio.", e);
        }
    }

    /**
     * Busca un cliente por su identificador único.
     *
     * @param id Identificador único del cliente.
     * @return Cliente encontrado.
     * @throws Exception Si ocurre un error durante la búsqueda.
     */
    @Override
    public ClienteDTO buscarPorId(Long id) throws Exception {
        if (id == null) {
            throw new Exception("El id del cliente es obligatorio.");
        }

        try {
            Cliente cliente = clienteDAO.buscarPorId(id);

            if (cliente == null) {
                throw new Exception("No se encontró el cliente.");
            }

            return convertirDTO(cliente);
        } catch (PersistenciaException e) {
            throw new Exception("Error al buscar el cliente en negocio.", e);
        }
    }

    /**
     * Edita la información de un cliente existente.
     *
     * @param clienteDTO DTO con la información actualizada del cliente.
     * @return Cliente actualizado.
     * @throws Exception Si ocurre un error durante la edición.
     */
    @Override
    public ClienteDTO editar(ClienteDTO clienteDTO) throws Exception {
        if (clienteDTO == null) {
            throw new Exception("El cliente no puede ser nulo.");
        }

        if (clienteDTO.getIdCliente() == null) {
            throw new Exception("El id del cliente es obligatorio para editar.");
        }

        validarDatos(clienteDTO);

        try {
            Cliente cliente = convertirEntidad(clienteDTO);
            Cliente clienteEditado = clienteDAO.editar(cliente);
            return convertirDTO(clienteEditado);
        } catch (PersistenciaException e) {
            throw new Exception("Error al editar el cliente en negocio.", e);
        }
    }

    /**
     * Elimina un cliente del sistema.
     *
     * @param id Identificador único del cliente a eliminar.
     * @return true si el cliente fue eliminado correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre un error durante la eliminación.
     */
    @Override
    public boolean eliminar(Long id) throws Exception {
        if (id == null) {
            throw new Exception("El id del cliente es obligatorio.");
        }

        try {
            return clienteDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new Exception("Error al eliminar el cliente en negocio.", e);
        }
    }

    /**
     * Busca clientes que coincidan con el filtro proporcionado.
     *
     * @param filtro Texto de búsqueda para filtrar clientes.
     * @return Lista de clientes que coinciden con el filtro.
     * @throws Exception Si ocurre un error durante la búsqueda.
     */
    @Override
    public List<ClienteDTO> buscarPorFiltros(String filtro) throws Exception {
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
            throw new Exception("Error al buscar clientes por filtros en negocio.", e);
        }
    }

    /**
     * Valida los datos obligatorios de un cliente.
     *
     * @param clienteDTO DTO del cliente a validar.
     * @throws Exception Si falta algún dato obligatorio.
     */
    private void validarDatos(ClienteDTO clienteDTO) throws Exception {
        if (clienteDTO == null) {
            throw new Exception("Cliente no puede ser nulo.");
        }

        if (clienteDTO.getNombre() == null || clienteDTO.getNombre().trim().isEmpty()) {
            throw new Exception("Nombre es obligatorio.");
        }

        if (clienteDTO.getApellidoPaterno() == null || clienteDTO.getApellidoPaterno().trim().isEmpty()) {
            throw new Exception("Apellido paterno es obligatorio.");
        }

        if (clienteDTO.getApellidoMaterno() == null || clienteDTO.getApellidoMaterno().trim().isEmpty()) {
            throw new Exception("Apellido materno es obligatorio.");
        }

        if (clienteDTO.getTelefono() == null || clienteDTO.getTelefono().trim().isEmpty()) {
            throw new Exception("Telefono es obligatorio.");
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
