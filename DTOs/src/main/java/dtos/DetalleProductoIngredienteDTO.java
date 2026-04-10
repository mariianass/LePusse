/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;

/**
 * DTO que representa el detalle de un ingrediente requerido por un producto.
 *  Contiene el ingrediente asociado y la cantidad necesaria para su preparación
 * 
 * @author regina, mariana e isaac
 */
public class DetalleProductoIngredienteDTO  implements Serializable{
    
    private Long idDetalleProductoIngrediente;
    private IngredienteDTO ingrediente;
    private Integer cantidadRequerida;

    /**
     * Constructor por defecto.
     */
    public DetalleProductoIngredienteDTO() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param idDetalleProductoIngrediente Identificador único del detalle.
     * @param ingrediente Ingrediente asociado al producto.
     * @param cantidadRequerida Cantidad requerida del ingrediente.
     */
    public DetalleProductoIngredienteDTO(Long idDetalleProductoIngrediente, IngredienteDTO ingrediente, Integer cantidadRequerida) {
        this.idDetalleProductoIngrediente = idDetalleProductoIngrediente;
        this.ingrediente = ingrediente;
        this.cantidadRequerida = cantidadRequerida;
    }

    public Long getIdDetalleProductoIngrediente() {
        return idDetalleProductoIngrediente;
    }

    public void setIdDetalleProductoIngrediente(Long idDetalleProductoIngrediente) {
        this.idDetalleProductoIngrediente = idDetalleProductoIngrediente;
    }

    public IngredienteDTO getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(IngredienteDTO ingrediente) {
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
        return "DetalleProductoIngredienteDTO{"
                + "idDetalleProductoIngrediente=" + idDetalleProductoIngrediente
                + ", ingrediente=" + ingrediente
                + ", cantidadRequerida=" + cantidadRequerida
                + '}';
    }

}
