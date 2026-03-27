/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.EstadoComanda;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Representa la información de una comanda dentro del sistema. Esta clase se
 * utiliza como DTO para transferir datos entre las capas de presentación y
 * negocio.
 *
 * @author regina, mariana e isaac.
 */
public class ComandaDTO implements Serializable {

    private Long idComanda;
    private String folio;
    private LocalDate fechaHoraCreacion;
    private EstadoComanda estado;
    private Double totalVenta;

    /**
     * Constructor por defecto de ComandaDTO.
     */
    public ComandaDTO() {
    }

    /**
     * Constructor que inicializa todos los atributos de ComandaDTO.
     *
     * @param idComanda Identificador único de la comanda.
     * @param folio Folio de la comanda.
     * @param fechaHoraCreacion Fecha de creación de la comanda.
     * @param estado Estado actual de la comanda.
     * @param totalVenta Total de venta de la comanda.
     */
    public ComandaDTO(Long idComanda, String folio, LocalDate fechaHoraCreacion, EstadoComanda estado, Double totalVenta) {
        this.idComanda = idComanda;
        this.folio = folio;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.estado = estado;
        this.totalVenta = totalVenta;
    }

    public Long getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(Long idComanda) {
        this.idComanda = idComanda;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDate getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(LocalDate fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public EstadoComanda getEstado() {
        return estado;
    }

    public void setEstado(EstadoComanda estado) {
        this.estado = estado;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }
}
