/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepción personalizada para representar errores ocurridos en la capa de
 * negocio del sistema.
 *
 * @author regina, mariana e isaac.
 */
public class NegocioException extends Exception {

    /**
     * Constructor que crea una nueva excepción de negocio con un mensaje
     * descriptivo.
     *
     * @param message Mensaje descriptivo del error.
     */
    public NegocioException(String message) {
        super(message);
    }

    /**
     * Constructor que crea una nueva excepción de negocio con un mensaje
     * descriptivo y la causa original del error.
     *
     * @param message Mensaje descriptivo del error.
     * @param cause Causa original de la excepción.
     */
    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}
