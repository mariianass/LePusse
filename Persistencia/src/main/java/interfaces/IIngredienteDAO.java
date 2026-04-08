/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Ingrediente;
import enums.UnidadMedida;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Ingrediente.
 * 
 * @author regina, mariana e isaac
 */
public interface IIngredienteDAO {
    
    Ingrediente guardar(Ingrediente ingrediente) throws PersistenciaException;
    boolean eliminar(Long id) throws PersistenciaException;
    Ingrediente editar(Ingrediente ingrediente) throws PersistenciaException;
    Ingrediente buscarPorId(Long id) throws PersistenciaException;
    List<Ingrediente> buscarPorNombreYUnidad(String nombre, UnidadMedida unidad) throws PersistenciaException;
    boolean existeDuplicado(String nombre, UnidadMedida unidad, Long id) throws PersistenciaException;
    void actualizarStock(Long id, Double nuevoStock)throws PersistenciaException;
    
}
