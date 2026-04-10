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
 * DTO que representa la información de un producto dentro del sistema.
 * Se utiliza para transferir datos entre las capas de presentación y negocio.
 * 
 * @author regina, mariana e isaac
 */
public class ProductoDTO implements Serializable {

    private Long idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private TipoProductoDTO tipo;
    private DisponibilidadProductoDTO disponibilidad;
    private Boolean activo;
    private String rutaImagen;
    private List<DetalleProductoIngredienteDTO> detallesIngredientes;

    /**
     * Constructor por defecto.
     */
    public ProductoDTO() {
        this.detallesIngredientes = new ArrayList<>();
    }

    /**
     * Constructor con todos los atributos.
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

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public TipoProductoDTO getTipo() {
        return tipo;
    }

    public void setTipo(TipoProductoDTO tipo) {
        this.tipo = tipo;
    }

    public DisponibilidadProductoDTO getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(DisponibilidadProductoDTO disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public List<DetalleProductoIngredienteDTO> getDetallesIngredientes() {
        return detallesIngredientes;
    }

    public void setDetallesIngredientes(List<DetalleProductoIngredienteDTO> detallesIngredientes) {
        this.detallesIngredientes = detallesIngredientes;
    }

    /**
     * Agrega un detalle de ingrediente a la lista del producto.
     * 
     * @param detalle Detalle a agregar.
     */
    public void agregarDetalleIngrediente(DetalleProductoIngredienteDTO detalle) {
        if (detalle != null) {
            this.detallesIngredientes.add(detalle);
        }
    }

    /**
     * Elimina un detalle de ingrediente de la lista del producto.
     * 
     * @param detalle Detalle a eliminar.
     */
    public void eliminarDetalleIngrediente(DetalleProductoIngredienteDTO detalle) {
        if (detalle != null) {
            this.detallesIngredientes.remove(detalle);
        }
    }

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
