/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.EstadoMesa;
import java.io.Serializable;

/**
 * Representa la información de una mesa dentro del sistema. Esta clase se
 * utiliza como DTO para transferir datos entre las capas de presentación y
 * negocio.
 *
 * @author regina, mariana e isaac.
 */
public class MesaDTO implements Serializable {

    private Long idMesa;
    private Integer numeroMesa;
    private EstadoMesa estado;

    /**
     * Constructor por defecto de MesaDTO.
     */
    public MesaDTO() {
    }

    /**
     * Constructor con todos los atributos de la mesa.
     *
     * @param idMesa Identificador único de la mesa.
     * @param numeroMesa Número asignado a la mesa.
     * @param estado Estado actual de la mesa.
     */
    public MesaDTO(Long idMesa, Integer numeroMesa, EstadoMesa estado) {
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.estado = estado;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public EstadoMesa getEstado() {
        return estado;
    }

    public void setEstado(EstadoMesa estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "MesaDTO{" + "idMesa=" + idMesa + ", numeroMesa=" + numeroMesa + ", estado=" + estado + '}';
    }

}
