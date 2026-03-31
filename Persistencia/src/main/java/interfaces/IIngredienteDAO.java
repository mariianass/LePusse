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
    
    void guardar(Ingrediente ingrediente) throws PersistenciaException;
    boolean eliminar(Long id) throws PersistenciaException;
    Ingrediente buscarPorId(Long id);
    List<Ingrediente> buscarTodos();
    List<Ingrediente> buscarPorNombre(String nombre);
    List<Ingrediente> buscarPorUnidad(UnidadMedida unidad);
    boolean existeDuplicado(String nombre, UnidadMedida unidad) throws PersistenciaException;
    void actualizarStock(Long id, Double nuevoStock);
    
}
