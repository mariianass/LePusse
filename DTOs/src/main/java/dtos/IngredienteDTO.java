/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.UnidadMedida;
import enumsDTO.UnidadMedidaDTO;
import java.io.Serializable;

/**
 * DTO que representa la información de un ingrediente transferida entre capas
 * del sistema.
 * 
 * @author regina, mariana e isaac
 */
public class IngredienteDTO implements Serializable {

    private Long idIngrediente;
    private String nombre;
    private UnidadMedidaDTO unidadMedida;
    private Double stockActual;
    private Double umbral;
    public IngredienteDTO() {
    }
    
    /**
     * Constructor con todos los atributos.
     * 
     * @param idIngrediente Identificador único del ingrediente.
     * @param nombre Nombre del ingrediente.
     * @param unidadMedida Unidad de medida del ingrediente.
     * @param stockActual Cantidad actual en inventario.
     * @param umbral Límite mínimo para alerta de stock.
     */
    public IngredienteDTO(Long idIngrediente, String nombre, UnidadMedidaDTO unidadMedida, Double stockActual, Double umbral) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.stockActual = stockActual;
        this.umbral = umbral;
    }

    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UnidadMedidaDTO getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedidaDTO unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Double getStockActual() {
        return stockActual;
    }

    public void setStockActual(Double stockActual) {
        this.stockActual = stockActual;
    }

    public Double getUmbral() {
        return umbral;
    }

    public void setUmbral(Double umbral) {
        this.umbral = umbral;
    }

    @Override
    public String toString() {
        return "IngredienteDTO{" 
                + "idIngrediente=" + idIngrediente 
                + ", nombre=" + nombre 
                + ", unidadMedida=" + unidadMedida 
                + ", stockActual=" + stockActual 
                + ", umbral=" + umbral 
                + '}';
    }
}