/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validadores;

import dtos.ClienteFrecuenteDTO;
import excepciones.NegocioException;

/**
 * Clase de utilidad encargada de realizar las validaciones de negocio para la
 * entidad ClienteFrecuente. Verifica tanto los datos heredados de Cliente como
 * los atributos específicos de un cliente frecuente.
 *
 * @author regina, mariana e isaac.
 */
public class ValidadorClienteFrecuente {

    /**
     * Valida la información de un cliente frecuente.
     *
     * @param clienteFrecuenteDTO DTO con la información del cliente frecuente.
     * @throws NegocioException Si algún dato obligatorio no cumple con las
     * reglas.
     */
    public static void validar(ClienteFrecuenteDTO clienteFrecuenteDTO) throws NegocioException {
        if (clienteFrecuenteDTO == null) {
            throw new NegocioException("El cliente frecuente no puede ser nulo.");
        }

        if (clienteFrecuenteDTO.getNombre() == null || clienteFrecuenteDTO.getNombre().trim().isEmpty()) {
            throw new NegocioException("El nombre es obligatorio.");
        }

        if (clienteFrecuenteDTO.getApellidoPaterno() == null || clienteFrecuenteDTO.getApellidoPaterno().trim().isEmpty()) {
            throw new NegocioException("El apellido paterno es obligatorio.");
        }

        if (clienteFrecuenteDTO.getApellidoMaterno() == null || clienteFrecuenteDTO.getApellidoMaterno().trim().isEmpty()) {
            throw new NegocioException("El apellido materno es obligatorio.");
        }

        if (clienteFrecuenteDTO.getTelefono() == null || clienteFrecuenteDTO.getTelefono().trim().isEmpty()) {
            throw new NegocioException("El teléfono es obligatorio.");
        }

        if (clienteFrecuenteDTO.getFechaRegistro() == null) {
            throw new NegocioException("La fecha de registro es obligatoria.");
        }

//        if (clienteFrecuenteDTO.getNumeroVisitas() == null) {
//            throw new NegocioException("El número de visitas es obligatorio.");
//        }
//
//        if (clienteFrecuenteDTO.getPuntosFidelidad() == null) {
//            throw new NegocioException("Los puntos de fidelidad son obligatorios.");
//        }
//
//        if (clienteFrecuenteDTO.getNumeroVisitas() < 0) {
//            throw new NegocioException("El número de visitas no puede ser negativo.");
//        }
//
//        if (clienteFrecuenteDTO.getPuntosFidelidad() < 0) {
//            throw new NegocioException("Los puntos de fidelidad no pueden ser negativos.");
//        }

        if (clienteFrecuenteDTO.getNombre().length() > 100) {
            throw new NegocioException("El nombre no puede exceder los 100 caracteres.");
        }

        if (clienteFrecuenteDTO.getApellidoPaterno().length() > 100) {
            throw new NegocioException("El apellido paterno no puede exceder los 100 caracteres.");
        }

        if (clienteFrecuenteDTO.getApellidoMaterno().length() > 100) {
            throw new NegocioException("El apellido materno no puede exceder los 100 caracteres.");
        }

        if (clienteFrecuenteDTO.getCorreoElectronico() != null && clienteFrecuenteDTO.getCorreoElectronico().length() > 100) {
            throw new NegocioException("El correo electrónico no puede exceder los 100 caracteres.");
        }
    }
}
