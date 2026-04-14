package BOs;

import DAOs.ClienteDAO;
import dtos.ClienteFrecuenteDTO;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IClienteDAO;
import interfaces.IClienteFrecuenteBO;
import java.util.ArrayList;
import java.util.List;
import utilidades.CifradorTelefono;
import static validadores.ValidadorClienteFrecuente.validar;

/**
 * Implementa la lógica de negocio de la entidad ClienteFrecuente. Esta clase se
 * de convertir entre DTO y entidad, y delegar las operaciones correspondientes
 * a la capa de persistencia.
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
     * @throws NegocioException Si ocurre un error durante el guardado.
     */
    @Override
    public ClienteFrecuenteDTO guardar(ClienteFrecuenteDTO clienteFrecuenteDTO) throws NegocioException {
        validar(clienteFrecuenteDTO);

        try {
            ClienteFrecuente clienteFrecuente = convertirEntidad(clienteFrecuenteDTO);
            Cliente clienteGuardado = clienteDAO.guardar(clienteFrecuente);

            if (!(clienteGuardado instanceof ClienteFrecuente)) {
                throw new NegocioException("El cliente guardado no corresponde a un cliente frecuente.");
            }

            return convertirDTO((ClienteFrecuente) clienteGuardado);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al guardar el cliente frecuente en negocio.", e);
        }
    }

    /**
     * Busca un cliente frecuente por su identificador único.
     *
     * @param id Identificador único del cliente frecuente.
     * @return Cliente frecuente encontrado.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    @Override
    public ClienteFrecuenteDTO buscarPorId(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El id del cliente frecuente es obligatorio.");
        }

        try {
            Cliente cliente = clienteDAO.buscarPorId(id);

            if (cliente == null) {
                throw new NegocioException("No se encontró el cliente frecuente.");
            }

            if (!(cliente instanceof ClienteFrecuente)) {
                throw new NegocioException("El cliente encontrado no es un cliente frecuente.");
            }

            return convertirDTO((ClienteFrecuente) cliente);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar el cliente frecuente en negocio.", e);
        }
    }

    /**
     * Edita la información de un cliente frecuente existente.
     *
     * @param clienteFrecuenteDTO DTO con la información actualizada del cliente
     * frecuente.
     * @return Cliente frecuente actualizado.
     * @throws NegocioException Si ocurre un error durante la edición.
     */
    @Override
    public ClienteFrecuenteDTO editar(ClienteFrecuenteDTO clienteFrecuenteDTO) throws NegocioException {
        if (clienteFrecuenteDTO == null) {
            throw new NegocioException("El cliente frecuente no puede ser nulo.");
        }

        if (clienteFrecuenteDTO.getIdCliente() == null) {
            throw new NegocioException("El id del cliente frecuente es obligatorio para editar.");
        }

        validar(clienteFrecuenteDTO);

        try {
            ClienteFrecuente clienteFrecuente = convertirEntidad(clienteFrecuenteDTO);
            Cliente clienteEditado = clienteDAO.editar(clienteFrecuente);

            if (!(clienteEditado instanceof ClienteFrecuente)) {
                throw new NegocioException("El cliente editado no corresponde a un cliente frecuente.");
            }

            return convertirDTO((ClienteFrecuente) clienteEditado);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al editar el cliente frecuente en negocio.", e);
        }
    }

    /**
     * Elimina un cliente frecuente del sistema.
     *
     * @param id Identificador único del cliente frecuente a eliminar.
     * @return true si el cliente frecuente fue eliminado correctamente, false
     * en caso contrario.
     * @throws NegocioException Si ocurre un error durante la eliminación.
     */
    @Override
    public boolean eliminar(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El id del cliente frecuente es obligatorio.");
        }

        try {
            Cliente cliente = clienteDAO.buscarPorId(id);

            if (cliente == null) {
                throw new NegocioException("No se encontró el cliente frecuente.");
            }

            if (!(cliente instanceof ClienteFrecuente)) {
                throw new NegocioException("El cliente encontrado no es un cliente frecuente.");
            }

            return clienteDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al eliminar el cliente frecuente en negocio.", e);
        }
    }

    /**
     * Busca clientes frecuentes que coincidan con el filtro proporcionado.
     *
     * @param filtro Texto de búsqueda para filtrar clientes frecuentes.
     * @return Lista de clientes frecuentes que coinciden con el filtro.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    @Override
    public List<ClienteFrecuenteDTO> buscarPorFiltros(String filtro) throws NegocioException {
        try {
            List<Cliente> clientes = clienteDAO.obtenerTodos();
            List<ClienteFrecuenteDTO> clientesFrecuentesDTO = new ArrayList<>();

            String filtroLimpio = filtro == null ? "" : filtro.trim().toLowerCase();

            if (clientes != null) {
                for (Cliente cliente : clientes) {
                    if (cliente instanceof ClienteFrecuente) {
                        ClienteFrecuenteDTO dto = convertirDTO((ClienteFrecuente) cliente);

                        if (filtroLimpio.isEmpty()
                                || contieneTexto(dto.getNombre(), filtroLimpio)
                                || contieneTexto(dto.getApellidoPaterno(), filtroLimpio)
                                || contieneTexto(dto.getApellidoMaterno(), filtroLimpio)
                                || contieneTexto(dto.getCorreoElectronico(), filtroLimpio)
                                || contieneTexto(dto.getTelefono(), filtroLimpio)
                                || contieneTexto(
                                        (dto.getNombre() + " " + dto.getApellidoPaterno() + " " + dto.getApellidoMaterno()).trim(),
                                        filtroLimpio)) {
                            clientesFrecuentesDTO.add(dto);
                        }
                    }
                }
            }

            return clientesFrecuentesDTO;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar clientes frecuentes por filtros en negocio.", e);
        }
    }

    private boolean contieneTexto(String texto, String filtro) {
        return texto != null && texto.toLowerCase().contains(filtro);
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
                CifradorTelefono.encriptar(clienteFrecuenteDTO.getTelefono()),
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
                CifradorTelefono.desencriptar(clienteFrecuente.getTelefono()),
                clienteFrecuente.getCorreoElectronico(),
                clienteFrecuente.getFechaRegistro(),
                clienteFrecuente.getNumeroVisitas(),
                null,
                clienteFrecuente.getPuntosFidelidad(),
                clienteFrecuente.getFechaUltimaComanda()
        );
    }

    @Override
    public List<ClienteFrecuenteDTO> obtenerTodos() throws NegocioException {
        try {
            List<Cliente> clientes = clienteDAO.obtenerTodos();
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
            throw new NegocioException("Error al obtener todos los clientes frecuentes en negocio.", e);
        }
    }
    
}