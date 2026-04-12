/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.EstadoComanda;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime fechaHoraCreacion;
    private EstadoComanda estado;
    private Double totalVenta;

    private Long idMesa;
    private Integer numeroMesa;

    private Long idCliente;
    private String nombreCliente;

    private List<DetalleComandaDTO> detalles;

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
     * @param estado Estado actual de la comanda.
     * @param totalVenta Total de venta de la comanda.
     * @param idMesa Identificador de la mesa.
     * @param numeroMesa Número de la mesa.
     * @param idCliente Identificador del cliente.
     * @param nombreCliente Nombre del cliente.
     * @param detalles Lista de detalles de la comanda.
     */
    public ComandaDTO(Long idComanda, String folio, LocalDateTime fechaHoraCreacion, EstadoComanda estado, Double totalVenta,
            Long idMesa, Integer numeroMesa, Long idCliente, String nombreCliente, List<DetalleComandaDTO> detalles) {
        this.idComanda = idComanda;
        this.folio = folio;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.estado = estado;
        this.totalVenta = totalVenta;
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.detalles = detalles;
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

    public LocalDateTime getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(LocalDateTime fechaHoraCreacion) {
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

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<DetalleComandaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleComandaDTO> detalles) {
        this.detalles = detalles;
    }
}
