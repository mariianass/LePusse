/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Representa la entidad base de un Cliente en el sistema.
 * Esta clase está mapeada a la tabla "clientes" en la base de datos.
 * @author regina, mariana e isaac.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "clientes")
@DiscriminatorColumn(name = "tipo_cliente")
public class Cliente implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "apellido_paterno", nullable = false, length = 100)
    private String apellidoPaterno;
    
    @Column(name = "apellido_materno", nullable = false, length = 100)
    private String apellidoMaterno;
    
    @Column(name = "telefono", nullable = false, length = 12, unique = true)
    private String telefono;
    
    @Column(name = "correo_electronico", nullable = true, length = 100)
    private String correoElectronico;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Cliente() {
    }

    /**
     * Constructor que inicializa todos los atributos del cliente.
     *
     * @param id Identificador único del cliente.
     * @param nombre Nombres del cliente.
     * @param apellidoPaterno Apellido paterno del cliente.
     * @param apellidoMaterno Apellido materno del cliente.
     * @param telefono Número de teléfono del cliente .
     * @param correoElectronico Correo electrónico del cliente.
     */
    public Cliente(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String correoElectronico) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", telefono=" + telefono + ", correoElectronico=" + correoElectronico + '}';
    }
    
    
}
