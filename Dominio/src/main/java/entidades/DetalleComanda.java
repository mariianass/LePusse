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
 * Entidad que representa el detalle de una comanda dentro del sistema. Cada
 * detalle corresponde a un producto solicitado, su cantidad, un posible
 * comentario especial, el precio unitario registrado en ese momento y su
 * subtotal.
 *
 * @author regina, mariana e isaac
 */
@Entity
@Table(name = "detalles_comanda")
public class DetalleComanda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_comanda")
    private Long idDetalleComanda;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "comentario_especial", nullable = true, length = 255)
    private String comentarioEspecial;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_comanda", nullable = false)
    private Comanda comanda;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public DetalleComanda() {
    }

    /**
     * Constructor con todos los atributos del detalle de comanda.
     *
     * @param idDetalleComanda Identificador único del detalle.
     * @param cantidad Cantidad solicitada del producto.
     * @param comentarioEspecial Comentario especial asociado al producto.
     * @param precio Precio unitario registrado al momento de la comanda.
     * @param subtotal Subtotal del detalle.
     * @param comanda Comanda a la que pertenece el detalle.
     * @param producto Producto solicitado.
     */
    public DetalleComanda(Long idDetalleComanda, Integer cantidad, String comentarioEspecial,
            Double precio, Double subtotal, Comanda comanda, Producto producto) {
        this.idDetalleComanda = idDetalleComanda;
        this.cantidad = cantidad;
        this.comentarioEspecial = comentarioEspecial;
        this.precio = precio;
        this.subtotal = subtotal;
        this.comanda = comanda;
        this.producto = producto;
    }

    public Long getIdDetalleComanda() {
        return idDetalleComanda;
    }

    public void setIdDetalleComanda(Long idDetalleComanda) {
        this.idDetalleComanda = idDetalleComanda;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getComentarioEspecial() {
        return comentarioEspecial;
    }

    public void setComentarioEspecial(String comentarioEspecial) {
        this.comentarioEspecial = comentarioEspecial;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "DetalleComanda{"
                + "idDetalleComanda=" + idDetalleComanda
                + ", cantidad=" + cantidad
                + ", comentarioEspecial=" + comentarioEspecial
                + ", precio=" + precio
                + ", subtotal=" + subtotal
                + ", idComanda=" + (comanda != null ? comanda.getIdComanda() : null)
                + ", idProducto=" + (producto != null ? producto.getIdProducto() : null)
                + ", nombreProducto=" + (producto != null ? producto.getNombre() : null)
                + '}';
    }

}
