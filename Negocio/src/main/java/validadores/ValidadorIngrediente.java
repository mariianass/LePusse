/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validadores;

import dtos.IngredienteDTO;
import excepciones.NegocioException;

/**
 * Clase utilitaria para validar la información de los ingredientes antes de ser
 * procesados por la capa de negocio.
 *
 * @author regina, mariana e isaac
 */
public class ValidadorIngrediente {

    /**
     * Constructor privado para evitar instancias.
     */
    private ValidadorIngrediente() {
    }

    /**
     * Valida los datos obligatorios de un ingrediente.
     *
     * @param ingredienteDTO DTO del ingrediente a validar.
     * @throws NegocioException Si alguna validación no se cumple.
     */
    public static void validar(IngredienteDTO ingredienteDTO) throws NegocioException {
        if (ingredienteDTO == null) {
            throw new NegocioException("El ingrediente no puede ser nulo.");
        }

        validarNombre(ingredienteDTO.getNombre());
        validarUnidadMedida(ingredienteDTO);
        validarStockActual(ingredienteDTO);
        validarUmbral(ingredienteDTO);
    }

    /**
     * Valida el nombre del ingrediente.
     *
     * @param nombre Nombre del ingrediente.
     * @throws NegocioException Si el nombre es inválido.
     */
    private static void validarNombre(String nombre) throws NegocioException {
        if (nombre == null || nombre.isBlank()) {
            throw new NegocioException("El nombre del ingrediente es obligatorio.");
        }

        if (nombre.length() > 100) {
            throw new NegocioException("El nombre del ingrediente no puede exceder 100 caracteres.");
        }
    }

    /**
     * Valida que la unidad de medida haya sido seleccionada.
     *
     * @param ingredienteDTO DTO del ingrediente.
     * @throws NegocioException Si la unidad es nula.
     */
    private static void validarUnidadMedida(IngredienteDTO ingredienteDTO) throws NegocioException {
        if (ingredienteDTO.getUnidadMedida() == null) {
            throw new NegocioException("La unidad de medida es obligatoria.");
        }
    }

    /**
     * Valida el stock actual del ingrediente.
     *
     * @param ingredienteDTO DTO del ingrediente.
     * @throws NegocioException Si el stock es inválido.
     */
    private static void validarStockActual(IngredienteDTO ingredienteDTO) throws NegocioException {
        if (ingredienteDTO.getStockActual() == null) {
            throw new NegocioException("La cantidad en inventario es obligatoria.");
        }

        if (ingredienteDTO.getStockActual() < 0) {
            throw new NegocioException("La cantidad en inventario no puede ser negativa.");
        }
    }

    /**
     * Valida el umbral del ingrediente.
     *
     * @param ingredienteDTO DTO del ingrediente.
     * @throws NegocioException Si el umbral es inválido.
     */
    private static void validarUmbral(IngredienteDTO ingredienteDTO) throws NegocioException {
        if (ingredienteDTO.getUmbral() == null) {
            throw new NegocioException("El umbral es obligatorio.");
        }

        if (ingredienteDTO.getUmbral() < 0) {
            throw new NegocioException("El umbral no puede ser negativo.");
        }
    }
}
