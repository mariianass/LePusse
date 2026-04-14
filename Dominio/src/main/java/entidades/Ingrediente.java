/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.UnidadMedida;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entidad que representa un ingrediente dentro del sistema.
 * @author regina, mariana e isaac
 */
@Entity
@Table(name = "ingredientes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre", "unidad_medida"})
})
public class Ingrediente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingrediente")
    private Long idIngrediente;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false)
    private UnidadMedida unidadMedida;
    
    @Column(name = "stock_actual", nullable = false)
    private Double stockActual;
    
    @Column(name = "umbral", nullable = false)
    private Double umbral;
    
    @OneToMany(mappedBy = "ingrediente")
    private List<DetalleProductoIngrediente> detallesIngredientes = new ArrayList<>();

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Ingrediente() {
    }

    /**
     * Constructor con todos los atributos para la creación manual de objetos.
     * @param idIngrediente Identificador único.
     * @param nombre Nombre descriptivo.
     * @param unidadMedida Constante de la unidad de medida.
     * @param stockActual Cantidad inicial en inventario.
     * @param umbral Límite mínimo para alertas.
     */
    public Ingrediente(Long idIngrediente, String nombre, UnidadMedida unidadMedida, Double stockActual, Double umbral) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.stockActual = stockActual;
        this.umbral = umbral;
    }
    

    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Double getStockActual() {
        return stockActual;
    }

    public void setStockActual(Double stockActual) {
        this.stockActual = stockActual;
    }

    public Double getUmbral() {
        return umbral;
    }

    public void setUmbral(Double umbral) {
        this.umbral = umbral;
    }

    public List<DetalleProductoIngrediente> getDetallesIngredientes() {
        return detallesIngredientes;
    }

    public void setDetallesIngredientes(List<DetalleProductoIngrediente> detallesIngredientes) {
        this.detallesIngredientes = detallesIngredientes;
    }
    

    @Override
    public String toString() {
        return "Ingrediente{" + "idIngrediente=" + idIngrediente + ", nombre=" + nombre + ", unidadMedida=" + unidadMedida + ", stockActual=" + stockActual + ", umbral=" + umbral + '}';
    }
      
    
}
