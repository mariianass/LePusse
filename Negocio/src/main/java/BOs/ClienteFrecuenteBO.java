package BOs;

import DAOs.ClienteDAO;
import dtos.ClienteFrecuenteDTO;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import interfaces.IClienteDAO;
import interfaces.IClienteFrecuenteBO;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa la lógica de negocio de la entidad ClienteFrecuente. Esta clase se
 * encarga de validar la información, convertir entre DTO y entidad, y delegar
 * las operaciones correspondientes a la capa de persistencia.
 *
 * @author regina, mariana e isaac.
 */
public class ClienteFrecuenteBO implements IClienteFrecuenteBO {

    private static ClienteFrecuenteBO instanciaClienteFrecuenteBO;
    private IClienteDAO clienteDAO;

    /**
     * Constructor privado de ClienteFrecuenteBO.
     */
    private ClienteFrecuenteBO() {
        this.clienteDAO = ClienteDAO.getInstance();
    }

    /**
     * Obtiene la instancia única de ClienteFrecuenteBO.
     *
     * @return Instancia única de ClienteFrecuenteBO.
     */
    public static ClienteFrecuenteBO getInstance() {
        if (instanciaClienteFrecuenteBO == null) {
            instanciaClienteFrecuenteBO = new ClienteFrecuenteBO();
        }
        return instanciaClienteFrecuenteBO;
    }

    /**
     * Guarda un nuevo cliente frecuente en el sistema.
     *
     * @param clienteFrecuenteDTO DTO con la información del cliente frecuente.
     * @return Cliente frecuente guardado.
     * @throws Exception Si ocurre un error durante el guardado.
     */
    @Override
    public ClienteFrecuenteDTO guardar(ClienteFrecuenteDTO clienteFrecuenteDTO) throws Exception {
        validarDatos(clienteFrecuenteDTO);

        try {
            ClienteFrecuente clienteFrecuente = convertirEntidad(clienteFrecuenteDTO);
            Cliente clienteGuardado = clienteDAO.guardar(clienteFrecuente);

            if (!(clienteGuardado instanceof ClienteFrecuente)) {
                throw new Exception("El cliente guardado no corresponde a un cliente frecuente.");
            }

            return convertirDTO((ClienteFrecuente) clienteGuardado);
        } catch (PersistenciaException e) {
            throw new Exception("Error al guardar el cliente frecuente en negocio.", e);
        }
    }

    /**
     * Busca un cliente frecuente por su identificador único.
     *
     * @param id Identificador único del cliente frecuente.
     * @return Cliente frecuente encontrado.
     * @throws Exception Si ocurre un error durante la búsqueda.
     */
    @Override
    public ClienteFrecuenteDTO buscarPorId(Long id) throws Exception {
        if (id == null) {
            throw new Exception("El id del cliente frecuente es obligatorio.");
        }

        try {
            Cliente cliente = clienteDAO.buscarPorId(id);

            if (cliente == null) {
                throw new Exception("No se encontró el cliente frecuente.");
            }

            if (!(cliente instanceof ClienteFrecuente)) {
                throw new Exception("El cliente encontrado no es un cliente frecuente.");
            }

            return convertirDTO((ClienteFrecuente) cliente);
        } catch (PersistenciaException e) {
            throw new Exception("Error al buscar el cliente frecuente en negocio.", e);
        }
    }

    /**
     * Edita la información de un cliente frecuente existente.
     *
     * @param clienteFrecuenteDTO DTO con la información actualizada del cliente
     * frecuente.
     * @return Cliente frecuente actualizado.
     * @throws Exception Si ocurre un error durante la edición.
     */
    @Override
    public ClienteFrecuenteDTO editar(ClienteFrecuenteDTO clienteFrecuenteDTO) throws Exception {
        if (clienteFrecuenteDTO == null) {
            throw new Exception("El cliente frecuente no puede ser nulo.");
        }

        if (clienteFrecuenteDTO.getIdCliente() == null) {
            throw new Exception("El id del cliente frecuente es obligatorio para editar.");
        }

        validarDatos(clienteFrecuenteDTO);

        try {
            ClienteFrecuente clienteFrecuente = convertirEntidad(clienteFrecuenteDTO);
            Cliente clienteEditado = clienteDAO.editar(clienteFrecuente);

            if (!(clienteEditado instanceof ClienteFrecuente)) {
                throw new Exception("El cliente editado no corresponde a un cliente frecuente.");
            }

            return convertirDTO((ClienteFrecuente) clienteEditado);
        } catch (PersistenciaException e) {
            throw new Exception("Error al editar el cliente frecuente en negocio.", e);
        }
    }

    /**
     * Elimina un cliente frecuente del sistema.
     *
     * @param id Identificador único del cliente frecuente a eliminar.
     * @return true si el cliente frecuente fue eliminado correctamente, false
     * en caso contrario.
     * @throws Exception Si ocurre un error durante la eliminación.
     */
    @Override
    public boolean eliminar(Long id) throws Exception {
        if (id == null) {
            throw new Exception("El id del cliente frecuente es obligatorio.");
        }

        try {
            Cliente cliente = clienteDAO.buscarPorId(id);

            if (cliente == null) {
                throw new Exception("No se encontró el cliente frecuente.");
            }

            if (!(cliente instanceof ClienteFrecuente)) {
                throw new Exception("El cliente encontrado no es un cliente frecuente.");
            }

            return clienteDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new Exception("Error al eliminar el cliente frecuente en negocio.", e);
        }
    }

    /**
     * Busca clientes frecuentes que coincidan con el filtro proporcionado.
     *
     * @param filtro Texto de búsqueda para filtrar clientes frecuentes.
     * @return Lista de clientes frecuentes que coinciden con el filtro.
     * @throws Exception Si ocurre un error durante la búsqueda.
     */
    @Override
    public List<ClienteFrecuenteDTO> buscarPorFiltros(String filtro) throws Exception {
        try {
            List<Cliente> clientes = clienteDAO.buscarPorFiltros(filtro);
            List<ClienteFrecuenteDTO> clientesFrecuentesDTO = new ArrayList<>();

            if (clientes != null) {
                for (Cliente cliente : clientes) {
                    if (cliente instanceof ClienteFrecuente) {
                        clientesFrecuentesDTO.add(convertirDTO((ClienteFrecuente) cliente));
                    }
                }
            }

            return clientesFrecuentesDTO;
        } catch (PersistenciaException e) {
            throw new Exception("Error al buscar clientes frecuentes por filtros en negocio.", e);
        }
    }

    /**
     * Valida los datos obligatorios de un cliente frecuente.
     *
     * @param clienteFrecuenteDTO DTO del cliente frecuente a validar.
     * @throws Exception Si falta algún dato obligatorio.
     */
    private void validarDatos(ClienteFrecuenteDTO clienteFrecuenteDTO) throws Exception {
        if (clienteFrecuenteDTO == null) {
            throw new Exception("Cliente frecuente no puede ser nulo.");
        }

        if (clienteFrecuenteDTO.getNombre() == null || clienteFrecuenteDTO.getNombre().trim().isEmpty()) {
            throw new Exception("Nombre es obligatorio.");
        }

        if (clienteFrecuenteDTO.getApellidoPaterno() == null || clienteFrecuenteDTO.getApellidoPaterno().trim().isEmpty()) {
            throw new Exception("Apellido paterno es obligatorio.");
        }

        if (clienteFrecuenteDTO.getApellidoMaterno() == null || clienteFrecuenteDTO.getApellidoMaterno().trim().isEmpty()) {
            throw new Exception("Apellido materno es obligatorio.");
        }

        if (clienteFrecuenteDTO.getTelefono() == null || clienteFrecuenteDTO.getTelefono().trim().isEmpty()) {
            throw new Exception("Telefono es obligatorio.");
        }

        if (clienteFrecuenteDTO.getFechaRegistro() == null) {
            throw new Exception("La fecha de registro es obligatoria.");
        }

//        if (clienteFrecuenteDTO.getNumeroVisitas() == null) {
//            throw new Exception("El numero de visitas es obligatorio.");
//        }
//
//        if (clienteFrecuenteDTO.getPuntosFidelidad() == null) {
//            throw new Exception("Los puntos de fidelidad son obligatorios.");
//        }
//
//        if (clienteFrecuenteDTO.getNumeroVisitas() < 0) {
//            throw new Exception("El numero de visitas no puede ser negativo.");
//        }
//
//        if (clienteFrecuenteDTO.getPuntosFidelidad() < 0) {
//            throw new Exception("Los puntos de fidelidad no pueden ser negativos.");
//        }
    }

    /**
     * Convierte un DTO de ClienteFrecuente a su representación en entidad.
     *
     * @param clienteFrecuenteDTO DTO del cliente frecuente.
     * @return Entidad cliente frecuente.
     */
    private ClienteFrecuente convertirEntidad(ClienteFrecuenteDTO clienteFrecuenteDTO) {
        if (clienteFrecuenteDTO == null) {
            return null;
        }

        return new ClienteFrecuente(
                clienteFrecuenteDTO.getFechaRegistro(),
                clienteFrecuenteDTO.getNumeroVisitas(),
                clienteFrecuenteDTO.getPuntosFidelidad(),
                clienteFrecuenteDTO.getFechaUltimaComanda(),
                clienteFrecuenteDTO.getIdCliente(),
                clienteFrecuenteDTO.getNombre(),
                clienteFrecuenteDTO.getApellidoPaterno(),
                clienteFrecuenteDTO.getApellidoMaterno(),
                clienteFrecuenteDTO.getTelefono(),
                clienteFrecuenteDTO.getCorreoElectronico()
        );
    }

    /**
     * Convierte una entidad ClienteFrecuente a su representación en DTO.
     *
     * @param clienteFrecuente Entidad cliente frecuente.
     * @return DTO del cliente frecuente.
     */
    private ClienteFrecuenteDTO convertirDTO(ClienteFrecuente clienteFrecuente) {
        if (clienteFrecuente == null) {
            return null;
        }

        return new ClienteFrecuenteDTO(
                clienteFrecuente.getId(),
                clienteFrecuente.getNombre(),
                clienteFrecuente.getApellidoPaterno(),
                clienteFrecuente.getApellidoMaterno(),
                clienteFrecuente.getTelefono(),
                clienteFrecuente.getCorreoElectronico(),
                clienteFrecuente.getFechaRegistro(),
                clienteFrecuente.getNumeroVisitas(),
                null,
                clienteFrecuente.getPuntosFidelidad(),
                clienteFrecuente.getFechaUltimaComanda()
        );
    }

}
