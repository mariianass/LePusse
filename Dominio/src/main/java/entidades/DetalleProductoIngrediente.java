/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidad que representa el detalle de los ingredientes requeridos para un producto.
 * 
 * También puede entenderse como la "receta" del producto, ya que cada instancia
 * indica qué ingrediente se utiliza y en qué cantidad.
 * 
 * Esta clase establece la relación entre un producto y sus ingredientes,
 * permitiendo definir múltiples ingredientes por producto.
 * 
 * Se mapea a la tabla "detalles_producto_ingrediente" en la base de datos.
 * 
 * @author Regina, Mariana e Isaac
 */
@Entity
@Table(name = "detalles_producto_ingrediente")
public class DetalleProductoIngrediente implements Serializable{
    
    /**
     * Identificador único del detalle de producto-ingrediente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_producto_ingrediente")
    private Long idDetalleProductoIngrediente;
    
    /**
     * Producto al que pertenece este detalle.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    /**
     * Ingrediente requerido para el producto.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;

    /**
     * Cantidad requerida del ingrediente para el producto.
     */
    @Column(name = "cantidad_requerida", nullable = false)
    private Integer cantidadRequerida;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public DetalleProductoIngrediente() {
    }

    /**
     * Constructor con todos los atributos del detalle.
     * 
     * @param idDetalleProductoIngrediente Identificador único del detalle.
     * @param producto Producto asociado al detalle.
     * @param ingrediente Ingrediente requerido.
     * @param cantidadRequerida Cantidad necesaria del ingrediente para el producto.
     */
    public DetalleProductoIngrediente(Long idDetalleProductoIngrediente, Producto producto,
            Ingrediente ingrediente, Integer cantidadRequerida) {
        this.idDetalleProductoIngrediente = idDetalleProductoIngrediente;
        this.producto = producto;
        this.ingrediente = ingrediente;
        this.cantidadRequerida = cantidadRequerida;
    }

    /**
     * Obtiene el identificador del detalle.
     * 
     * @return id del detalle producto-ingrediente.
     */
    public Long getIdDetalleProductoIngrediente() {
        return idDetalleProductoIngrediente;
    }

    /**
     * Establece el identificador del detalle.
     * 
     * @param idDetalleProductoIngrediente nuevo identificador.
     */
    public void setIdDetalleProductoIngrediente(Long idDetalleProductoIngrediente) {
        this.idDetalleProductoIngrediente = idDetalleProductoIngrediente;
    }

    /**
     * Obtiene el producto asociado a este detalle.
     * 
     * @return producto asociado.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto asociado a este detalle.
     * 
     * @param producto nuevo producto.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Obtiene el ingrediente asociado.
     * 
     * @return ingrediente requerido.
     */
    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    /**
     * Establece el ingrediente asociado.
     * 
     * @param ingrediente nuevo ingrediente.
     */
    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    /**
     * Obtiene la cantidad requerida del ingrediente.
     * 
     * @return cantidad requerida.
     */
    public Integer getCantidadRequerida() {
        return cantidadRequerida;
    }

    /**
     * Establece la cantidad requerida del ingrediente.
     * 
     * @param cantidadRequerida nueva cantidad.
     */
    public void setCantidadRequerida(Integer cantidadRequerida) {
        this.cantidadRequerida = cantidadRequerida;
    }

    /**
     * Representación en texto del detalle producto-ingrediente.
     * 
     * Incluye el identificador, el nombre del ingrediente (si está disponible)
     * y la cantidad requerida.
     * 
     * @return cadena con los datos principales del detalle.
     */
    @Override
    public String toString() {
        return "DetalleProductoIngrediente{" 
                + "idDetalleProductoIngrediente=" + idDetalleProductoIngrediente
                + ", ingrediente=" + (ingrediente != null ? ingrediente.getNombre() : null)
                + ", cantidadRequerida=" + cantidadRequerida
                + '}';
    }
}