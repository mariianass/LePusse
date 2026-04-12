/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.EstadoMesa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad que representa una mesa dentro del sistema. Cada mesa puede
 * encontrarse disponible, ocupada o inactiva, y puede estar asociada
 * históricamente a múltiples comandas.
 *
 * @author regina, mariana e isaac
 */
@Entity
@Table(name = "mesas")
public class Mesa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa")
    private Long idMesa;

    @Column(name = "numero_mesa", nullable = false, unique = true)
    private Integer numeroMesa;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMesa estado;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
    private List<Comanda> comandas = new ArrayList<>();

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Mesa() {
    }

    /**
     * Constructor con todos los atributos de la mesa.
     *
     * @param idMesa Identificador único de la mesa.
     * @param numeroMesa Número asignado a la mesa.
     * @param estado Estado actual de la mesa.
     * @param comandas Lista de comandas asociadas históricamente a la mesa.
     */
    public Mesa(Long idMesa, Integer numeroMesa, EstadoMesa estado, List<Comanda> comandas) {
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.estado = estado;
        this.comandas = comandas != null ? comandas : new ArrayList<>();
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

    public List<Comanda> getComandas() {
        return comandas;
    }

    public void setComandas(List<Comanda> comandas) {
        this.comandas = comandas;
    }

    /**
     * Agrega una comanda a la mesa y establece la relación.
     *
     * @param comanda Comanda a agregar.
     */
    public void agregarComanda(Comanda comanda) {
        if (comanda != null) {
            comanda.setMesa(this);
            this.comandas.add(comanda);
        }
    }

    /**
     * Elimina una comanda de la mesa.
     *
     * @param comanda Comanda a eliminar.
     */
    public void eliminarComanda(Comanda comanda) {
        if (comanda != null) {
            this.comandas.remove(comanda);
            comanda.setMesa(null);
        }
    }

    @Override
    public String toString() {
        return "Mesa{" + "idMesa=" + idMesa + ", numeroMesa=" + numeroMesa + ", estado=" + estado + ", comandas=" + comandas + '}';
    }

}
