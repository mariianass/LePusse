/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import entidades.Cliente;
import java.util.List;

/**
 * Interfaz para el acceso a datos de la entidad Cliente.
 * @author regina, mariana e isaac
 */
public interface IClienteDAO {

    public Cliente guardar(Cliente cliente);
    public Cliente buscarPorId(Long id);
    public Cliente editar(Cliente cliente);
    public boolean eliminar(Long id);
    List<Cliente> buscarPorFiltros(String filtro);
 
}
