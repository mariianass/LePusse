/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.EstadoMesa;
import enumsDTO.EstadoMesaDTO;
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
    private EstadoMesaDTO estado;

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
    public MesaDTO(Long idMesa, Integer numeroMesa, EstadoMesaDTO estado) {
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

    public EstadoMesaDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoMesaDTO estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "MesaDTO{" + "idMesa=" + idMesa + ", numeroMesa=" + numeroMesa + ", estado=" + estado + '}';
    }

}
