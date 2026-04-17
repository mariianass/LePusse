/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import entidades.Cliente;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz para el acceso a datos de la entidad Cliente.
 *
 * @author regina, mariana e isaac
 */
public interface IClienteDAO {

    public Cliente guardar(Cliente cliente) throws PersistenciaException;

    public Cliente buscarPorId(Long id) throws PersistenciaException;

    public Cliente editar(Cliente cliente) throws PersistenciaException;

    public boolean eliminar(Long id) throws PersistenciaException;

    List<Cliente> buscarPorFiltros(String filtro) throws PersistenciaException;

    List<Cliente> obtenerTodos() throws PersistenciaException;

    public Cliente registrarClienteGeneral() throws PersistenciaException;

    /**
     * Obtiene el número de visitas realizadas por un cliente frecuente. Solo se
     * consideran las comandas entregadas.
     *
     * @param idCliente Identificador único del cliente.
     * @return Número de visitas registradas.
     * @throws PersistenciaException Si ocurre un error durante la consulta.
     */
    public Integer obtenerNumeroVisitas(Long idCliente) throws PersistenciaException;

    /**
     * Obtiene el total gastado acumulado por un cliente frecuente. Solo se
     * consideran las comandas entregadas.
     *
     * @param idCliente Identificador único del cliente.
     * @return Total gastado por el cliente.
     * @throws PersistenciaException Si ocurre un error durante la consulta.
     */
    public Double obtenerTotalGastado(Long idCliente) throws PersistenciaException;

    /**
     * Obtiene la fecha y hora de la última comanda entregada de un cliente
     * frecuente.
     *
     * @param idCliente Identificador único del cliente.
     * @return Fecha de la última comanda entregada, o null si no existe.
     * @throws PersistenciaException Si ocurre un error durante la consulta.
     */
    public LocalDateTime obtenerFechaUltimaComanda(Long idCliente) throws PersistenciaException;

}
