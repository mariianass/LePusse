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
 * Representa el detalle de los ingredientes requeridos para un producto. (la receta)
 * Cada registro indica que ingrediente utiliza un producto y que cantidad.
 * 
 * @author regina, mariana e issac
 */
@Entity
@Table(name = "detalles_producto_ingrediente")
public class DetalleProductoIngrediente implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_producto_ingrediente")
    private Long idDetalleProductoIngrediente;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;

    @Column(name = "cantidad_requerida", nullable = false)
    private Integer cantidadRequerida;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public DetalleProductoIngrediente() {
    }

    /**
     * Constructor con todos los atributos.
     * 
     * @param idDetalleProductoIngrediente Identificador único del detalle.
     * @param producto Producto asociado.
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

    public Long getIdDetalleProductoIngrediente() {
        return idDetalleProductoIngrediente;
    }

    public void setIdDetalleProductoIngrediente(Long idDetalleProductoIngrediente) {
        this.idDetalleProductoIngrediente = idDetalleProductoIngrediente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Integer getCantidadRequerida() {
        return cantidadRequerida;
    }

    public void setCantidadRequerida(Integer cantidadRequerida) {
        this.cantidadRequerida = cantidadRequerida;
    }

    @Override
    public String toString() {
        return "DetalleProductoIngrediente{" 
                + "idDetalleProductoIngrediente=" + idDetalleProductoIngrediente
                + ", ingrediente=" + (ingrediente != null ? ingrediente.getNombre() : null)
                + ", cantidadRequerida=" + cantidadRequerida
                + '}';
    }
}
