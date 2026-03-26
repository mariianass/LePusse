/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.ClienteFrecuenteDTO;
import java.util.List;

/**
 * Interfaz para la gestión de clientes frecuentes dentro del sistema. Esta
 * interfaz establece los métodos necesarios para registrar, actualizar,
 * consultar y eliminar clientes frecuentes.
 *
 * @author regina, mariana e isaac.
 */
public interface IClienteFrecuenteBO {

    /**
     * Registra un nuevo cliente frecuente en el sistema.
     *
     * @param clienteFrecuenteDTO DTO con la información del cliente frecuente.
     * @return Cliente frecuente registrado.
     * @throws Exception Si ocurre un error durante el registro.
     */
    ClienteFrecuenteDTO registrar(ClienteFrecuenteDTO clienteFrecuenteDTO) throws Exception;

    /**
     * Actualiza la información de un cliente frecuente existente.
     *
     * @param clienteFrecuenteDTO DTO con la información actualizada del cliente
     * frecuente.
     * @return Cliente frecuente actualizado.
     * @throws Exception Si ocurre un error durante la actualización.
     */
    ClienteFrecuenteDTO actualizar(ClienteFrecuenteDTO clienteFrecuenteDTO) throws Exception;

    /**
     * Consulta todos los clientes frecuentes registrados en el sistema.
     *
     * @return Lista de clientes frecuentes.
     * @throws Exception Si ocurre un error durante la consulta.
     */
    List<ClienteFrecuenteDTO> consultarTodos() throws Exception;

    /**
     * Busca un cliente frecuente por su identificador único.
     *
     * @param idCliente Identificador único del cliente frecuente.
     * @return Cliente frecuente encontrado.
     * @throws Exception Si ocurre un error durante la búsqueda.
     */
    ClienteFrecuenteDTO buscarPorId(Long idCliente) throws Exception;

    /**
     * Elimina un cliente frecuente del sistema.
     *
     * @param idCliente Identificador único del cliente frecuente a eliminar.
     * @throws Exception Si ocurre un error durante la eliminación.
     */
    void eliminar(Long idCliente) throws Exception;
}
