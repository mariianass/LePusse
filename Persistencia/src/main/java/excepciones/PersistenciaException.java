/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepción personalizada para manejar los errores relacionados con la capa de persistencia.
 * @author regina, mariana e isaac
 */
public class PersistenciaException extends Exception{

    /**
     * Construye una nueva excepción de persistencia sin un mensaje de detalle.
     */
    public PersistenciaException() {
        super();
    }
    
    /**
     * Construye una nueva excepción de persistencia con el mensaje de detalle especificado.
     * @param message El mensaje que describe el error o la razón de la excepción.
     */
    public PersistenciaException(String message) {
        super(message);
    }

    /**
     * Construye una nueva excepción de persistencia con el mensaje de detalle 
     * y la causa original especificados.
     * @param message El mensaje que describe el error o la razón de la excepción.
     * @param cause La causa original del error.
     */
    public PersistenciaException(String message, Throwable cause) {
        super(message, cause);
    }   
    
}
