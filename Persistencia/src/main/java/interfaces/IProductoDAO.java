/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Producto;
import enums.DisponibilidadProducto;
import enums.TipoProducto;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Producto.
 * 
 * Proporciona los métodos necesarios para el acceso y manipulación de datos 
 * relacionados con los productos en el sistema.
 * 
 * @author regina, mariana e isaac
 */
public interface IProductoDAO {
    
    /**
     * Registra un nuevo producto en el sistema de persistencia.
     * * @param producto El objeto Producto con la información a guardar.
     * @return El producto guardado, incluyendo su identificador generado.
     * @throws PersistenciaException Si ocurre un error durante el proceso de guardado 
     * o si los datos no cumplen con las validaciones.
     */
    Producto guardar(Producto producto) throws PersistenciaException;

    /**
     * Actualiza la información de un producto existente.
     * * @param producto El objeto Producto con los datos actualizados.
     * @return El producto tras ser modificado en la persistencia.
     * @throws PersistenciaException Si el producto no existe o si ocurre un error 
     * en la base de datos.
     */
    Producto editar(Producto producto) throws PersistenciaException;

    /**
     * Recupera un producto específico mediante su identificador único.
     * * @param id El identificador único del producto.
     * @return El Producto encontrado o null si no se halla ninguna coincidencia.
     * @throws PersistenciaException Si ocurre un error técnico durante la búsqueda.
     */
    Producto buscarPorId(Long id) throws PersistenciaException;

    /**
     * Elimina de forma permanente un producto del sistema.
     * * @param id El identificador del producto a eliminar.
     * @return true si el producto fue eliminado con éxito, false en caso contrario.
     * @throws PersistenciaException Si el producto tiene dependencias que impiden su 
     * borrado o si ocurre un error en la transacción.
     */
    boolean eliminar(Long id) throws PersistenciaException;

    /**
     * Obtiene una lista completa de todos los productos registrados.
     * * @return Una lista de objetos Producto. Si no hay registros, devuelve una lista vacía.
     * @throws PersistenciaException Si ocurre un error al consultar la base de datos.
     */
    List<Producto> obtenerTodos() throws PersistenciaException;

    /**
     * Busca productos filtrando por una coincidencia parcial en el nombre y/o un tipo específico.
     * * @param nombre Cadena de texto para buscar coincidencias en el nombre del producto.
     * @param tipo El tipo de producto (Enum) por el cual filtrar.
     * @return Una lista de productos que cumplen con los criterios de búsqueda.
     * @throws PersistenciaException Si ocurre un error al procesar la consulta filtrada.
     */
    List<Producto> buscarPorNombreYTipo(String nombre, TipoProducto tipo) throws PersistenciaException;

    /**
     * Verifica si existe otro producto con el mismo nombre para evitar duplicidad de datos.
     * * @param nombre El nombre del producto a verificar.
     * @param idExcluir El ID del producto que se debe ignorar en la verificación (útil al editar).
     * @return true si ya existe un producto con ese nombre, false en caso contrario.
     * @throws PersistenciaException Si ocurre un error durante la validación.
     */
    boolean existeDuplicadoActivo(String nombre, Long idExcluir) throws PersistenciaException;

    /**
     * Modifica el estado de activación (lógico) de un producto.
     * * @param idProducto El identificador del producto.
     * @param activo true para habilitar el producto, false para deshabilitarlo.
     * @throws PersistenciaException Si ocurre un error al actualizar el estado.
     */
    void cambiarEstadoActivo(Long idProducto, Boolean activo) throws PersistenciaException;

    /**
     * Actualiza el nivel de disponibilidad de un producto (ej. Disponible, Agotado).
     * * @param idProducto El identificador del producto.
     * @param disponibilidad El nuevo valor de disponibilidad (Enum).
     * @throws PersistenciaException Si ocurre un error al actualizar la disponibilidad.
     */
    void actualizarDisponibilidad(Long idProducto, DisponibilidadProducto disponibilidad) throws PersistenciaException;
}