/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.EstadoComanda;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad que representa una Comanda dentro del sistema.
 * Se mapea a la tabla "comandas" en la base de datos y contiene 
 * la información detallada de una orden, incluyendo su estado, 
 * fecha de creación y el total a pagar.
 * @author regina, mariana e isaac
 */
@Entity
@Table(name = "comandas")
public class Comanda {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComanda;
    
    @Column(name = "folio", nullable = false, length = 13)
    private String folio;

    @Column(name = "fecha_hora_creacion", nullable = false)
    private LocalDate fechaHoraCreacion;
    
    @Column(name = "estado", nullable = false)
    private EstadoComanda estado;
    
    @Column(name = "total_venta", nullable = false)
    private Double totalVenta;

    /**
     * Constructor por defecto requerido por JPA.
     * Crea una instancia vacía de Comanda.
     */
    public Comanda() {
    }

    /**
     * Constructor con todos los atributos de la comanda.
     * * @param idComanda         El identificador único de la comanda.
     * @param folio             El folio alfanumérico o numérico de la comanda.
     * @param fechaHoraCreacion La fecha de creación de la comanda.
     * @param estado            El estado en el que se encuentra la orden.
     * @param totalVenta        El monto total a cobrar por esta comanda.
     */
    public Comanda(Long idComanda, String folio, LocalDate fechaHoraCreacion, EstadoComanda estado, Double totalVenta) {
        this.idComanda = idComanda;
        this.folio = folio;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.estado = estado;
        this.totalVenta = totalVenta;
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
    
    public Long getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(Long idComanda) {
        this.idComanda = idComanda;
    }
    
}
