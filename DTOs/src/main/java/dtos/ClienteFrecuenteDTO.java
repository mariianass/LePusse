/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Representa la información de un cliente frecuente dentro del sistema. Esta
 * clase se utiliza como DTO para transferir datos entre las capas de
 * presentación y negocio.
 *
 * @author regina, mariana e isaac.
 */
public class ClienteFrecuenteDTO implements Serializable {

    private Long idCliente;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correoElectronico;
    private LocalDate fechaRegistro;
    private Integer numeroVisitas;
    private Double totalGastado;
    private Integer puntosFidelidad;
    private LocalDate fechaUltimaComanda;

    /**
     * Constructor por defecto de ClienteFrecuenteDTO.
     */
    public ClienteFrecuenteDTO() {
    }

    /**
     * Constructor que inicializa todos los atributos de ClienteFrecuenteDTO.
     *
     * @param idCliente Identificador único del cliente.
     * @param nombre Nombre del cliente.
     * @param apellidoPaterno Apellido paterno del cliente.
     * @param apellidoMaterno Apellido materno del cliente.
     * @param telefono Número de teléfono del cliente.
     * @param correoElectronico Correo electrónico del cliente.
     * @param fechaRegistro Fecha de registro del cliente frecuente.
     * @param numeroVisitas Número de visitas acumuladas del cliente.
     * @param totalGastado Total gastado acumulado por el cliente.
     * @param puntosFidelidad Puntos de fidelidad acumulados.
     * @param fechaUltimaComanda Fecha de la última comanda registrada.
     */
    public ClienteFrecuenteDTO(Long idCliente, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String correoElectronico, LocalDate fechaRegistro, Integer numeroVisitas, Double totalGastado, Integer puntosFidelidad, LocalDate fechaUltimaComanda) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.fechaRegistro = fechaRegistro;
        this.numeroVisitas = numeroVisitas;
        this.totalGastado = totalGastado;
        this.puntosFidelidad = puntosFidelidad;
        this.fechaUltimaComanda = fechaUltimaComanda;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getNumeroVisitas() {
        return numeroVisitas;
    }

    public void setNumeroVisitas(Integer numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }

    public Double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    public Integer getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public void setPuntosFidelidad(Integer puntosFidelidad) {
        this.puntosFidelidad = puntosFidelidad;
    }

    public LocalDate getFechaUltimaComanda() {
        return fechaUltimaComanda;
    }

    public void setFechaUltimaComanda(LocalDate fechaUltimaComanda) {
        this.fechaUltimaComanda = fechaUltimaComanda;
    }
}
