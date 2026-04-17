/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;

/**
 * DTO que representa el detalle de un ingrediente
 * requerido por un producto.
 * 
 * Este objeto se utiliza para transferir información entre las capas de
 * presentación y negocio, sin exponer directamente las entidades.
 * 
 * Contiene el ingrediente asociado y la cantidad necesaria para la
 * preparación del producto.
 * 
 * @author Regina, Mariana e Isaac
 */
public class DetalleProductoIngredienteDTO  implements Serializable{
    
    /**
     * Identificador único del detalle producto-ingrediente.
     */
    private Long idDetalleProductoIngrediente;

    /**
     * Ingrediente asociado al producto.
     */
    private IngredienteDTO ingrediente;

    /**
     * Cantidad requerida del ingrediente.
     */
    private Integer cantidadRequerida;

    /**
     * Constructor por defecto.
     */
    public DetalleProductoIngredienteDTO() {
    }

    /**
     * Constructor con todos los atributos del detalle.
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
     * Obtiene el ingrediente asociado.
     * 
     * @return ingrediente del detalle.
     */
    public IngredienteDTO getIngrediente() {
        return ingrediente;
    }

    /**
     * Establece el ingrediente asociado.
     * 
     * @param ingrediente nuevo ingrediente.
     */
    public void setIngrediente(IngredienteDTO ingrediente) {
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
     * Representación en texto del detalle DTO.
     * 
     * @return cadena con los datos del detalle.
     */
    @Override
    public String toString() {
        return "DetalleProductoIngredienteDTO{"
                + "idDetalleProductoIngrediente=" + idDetalleProductoIngrediente
                + ", ingrediente=" + ingrediente
                + ", cantidadRequerida=" + cantidadRequerida
                + '}';
    }

}