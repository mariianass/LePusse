/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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
    private LocalDate fechaRegistro;
    @Column(name = "numero_visitas")
    private Integer numeroVisitas;
    @Column(name = "puntos_fidelidad")
    private Integer puntosFidelidad;
    @Column(name = "fecha_ultima_comanda")
    private LocalDate fechaUltimaComanda;

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
     * @param numeroVisitas Número de visitas acumuladas.
     * @param puntosFidelidad Puntos de fidelidad acumulados.
     * @param fechaUltimaComanda Fecha de la última compra/comanda.
     */
    public ClienteFrecuente(LocalDate fechaRegistro, Integer numeroVisitas, Integer puntosFidelidad, LocalDate fechaUltimaComanda) {
        this.fechaRegistro = fechaRegistro;
        this.numeroVisitas = numeroVisitas;
        this.puntosFidelidad = puntosFidelidad;
        this.fechaUltimaComanda = fechaUltimaComanda;
    }

    /**
     * Constructor completo que inicializa tanto los atributos específicos 
     * de ClienteFrecuente como los atributos heredados de Cliente.
     *
     * @param fechaRegistro Fecha de registro como cliente frecuente.
     * @param numeroVisitas Número de visitas acumuladas.
     * @param puntosFidelidad Puntos de fidelidad acumulados.
     * @param fechaUltimaComanda Fecha de la última compra/comanda.
     * @param id Identificador único del cliente.
     * @param nombre Nombre(s) del cliente.
     * @param apellidoPaterno Apellido paterno del cliente.
     * @param apellidoMaterno Apellido materno del cliente.
     * @param telefono Número de teléfono del cliente.
     * @param correoElectronico Correo electrónico del cliente.
     */
    public ClienteFrecuente(LocalDate fechaRegistro, Integer numeroVisitas, Integer puntosFidelidad, LocalDate fechaUltimaComanda, Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String correoElectronico) {
        super(id, nombre, apellidoPaterno, apellidoMaterno, telefono, correoElectronico);
        this.fechaRegistro = fechaRegistro;
        this.numeroVisitas = numeroVisitas;
        this.puntosFidelidad = puntosFidelidad;
        this.fechaUltimaComanda = fechaUltimaComanda;
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

    @Override
    public String toString() {
        return "ClienteFrecuente{" + "fechaRegistro=" + fechaRegistro + ", numeroVisitas=" + numeroVisitas + ", puntosFidelidad=" + puntosFidelidad + ", fechaUltimaComanda=" + fechaUltimaComanda + '}';
    }

}
