/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enumsDTO.DisponibilidadProductoDTO;
import enumsDTO.TipoProductoDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO que representa la información de un producto.
 * 
 * Se utiliza para transferir datos entre la capa de presentación y la capa
 * de negocio sin exponer directamente las entidades de la base de datos.
 * 
 * Contiene los atributos necesarios para representar un producto, así como
 * su lista de ingredientes asociados.
 * 
 * @author Regina, Mariana e Isaac
 */
public class ProductoDTO implements Serializable {

    /**
     * Identificador único del producto.
     */
    private Long idProducto;

    /**
     * Nombre del producto.
     */
    private String nombre;

    /**
     * Descripción del producto.
     */
    private String descripcion;

    /**
     * Precio del producto.
     */
    private Double precio;

    /**
     * Tipo de producto (platillo, bebida o postre).
     */
    private TipoProductoDTO tipo;

    /**
     * Disponibilidad actual del producto.
     */
    private DisponibilidadProductoDTO disponibilidad;

    /**
     * Estado lógico del producto (activo/inactivo).
     */
    private Boolean activo;

    /**
     * Ruta de la imagen asociada al producto.
     */
    private String rutaImagen;

    /**
     * Lista de detalles de ingredientes del producto.
     */
    private List<DetalleProductoIngredienteDTO> detallesIngredientes;

    /**
     * Constructor por defecto.
     * Inicializa la lista de ingredientes.
     */
    public ProductoDTO() {
        this.detallesIngredientes = new ArrayList<>();
    }

    /**
     * Constructor con todos los atributos del producto.
     * 
     * @param idProducto Identificador único del producto.
     * @param nombre Nombre del producto.
     * @param descripcion Descripción del producto.
     * @param precio Precio del producto.
     * @param tipo Tipo del producto.
     * @param disponibilidad Disponibilidad actual del producto.
     * @param activo Estado lógico del producto.
     * @param rutaImagen Ruta de la imagen asociada al producto.
     * @param detallesIngredientes Lista de ingredientes requeridos por el producto.
     */
    public ProductoDTO(Long idProducto, String nombre, String descripcion, Double precio,
            TipoProductoDTO tipo, DisponibilidadProductoDTO disponibilidad,
            Boolean activo, String rutaImagen,
            List<DetalleProductoIngredienteDTO> detallesIngredientes) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.disponibilidad = disponibilidad;
        this.activo = activo;
        this.rutaImagen = rutaImagen;
        this.detallesIngredientes = detallesIngredientes != null
                ? detallesIngredientes : new ArrayList<>();
    }

    /**
     * Obtiene el identificador del producto.
     * 
     * @return id del producto.
     */
    public Long getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto.
     * 
     * @param idProducto nuevo identificador.
     */
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el nombre del producto.
     * 
     * @return nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * 
     * @param nombre nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del producto.
     * 
     * @return descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     * 
     * @param descripcion nueva descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el precio del producto.
     * 
     * @return precio del producto.
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * 
     * @param precio nuevo precio.
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el tipo de producto.
     * 
     * @return tipo del producto.
     */
    public TipoProductoDTO getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de producto.
     * 
     * @param tipo nuevo tipo.
     */
    public void setTipo(TipoProductoDTO tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la disponibilidad del producto.
     * 
     * @return disponibilidad actual.
     */
    public DisponibilidadProductoDTO getDisponibilidad() {
        return disponibilidad;
    }

    /**
     * Establece la disponibilidad del producto.
     * 
     * @param disponibilidad nueva disponibilidad.
     */
    public void setDisponibilidad(DisponibilidadProductoDTO disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    /**
     * Indica si el producto está activo.
     * 
     * @return true si está activo, false en caso contrario.
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * Establece el estado del producto.
     * 
     * @param activo nuevo estado lógico.
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    /**
     * Obtiene la ruta de la imagen del producto.
     * 
     * @return ruta de la imagen.
     */
    public String getRutaImagen() {
        return rutaImagen;
    }

    /**
     * Establece la ruta de la imagen del producto.
     * 
     * @param rutaImagen nueva ruta de la imagen.
     */
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    /**
     * Obtiene la lista de detalles de ingredientes del producto.
     * 
     * @return lista de detalles de ingredientes.
     */
    public List<DetalleProductoIngredienteDTO> getDetallesIngredientes() {
        return detallesIngredientes;
    }

    /**
     * Establece la lista de detalles de ingredientes del producto.
     * 
     * @param detallesIngredientes nueva lista de detalles.
     */
    public void setDetallesIngredientes(List<DetalleProductoIngredienteDTO> detallesIngredientes) {
        this.detallesIngredientes = detallesIngredientes;
    }

    /**
     * Agrega un detalle de ingrediente a la lista del producto.
     * 
     * @param detalle detalle a agregar.
     */
    public void agregarDetalleIngrediente(DetalleProductoIngredienteDTO detalle) {
        if (detalle != null) {
            this.detallesIngredientes.add(detalle);
        }
    }

    /**
     * Elimina un detalle de ingrediente de la lista del producto.
     * 
     * @param detalle detalle a eliminar.
     */
    public void eliminarDetalleIngrediente(DetalleProductoIngredienteDTO detalle) {
        if (detalle != null) {
            this.detallesIngredientes.remove(detalle);
        }
    }

    /**
     * Representación en texto del producto DTO.
     * 
     * @return cadena con los datos del producto.
     */
    @Override
    public String toString() {
        return "ProductoDTO{" 
                + "idProducto=" + idProducto
                + ", nombre=" + nombre
                + ", descripcion=" + descripcion
                + ", precio=" + precio
                + ", tipo=" + tipo
                + ", disponibilidad=" + disponibilidad
                + ", activo=" + activo
                + ", rutaImagen=" + rutaImagen
                + ", detallesIngredientes=" + detallesIngredientes
                + '}';
    }
    
}