package interfaces;

import dtos.ClienteFrecuenteDTO;
import java.util.List;

/**
 * Interfaz para la lógica de negocio de la entidad ClienteFrecuente. Define las
 * operaciones básicas para guardar, consultar, actualizar, eliminar y buscar
 * clientes frecuentes dentro del sistema.
 *
 * @author regina, mariana e isaac.
 */
public interface IClienteFrecuenteBO {

    /**
     * Guarda un nuevo cliente frecuente en el sistema.
     *
     * @param clienteFrecuenteDTO DTO con la información del cliente frecuente.
     * @return Cliente frecuente guardado.
     * @throws Exception Si ocurre un error durante el guardado.
     */
    public ClienteFrecuenteDTO guardar(ClienteFrecuenteDTO clienteFrecuenteDTO) throws Exception;

    /**
     * Busca un cliente frecuente por su identificador único.
     *
     * @param id Identificador único del cliente frecuente.
     * @return Cliente frecuente encontrado.
     * @throws Exception Si ocurre un error durante la búsqueda.
     */
    public ClienteFrecuenteDTO buscarPorId(Long id) throws Exception;

    /**
     * Edita la información de un cliente frecuente existente.
     *
     * @param clienteFrecuenteDTO DTO con la información actualizada del cliente
     * frecuente.
     * @return Cliente frecuente actualizado.
     * @throws Exception Si ocurre un error durante la edición.
     */
    public ClienteFrecuenteDTO editar(ClienteFrecuenteDTO clienteFrecuenteDTO) throws Exception;

    /**
     * Elimina un cliente frecuente del sistema.
     *
     * @param id Identificador único del cliente frecuente a eliminar.
     * @return true si el cliente frecuente fue eliminado correctamente, false
     * en caso contrario.
     * @throws Exception Si ocurre un error durante la eliminación.
     */
    public boolean eliminar(Long id) throws Exception;

    /**
     * Busca clientes frecuentes que coincidan con el filtro proporcionado.
     *
     * @param filtro Texto de búsqueda para filtrar clientes frecuentes.
     * @return Lista de clientes frecuentes que coinciden con el filtro.
     * @throws Exception Si ocurre un error durante la búsqueda.
     */
    List<ClienteFrecuenteDTO> buscarPorFiltros(String filtro) throws Exception;
}
