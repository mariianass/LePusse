package interfaces;

import dtos.ClienteDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz para la lógica de negocio de la entidad Cliente. Define las
 * operaciones básicas para registrar, consultar, actualizar, eliminar y buscar
 * clientes dentro del sistema.
 *
 * @author regina, mariana e isaac.
 */
public interface IClienteBO {

    /**
     * Registra un nuevo cliente en el sistema.
     *
     * @param clienteDTO DTO con la información del cliente.
     * @return Cliente registrado.
     * @throws NegocioException Si ocurre un error durante el registro.
     */
    public ClienteDTO guardar(ClienteDTO clienteDTO) throws NegocioException;

    /**
     * Busca un cliente por su identificador único.
     *
     * @param id Identificador único del cliente.
     * @return Cliente encontrado.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    public ClienteDTO buscarPorId(Long id) throws NegocioException;

    /**
     * Actualiza la información de un cliente existente.
     *
     * @param clienteDTO DTO con la información actualizada del cliente.
     * @return Cliente actualizado.
     * @throws NegocioException Si ocurre un error durante la actualización.
     */
    public ClienteDTO editar(ClienteDTO clienteDTO) throws NegocioException;

    /**
     * Elimina un cliente del sistema.
     *
     * @param id Identificador único del cliente a eliminar.
     * @return true si el cliente fue eliminado correctamente, false en caso
     * contrario.
     * @throws NegocioException Si ocurre un error durante la eliminación.
     */
    public boolean eliminar(Long id) throws NegocioException;

    /**
     * Busca clientes que coincidan con el filtro proporcionado.
     *
     * @param filtro Texto de búsqueda para filtrar clientes.
     * @return Lista de clientes que coinciden con el filtro.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    List<ClienteDTO> buscarPorFiltros(String filtro) throws NegocioException;
}
