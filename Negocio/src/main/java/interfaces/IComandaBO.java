/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.ComandaDTO;
import dtos.MesaDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz para la lógica de negocio de la entidad Comanda. Define las
 * operaciones necesarias para registrar, consultar, editar, entregar, cancelar
 * y buscar comandas dentro del sistema, así como la gestión básica de mesas.
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
     * Marca una comanda como entregada dentro del sistema.
     *
     * @param id Identificador único de la comanda.
     * @return Comanda actualizada con estado entregada.
     * @throws NegocioException Si ocurre un error durante la operación.
     */
    public ComandaDTO entregar(Long id) throws NegocioException;

    /**
     * Marca una comanda como cancelada dentro del sistema.
     *
     * @param id Identificador único de la comanda.
     * @return Comanda actualizada con estado cancelada.
     * @throws NegocioException Si ocurre un error durante la operación.
     */
    public ComandaDTO cancelar(Long id) throws NegocioException;

    /**
     * Busca comandas que coincidan con el filtro proporcionado.
     *
     * @param filtro Texto de búsqueda para filtrar comandas.
     * @return Lista de comandas que coinciden con el filtro.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    public List<ComandaDTO> buscarPorFiltros(String filtro) throws NegocioException;

    /**
     * Obtiene la lista de mesas disponibles dentro del sistema.
     *
     * @return Lista de mesas disponibles.
     * @throws NegocioException Si ocurre un error durante la consulta.
     */
    public List<MesaDTO> obtenerMesasDisponibles() throws NegocioException;

}
