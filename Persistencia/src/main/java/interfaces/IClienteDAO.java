/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import entidades.Cliente;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz para el acceso a datos de la entidad Cliente.
 * @author regina, mariana e isaac
 */
public interface IClienteDAO {

    public Cliente guardar(Cliente cliente) throws PersistenciaException;
    public Cliente buscarPorId(Long id) throws PersistenciaException;
    public Cliente editar(Cliente cliente) throws PersistenciaException;
    public boolean eliminar(Long id) throws PersistenciaException;
    List<Cliente> buscarPorFiltros(String filtro) throws PersistenciaException;
    List<Cliente> obtenerTodos() throws PersistenciaException;
 
}
