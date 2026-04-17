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
 * Interfaz que define las operaciones de negocio relacionadas con los productos.
 * 
 * Establece los métodos que deben implementarse para la gestión de productos,
 * incluyendo operaciones de registro, edición, consulta, eliminación y actualización
 * de su estado y disponibilidad.
 * 
 * Esta interfaz forma parte de la capa de negocio y actúa como contrato para las
 * clases que implementan la lógica correspondiente.
 *
 * @author Regina, Mariana e Isaac
 */
public interface IProductoBO {

    /**
     * Guarda un nuevo producto en el sistema.
     * 
     * @param productoDTO producto a registrar.
     * @return producto registrado con su identificador asignado.
     * @throws NegocioException si ocurre un error en la validación o registro.
     */
    ProductoDTO guardar(ProductoDTO productoDTO) throws NegocioException;

    /**
     * Edita la información de un producto existente.
     * 
     * @param productoDTO producto con los datos actualizados.
     * @return producto actualizado.
     * @throws NegocioException si ocurre un error en la validación o actualización.
     */
    ProductoDTO editar(ProductoDTO productoDTO) throws NegocioException;

    /**
     * Busca un producto por su identificador.
     * 
     * @param id identificador del producto.
     * @return producto encontrado.
     * @throws NegocioException si el producto no existe o ocurre un error.
     */
    ProductoDTO buscarPorId(Long id) throws NegocioException;

    /**
     * Elimina un producto del sistema.
     * 
     * @param id identificador del producto a eliminar.
     * @throws NegocioException si ocurre un error durante la eliminación.
     */
    void eliminar(Long id) throws NegocioException;

    /**
     * Obtiene todos los productos registrados.
     * 
     * @return lista de productos.
     * @throws NegocioException si ocurre un error en la consulta.
     */
    List<ProductoDTO> obtenerTodos() throws NegocioException;

    /**
     * Busca productos filtrando por nombre y tipo.
     * 
     * Permite realizar consultas más específicas sin necesidad de traer
     * toda la lista de productos.
     * 
     * @param nombre nombre o parte del nombre del producto.
     * @param tipo tipo de producto.
     * @return lista de productos que cumplen con los filtros.
     * @throws NegocioException si ocurre un error en la consulta.
     */
    List<ProductoDTO> buscarPorNombreYTipo(String nombre, TipoProductoDTO tipo) throws NegocioException;
    
    /**
     * Cambia el estado lógico (activo/inactivo) de un producto.
     * 
     * @param idProducto identificador del producto.
     * @param activo nuevo estado del producto.
     * @throws NegocioException si ocurre un error durante la actualización.
     */
    void cambiarEstadoActivo(Long idProducto, Boolean activo) throws NegocioException;

    /**
     * Actualiza la disponibilidad del producto en función de su stock
     * o de sus ingredientes.
     * 
     * @param idProducto identificador del producto.
     * @throws NegocioException si ocurre un error durante la actualización.
     */
    void actualizarDisponibilidad(Long idProducto) throws NegocioException;
}