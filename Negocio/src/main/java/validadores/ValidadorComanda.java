/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validadores;

import dtos.ComandaDTO;
import dtos.DetalleComandaDTO;
import excepciones.NegocioException;

/**
 * Clase de utilidad encargada de realizar las validaciones de negocio para la
 * entidad Comanda. Verifica que la información obligatoria cumpla con las
 * reglas necesarias antes de ser procesada por la capa de negocio.
 *
 * @author regina, mariana e isaac.
 */
public class ValidadorComanda {

    /**
     * Valida la información de una comanda.
     *
     * @param comandaDTO DTO con la información de la comanda.
     * @throws NegocioException Si algún dato obligatorio no cumple con las
     * reglas.
     */
    public static void validar(ComandaDTO comandaDTO) throws NegocioException {
        if (comandaDTO == null) {
            throw new NegocioException("La comanda no puede ser nula.");
        }

        if (comandaDTO.getIdMesa() == null) {
            throw new NegocioException("La mesa es obligatoria.");
        }

        if (comandaDTO.getEstado() == null) {
            throw new NegocioException("El estado de la comanda es obligatorio.");
        }

        if (comandaDTO.getDetalles() == null || comandaDTO.getDetalles().isEmpty()) {
            throw new NegocioException("La comanda debe contener al menos un producto.");
        }

        for (DetalleComandaDTO detalle : comandaDTO.getDetalles()) {
            if (detalle == null) {
                throw new NegocioException("Uno de los detalles de la comanda es nulo.");
            }

            if (detalle.getIdProducto() == null) {
                throw new NegocioException("El producto en el detalle es obligatorio.");
            }

            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
                throw new NegocioException("La cantidad de cada producto debe ser mayor que cero.");
            }

            if (detalle.getComentarioEspecial() != null && detalle.getComentarioEspecial().length() > 255) {
                throw new NegocioException("El comentario especial no puede exceder los 255 caracteres.");
            }

            if (detalle.getPrecio() == null || detalle.getPrecio() < 0) {
                throw new NegocioException("El precio del detalle debe ser válido.");
            }

            if (detalle.getSubtotal() == null || detalle.getSubtotal() < 0) {
                throw new NegocioException("El subtotal del detalle debe ser válido.");
            }

            if (detalle.getNombreProducto() != null && detalle.getNombreProducto().length() > 100) {
                throw new NegocioException("El nombre del producto no puede exceder los 100 caracteres.");
            }
        }
    }
}
