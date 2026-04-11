/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import dtos.ProductoDTO;
import enumsDTO.TipoProductoDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de negocio para productos.
 *
 * @author regina, mariana e isaac
 */
public interface IProductoBO {

    ProductoDTO guardar(ProductoDTO productoDTO) throws NegocioException;

    ProductoDTO editar(ProductoDTO productoDTO) throws NegocioException;

    ProductoDTO buscarPorId(Long id) throws NegocioException;

    void eliminar(Long id) throws NegocioException;

    List<ProductoDTO> obtenerTodos() throws NegocioException;

    List<ProductoDTO> buscarPorNombreYTipo(String nombre, TipoProductoDTO tipo) throws NegocioException;
    
    void cambiarEstadoActivo(Long idProducto, Boolean activo) throws NegocioException;

    void actualizarDisponibilidad(Long idProducto) throws NegocioException;
}
