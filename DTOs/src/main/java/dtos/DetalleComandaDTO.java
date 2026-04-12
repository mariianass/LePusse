/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;

/**
 * Representa la información de un detalle de comanda dentro del sistema. Esta
 * clase se utiliza como DTO para transferir datos entre las capas de
 * presentación y negocio.
 *
 * @author regina, mariana e isaac.
 */
public class DetalleComandaDTO implements Serializable {

    private Long idDetalleComanda;
    Integer cantidad;
    private String comentarioEspecial;
    private Double precio;
    private Double subtotal;
    private Long idProducto;
    private String nombreProducto;

    /**
     * Constructor por defecto de DetalleComandaDTO.
     */
    public DetalleComandaDTO() {
    }

    /**
     * Constructor con todos los atributos del detalle de comanda.
     *
     * @param idDetalleComanda Identificador del detalle.
     * @param cantidad Cantidad solicitada.
     * @param comentarioEspecial Comentario especial.
     * @param precio Precio unitario.
     * @param subtotal Subtotal calculado.
     * @param idProducto Identificador del producto.
     * @param nombreProducto Nombre del producto.
     */
    public DetalleComandaDTO(Long idDetalleComanda, Integer cantidad, String comentarioEspecial,
                            Double precio, Double subtotal, Long idProducto, String nombreProducto) {
        this.idDetalleComanda = idDetalleComanda;
        this.cantidad = cantidad;
        this.comentarioEspecial = comentarioEspecial;
        this.precio = precio;
        this.subtotal = subtotal;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
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

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    @Override
    public String toString() {
        return "DetalleComandaDTO{" + "idDetalleComanda=" + idDetalleComanda + ", cantidad=" + cantidad + ", comentarioEspecial=" + comentarioEspecial + ", precio=" + precio + ", subtotal=" + subtotal + ", idProducto=" + idProducto + ", nombreProducto=" + nombreProducto + '}';
    }

}
