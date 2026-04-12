/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.EstadoComanda;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad que representa una Comanda dentro del sistema. Se mapea a la tabla
 * "comandas" en la base de datos y contiene la información general de una
 * orden, incluyendo su folio, fecha y hora de creación, estado, total de venta,
 * la mesa asignada, el cliente asociado y sus detalles.
 *
 * @author regina, mariana e isaac
 */
@Entity
@Table(name = "comandas")
public class Comanda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comanda")
    private Long idComanda;

    @Column(name = "folio", nullable = false, unique = true, length = 15)
    private String folio;

    @Column(name = "fecha_hora_creacion", nullable = false)
    private LocalDateTime fechaHoraCreacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoComanda estado;

    @Column(name = "total_venta", nullable = false)
    private Double totalVenta;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_mesa", nullable = false)
    private Mesa mesa;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_cliente", nullable = true)
    private Cliente cliente;

    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleComanda> detalles = new ArrayList<>();

    /**
     * Constructor por defecto requerido por JPA. Crea una instancia vacía de
     * Comanda.
     */
    public Comanda() {
    }

    /**
     * Constructor con todos los atributos de la comanda.
     *
     * @param idComanda El identificador único de la comanda.
     * @param folio El folio alfanumérico o numérico de la comanda.
     * @param fechaHoraCreacion La fecha y hora de creación de la comanda.
     * @param estado El estado en el que se encuentra la orden.
     * @param totalVenta El monto total a cobrar por esta comanda.
     * @param mesa La mesa asociada a la comanda.
     * @param cliente El cliente asociado a la comanda.
     * @param detalles La lista de detalles registrados en la comanda.
     */
    public Comanda(Long idComanda, String folio, LocalDateTime fechaHoraCreacion, EstadoComanda estado, 
                   Double totalVenta, Mesa mesa, Cliente cliente, List<DetalleComanda> detalles) {
        this.idComanda = idComanda;
        this.folio = folio;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.estado = estado;
        this.totalVenta = totalVenta;
        this.mesa = mesa;
        this.cliente = cliente;
        if (detalles != null) {
            setDetalles(detalles);
        } else {
            this.detalles = new ArrayList<>();
        }
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

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetalleComanda> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleComanda> detalles) {
        this.detalles = detalles;

        if (this.detalles != null) {
            for (DetalleComanda detalle : this.detalles) {
                detalle.setComanda(this);
            }
        }
    }

    public Long getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(Long idComanda) {
        this.idComanda = idComanda;
    }

    public void agregarDetalle(DetalleComanda detalle) {
        if (detalle != null) {
            detalle.setComanda(this);
            this.detalles.add(detalle);
        }
    }

    public void eliminarDetalle(DetalleComanda detalle) {
        if (detalle != null) {
            this.detalles.remove(detalle);
            detalle.setComanda(null);
        }
    }

    @Override
    public String toString() {
        return "Comanda{" + "idComanda=" + idComanda + ", folio=" + folio + ", fechaHoraCreacion=" + fechaHoraCreacion + ", estado=" + estado + ", totalVenta=" + totalVenta + ", mesa=" + mesa + ", cliente=" + cliente + ", detalles=" + detalles + '}';
    }

}
