/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Formula;

/**
 * Representa a un cliente frecuente dentro del sistema.
 * Esta clase hereda de Cliente y está mapeada a su propia tabla 
 * "clientes_frecuentes" en la base de datos. 
 * @author regina, mariana e isaac.
 */
@Entity
@Table(name = "clientes_frecuentes")
@DiscriminatorValue("FRECUENTE")
@PrimaryKeyJoinColumn(name = "id_cliente")
public class ClienteFrecuente extends Cliente implements Serializable {
 
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
    
    @Transient
    private Integer numeroVisitas;
    
    @Transient
    private Integer puntosFidelidad;
    
    @Transient
    private Double totalGastado;
    
    @Transient
    private LocalDateTime fechaUltimaComanda;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public ClienteFrecuente() {
    }    
    
    /**
     * Constructor que inicializa únicamente los atributos específicos de ClienteFrecuente.
     * No incluye los atributos heredados de Cliente.
     *
     * @param fechaRegistro Fecha de registro como cliente frecuente.
     * 
     */
    public ClienteFrecuente(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Constructor completo que inicializa tanto los atributos específicos 
     * de ClienteFrecuente como los atributos heredados de Cliente.
     *
     * @param fechaRegistro Fecha de registro como cliente frecuente.
     * @param id Identificador único del cliente.
     * @param nombre Nombre(s) del cliente.
     * @param apellidoPaterno Apellido paterno del cliente.
     * @param apellidoMaterno Apellido materno del cliente.
     * @param telefono Número de teléfono del cliente.
     * @param correoElectronico Correo electrónico del cliente.
     */
    public ClienteFrecuente(LocalDateTime fechaRegistro, Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String correoElectronico) {
        super(id, nombre, apellidoPaterno, apellidoMaterno, telefono, correoElectronico);
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getNumeroVisitas() {
        return numeroVisitas;
    }

    public void setNumeroVisitas(Integer numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }

    public Integer getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public void setPuntosFidelidad(Integer puntosFidelidad) {
        this.puntosFidelidad = puntosFidelidad;
    }

    public LocalDateTime getFechaUltimaComanda() {
        return fechaUltimaComanda;
    }

    public void setFechaUltimaComanda(LocalDateTime fechaUltimaComanda) {
        this.fechaUltimaComanda = fechaUltimaComanda;
    }

    public Double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }
    
    
    @Override
    public String toString() {
        return "ClienteFrecuente{" + "fechaRegistro=" + fechaRegistro + ", numeroVisitas=" + numeroVisitas + ", puntosFidelidad=" + puntosFidelidad + ", fechaUltimaComanda=" + fechaUltimaComanda + '}';
    }

}
