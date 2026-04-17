/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validadores;

import dtos.DetalleProductoIngredienteDTO;
import dtos.ProductoDTO;
import excepciones.NegocioException;

/**
 * Clase utilitaria encargada de validar la información de los productos
 * antes de ser procesados por la capa de negocio.
 * 
 * Verifica que los datos del producto cumplan con las reglas establecidas,
 * como campos obligatorios, longitudes máximas y valores válidos.
 * 
 * También valida la lista de ingredientes asociados al producto,
 * asegurando que cada detalle sea correcto.
 * 
 * Esta clase no puede ser instanciada.
 *
 * @author Regina, Mariana e Isaac
 */
public class ValidadorProducto {

    /**
     * Constructor privado para evitar la creación de instancias.
     */
    private ValidadorProducto() {
    }

    /**
     * Valida la información de un producto antes de su procesamiento.
     * 
     * Comprueba que:
     * <ul>
     *     <li>El producto no sea nulo.</li>
     *     <li>El nombre y descripción sean válidos y no excedan la longitud permitida.</li>
     *     <li>El precio sea mayor que cero.</li>
     *     <li>El tipo, disponibilidad y estado estén definidos.</li>
     *     <li>Exista al menos un ingrediente asociado.</li>
     *     <li>Cada ingrediente tenga datos válidos.</li>
     * </ul>
     * 
     * @param productoDTO producto a validar.
     * @throws NegocioException si alguna regla de validación no se cumple.
     */
    public static void validar(ProductoDTO productoDTO) throws NegocioException {
        if (productoDTO == null) {
            throw new NegocioException("El producto no puede ser nulo.");
        }

        if (productoDTO.getNombre() == null || productoDTO.getNombre().isBlank()) {
            throw new NegocioException("El nombre del producto es obligatorio.");
        }

        if (productoDTO.getNombre().length() > 100) {
            throw new NegocioException("El nombre del producto no puede exceder 100 caracteres.");
        }

        if (productoDTO.getDescripcion() == null || productoDTO.getDescripcion().isBlank()) {
            throw new NegocioException("La descripción del producto es obligatoria.");
        }

        if (productoDTO.getDescripcion().length() > 255) {
            throw new NegocioException("La descripción del producto no puede exceder 255 caracteres.");
        }

        if (productoDTO.getPrecio() == null) {
            throw new NegocioException("El precio del producto es obligatorio.");
        }

        if (productoDTO.getPrecio() <= 0) {
            throw new NegocioException("El precio del producto debe ser mayor que cero.");
        }

        if (productoDTO.getTipo() == null) {
            throw new NegocioException("El tipo de producto es obligatorio.");
        }

        if (productoDTO.getDisponibilidad() == null) {
            throw new NegocioException("La disponibilidad del producto es obligatoria.");
        }

        if (productoDTO.getActivo() == null) {
            throw new NegocioException("El estado del producto es obligatorio.");
        }

        if (productoDTO.getDetallesIngredientes() == null || productoDTO.getDetallesIngredientes().isEmpty()) {
            throw new NegocioException("El producto debe tener al menos un ingrediente.");
        }

        for (DetalleProductoIngredienteDTO detalle : productoDTO.getDetallesIngredientes()) {
            if (detalle == null) {
                throw new NegocioException("El detalle del ingrediente no puede ser nulo.");
            }

            if (detalle.getIngrediente() == null || detalle.getIngrediente().getIdIngrediente() == null) {
                throw new NegocioException("Cada detalle debe tener un ingrediente válido.");
            }

            if (detalle.getCantidadRequerida() == null) {
                throw new NegocioException("La cantidad requerida del ingrediente es obligatoria.");
            }

            if (detalle.getCantidadRequerida() <= 0) {
                throw new NegocioException("La cantidad requerida debe ser mayor que cero.");
            }
        }
    }
}