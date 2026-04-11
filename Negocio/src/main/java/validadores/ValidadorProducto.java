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
 * @author regina, mariana e isaac
 */
public class ValidadorProducto {

    private ValidadorProducto() {
    }

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
