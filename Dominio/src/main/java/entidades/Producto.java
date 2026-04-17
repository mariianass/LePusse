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
 * Entidad que representa un producto dentro del sistema.
 * 
 * Un producto puede ser un platillo, bebida o postre. Está compuesto por uno
 * o varios ingredientes, cada uno con una cantidad requerida específica.
 * 
 * Esta clase se mapea a la tabla "productos" en la base de datos.
 * 
 * @author Regina, Mariana e Isaac
 */
@Entity
@Table(name = "productos", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre"})
})
public class Producto implements Serializable{
    
    /**
     * Identificador único del producto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    /**
     * Nombre del producto.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Descripción del producto.
     */
    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    /**
     * Precio del producto.
     */
    @Column(name = "precio", nullable = false)
    private Double precio;

    /**
     * Tipo de producto (platillo, bebida o postre).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoProducto tipo;

    /**
     * Disponibilidad actual del producto.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "disponibilidad", nullable = false)
    private DisponibilidadProducto disponibilidad;

    /**
     * Estado lógico del producto (activo/inactivo).
     */
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    /**
     * Ruta de la imagen asociada al producto.
     */
    @Column(name = "ruta_imagen", nullable = true, length = 255)
    private String rutaImagen;

    /**
     * Lista de detalles que relacionan el producto con sus ingredientes.
     */
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleProductoIngrediente> detallesIngredientes = new ArrayList<>();

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Producto() {
    }

    /**
     * Constructor con todos los atributos del producto.
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
     * @return tipo de producto.
     */
    public TipoProducto getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de producto.
     * 
     * @param tipo nuevo tipo.
     */
    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la disponibilidad del producto.
     * 
     * @return disponibilidad actual.
     */
    public DisponibilidadProducto getDisponibilidad() {
        return disponibilidad;
    }

    /**
     * Establece la disponibilidad del producto.
     * 
     * @param disponibilidad nueva disponibilidad.
     */
    public void setDisponibilidad(DisponibilidadProducto disponibilidad) {
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
    public List<DetalleProductoIngrediente> getDetallesIngredientes() {
        return detallesIngredientes;
    }

    /**
     * Establece la lista de detalles de ingredientes del producto.
     * 
     * Reemplaza la lista actual y mantiene la relación bidireccional.
     * 
     * @param detallesIngredientes nueva lista de detalles.
     */
    public void setDetallesIngredientes(List<DetalleProductoIngrediente> detallesIngredientes) {
        this.detallesIngredientes.clear();

        if (detallesIngredientes != null) {
            for (DetalleProductoIngrediente detalle : detallesIngredientes) {
                agregarDetalleIngrediente(detalle);
            }
        }
    }

    /**
     * Agrega un detalle de ingrediente al producto y establece
     * la relación bidireccional.
     * 
     * @param detalle detalle a agregar.
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
     * @param detalle detalle a eliminar.
     */
    public void eliminarDetalleIngrediente(DetalleProductoIngrediente detalle) {
        if (detalle != null) {
            this.detallesIngredientes.remove(detalle);
            detalle.setProducto(null);
        }
    }

    /**
     * Representación en texto del producto.
     * 
     * @return cadena con los datos principales del producto.
     */
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