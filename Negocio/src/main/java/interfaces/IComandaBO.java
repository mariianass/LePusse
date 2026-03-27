/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.ComandaDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz para la lógica de negocio de la entidad Comanda. Define las
 * operaciones básicas para guardar, consultar, actualizar, eliminar y buscar
 * comandas dentro del sistema.
 *
 * @author regina, mariana e isaac.
 */
public interface IComandaBO {

    /**
     * Guarda una nueva comanda en el sistema.
     *
     * @param comandaDTO DTO con la información de la comanda.
     * @return Comanda guardada.
     * @throws NegocioException Si ocurre un error durante el guardado.
     */
    public ComandaDTO guardar(ComandaDTO comandaDTO) throws NegocioException;

    /**
     * Busca una comanda por su identificador único.
     *
     * @param id Identificador único de la comanda.
     * @return Comanda encontrada.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    public ComandaDTO buscarPorId(Long id) throws NegocioException;

    /**
     * Edita la información de una comanda existente.
     *
     * @param comandaDTO DTO con la información actualizada de la comanda.
     * @return Comanda actualizada.
     * @throws NegocioException Si ocurre un error durante la edición.
     */
    public ComandaDTO editar(ComandaDTO comandaDTO) throws NegocioException;

    /**
     * Elimina una comanda del sistema.
     *
     * @param id Identificador único de la comanda a eliminar.
     * @return true si la comanda fue eliminada correctamente, false en caso
     * contrario.
     * @throws NegocioException Si ocurre un error durante la eliminación.
     */
    public boolean eliminar(Long id) throws NegocioException;

    /**
     * Busca comandas que coincidan con el filtro proporcionado.
     *
     * @param filtro Texto de búsqueda para filtrar comandas.
     * @return Lista de comandas que coinciden con el filtro.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    List<ComandaDTO> buscarPorFiltros(String filtro) throws NegocioException;
}
