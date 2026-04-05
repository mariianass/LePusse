/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.IngredienteDTO;
import enums.UnidadMedida;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de negocio para ingredientes.
 * 
 * @author regina, mariana e isaac
 */
public interface IIngredienteBO {
    
    List<IngredienteDTO> buscarPorNombreYUnidad(String nombre, UnidadMedida unidad) throws NegocioException;
    IngredienteDTO buscarPorId(Long id) throws NegocioException;
}
