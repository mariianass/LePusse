/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validadores;

import dtos.ClienteDTO;
import excepciones.NegocioException;

/**
 * Clase de utilidad encargada de realizar las validaciones de negocio para la
 * entidad Cliente. Verifica que la información obligatoria cumpla con las
 * reglas necesarias antes de ser procesada por la capa de negocio.
 *
 * @author regina, mariana e isaac.
 */
public class ValidadorCliente {

    /**
     * Valida la información de un cliente.
     *
     * @param clienteDTO DTO con la información del cliente.
     * @throws NegocioException Si algún dato obligatorio no cumple con las
     * reglas.
     */
    public static void validar(ClienteDTO clienteDTO) throws NegocioException {
        if (clienteDTO == null) {
            throw new NegocioException("El cliente no puede ser nulo.");
        }

        if (clienteDTO.getNombre() == null || clienteDTO.getNombre().trim().isEmpty()) {
            throw new NegocioException("El nombre es obligatorio.");
        }

        if (clienteDTO.getApellidoPaterno() == null || clienteDTO.getApellidoPaterno().trim().isEmpty()) {
            throw new NegocioException("El apellido paterno es obligatorio.");
        }

        if (clienteDTO.getApellidoMaterno() == null || clienteDTO.getApellidoMaterno().trim().isEmpty()) {
            throw new NegocioException("El apellido materno es obligatorio.");
        }

        if (clienteDTO.getTelefono() == null || clienteDTO.getTelefono().trim().isEmpty()) {
            throw new NegocioException("El teléfono es obligatorio.");
        }

        if (clienteDTO.getNombre().length() > 100) {
            throw new NegocioException("El nombre no puede exceder los 100 caracteres.");
        }

        if (clienteDTO.getApellidoPaterno().length() > 100) {
            throw new NegocioException("El apellido paterno no puede exceder los 100 caracteres.");
        }

        if (clienteDTO.getApellidoMaterno().length() > 100) {
            throw new NegocioException("El apellido materno no puede exceder los 100 caracteres.");
        }

        if (clienteDTO.getCorreoElectronico() != null && clienteDTO.getCorreoElectronico().length() > 100) {
            throw new NegocioException("El correo electrónico no puede exceder los 100 caracteres.");
        }
    }
}
