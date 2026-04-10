/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import entidades.Producto;
import enums.DisponibilidadProducto;
import enums.TipoProducto;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Producto
 * 
 * @author regina, mariana e isaac
 */
public interface IProductoDAO {
    
    Producto guardar(Producto producto) throws PersistenciaException;

    Producto editar(Producto producto) throws PersistenciaException;

    Producto buscarPorId(Long id) throws PersistenciaException;

    boolean eliminar(Long id) throws PersistenciaException;

    List<Producto> obtenerTodos() throws PersistenciaException;

    List<Producto> buscarPorNombreYTipo(String nombre, TipoProducto tipo) throws PersistenciaException;

    boolean existeDuplicadoActivo(String nombre, Long idExcluir) throws PersistenciaException;

    void cambiarEstadoActivo(Long idProducto, Boolean activo) throws PersistenciaException;

    void actualizarDisponibilidad(Long idProducto, DisponibilidadProducto disponibilidad) throws PersistenciaException;
}
