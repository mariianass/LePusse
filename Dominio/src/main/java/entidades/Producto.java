/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.DisponibilidadProducto;
import enums.TipoProducto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *Entidad que representa un producto dentro del sistema.
 * Un producto puede ser un platillo, bebida o postre, se compone de uno o varios ingredientes
 * con cantidades requeridas específicas.
 * 
 * @author regina, mariana e isaac
 */
@Entity
@Table(name = "productos", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre"})
})
public class Producto implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoProducto tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "disponibilidad", nullable = false)
    private DisponibilidadProducto disponibilidad;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "ruta_imagen", nullable = true, length = 255)
    private String rutaImagen;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleProductoIngrediente> detallesIngredientes = new ArrayList<>();

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Producto() {
    }

    /**
     * Constructor con todos los atributos.
     * 
     * @param idProducto Identificador único del producto.
     * @param nombre Nombre del producto.
     * @param descripcion Descripción del producto.
     * @param precio Precio del producto.
     * @param tipo Tipo de producto.
     * @param disponibilidad Disponibilidad actual del producto.
     * @param activo Estado lógico del producto.
     * @param rutaImagen Ruta de la imagen asociada.
     * @param detallesIngredientes Lista de ingredientes requeridos.
     */
    public Producto(Long idProducto, String nombre, String descripcion, Double precio,
            TipoProducto tipo, DisponibilidadProducto disponibilidad, Boolean activo,
            String rutaImagen, List<DetalleProductoIngrediente> detallesIngredientes) {
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

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    public DisponibilidadProducto getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(DisponibilidadProducto disponibilidad) {
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

    public List<DetalleProductoIngrediente> getDetallesIngredientes() {
        return detallesIngredientes;
    }

    public void setDetallesIngredientes(List<DetalleProductoIngrediente> detallesIngredientes) {
        this.detallesIngredientes = detallesIngredientes;
    }

    /**
     * Agrega un detalle de ingrediente al producto y establece la relación bidireccional.
     * 
     * @param detalle Detalle a agregar.
     */
    public void agregarDetalleIngrediente(DetalleProductoIngrediente detalle) {
        if (detalle != null) {
            detalle.setProducto(this);
            this.detallesIngredientes.add(detalle);
        }
    }

    /**
     * Elimina un detalle de ingrediente del producto.
     * 
     * @param detalle Detalle a eliminar.
     */
    public void eliminarDetalleIngrediente(DetalleProductoIngrediente detalle) {
        if (detalle != null) {
            this.detallesIngredientes.remove(detalle);
            detalle.setProducto(null);
        }
    }

    @Override
    public String toString() {
        return "Producto{" 
                + "idProducto=" + idProducto
                + ", nombre=" + nombre
                + ", descripcion=" + descripcion
                + ", precio=" + precio
                + ", tipo=" + tipo
                + ", disponibilidad=" + disponibilidad
                + ", activo=" + activo
                + ", rutaImagen=" + rutaImagen
                + '}';
    }
    
}
