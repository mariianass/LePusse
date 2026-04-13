/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Comanda;
import entidades.Mesa;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz para la persistencia de la entidad Comanda. Define las operaciones
 * necesarias para guardar, consultar, actualizar y buscar comandas dentro del
 * sistema, así como la consulta de mesas disponibles y comandas activas por
 * mesa.
 *
 * @author regina, mariana e isaac.
 */
public interface IComandaDAO {

    /**
     * Guarda una nueva comanda en el sistema.
     *
     * @param comanda Entidad con la información de la comanda.
     * @return Comanda guardada.
     * @throws PersistenciaException Si ocurre un error durante el guardado.
     */
    public Comanda guardar(Comanda comanda) throws PersistenciaException;

    /**
     * Busca una comanda por su identificador único.
     *
     * @param id Identificador único de la comanda.
     * @return Comanda encontrada.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda.
     */
    public Comanda buscarPorId(Long id) throws PersistenciaException;

    /**
     * Edita la información de una comanda existente.
     *
     * @param comanda Entidad con la información actualizada de la comanda.
     * @return Comanda actualizada.
     * @throws PersistenciaException Si ocurre un error durante la edición.
     */
    public Comanda editar(Comanda comanda) throws PersistenciaException;

    /**
     * Obtiene la lista de mesas disponibles dentro del sistema.
     *
     * @return Lista de mesas disponibles.
     * @throws PersistenciaException Si ocurre un error durante la consulta.
     */
    public List<Mesa> obtenerMesasDisponibles() throws PersistenciaException;

    /**
     * Busca comandas que coincidan con el filtro proporcionado.
     *
     * @param filtro Texto de búsqueda para filtrar comandas.
     * @return Lista de comandas que coinciden con el filtro.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda.
     */
    public List<Comanda> buscarPorFiltros(String filtro) throws PersistenciaException;

    /**
     * Busca una comanda activa asociada a una mesa.
     *
     * @param idMesa Identificador único de la mesa.
     * @return Comanda activa encontrada, o null si no existe una comanda activa
     * para esa mesa.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda.
     */
    public Comanda buscarComandaActivaPorMesa(Long idMesa) throws PersistenciaException;
}
