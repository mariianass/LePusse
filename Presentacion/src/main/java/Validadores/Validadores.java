/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validadores;

import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * Clase de utilidad encargada de realizar las validaciones de entrada de datos 
 * para el módulo de Clientes Frecuentes.
 * @author regina, mariana e isaac
 */
public class Validadores {

    /**
    * Realiza una validación integral de los datos de un cliente frecuente.
    * Verifica campos obligatorios, longitudes máximas según JPA (100 chars),
    * formatos de texto, formato de teléfono
    * y formato de correo electrónico.
    * @param parent 
    * @param pNombre  El primer nombre del cliente (Obligatorio).
    * @param aPaterno El apellido paterno del cliente (Obligatorio).
    * @param aMaterno El apellido materno del cliente (Obligatorio).
    * @param tel      El número telefónico de 10 dígitos (Obligatorio).
    * @param correo   La dirección de correo electrónico (Opcional).
    * @return true si todos los datos cumplen con las reglas de validación, 
    * false si alguno falla.
    */
    public static boolean validarCliente(Component parent, String pNombre, String aPaterno, String aMaterno, String tel, String correo) {
        // Validar campos obligatorios vacíos
        if (pNombre.isEmpty() || aPaterno.isEmpty() || aMaterno.isEmpty() || tel.isEmpty()) {
            mostrarMensaje(parent, "Por favor, complete todos los campos obligatorios.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        //  Validar longitud y contenido de nombres/apellidos
        String regexNombres = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{2,100}$";

        if (!pNombre.matches(regexNombres)) {
            mostrarMensaje(parent, "El nombre debe tener entre 2 y 100 letras y no contener números.", "Nombre Inválido", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!aPaterno.matches(regexNombres)) {
            mostrarMensaje(parent, "El apellido paterno debe tener entre 2 y 100 letras.", "Apellido Inválido", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!aMaterno.matches(regexNombres)) {
            mostrarMensaje(parent, "El apellido materno debe tener entre 2 y 100 letras.", "Apellido Inválido", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar formato de teléfono (Exactamente 10 dígitos)
        if (!tel.matches("\\d{10}")) {
            mostrarMensaje(parent, "El teléfono debe contener exactamente 10 dígitos numéricos.", "Formato de Teléfono Inválido", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar formato de correo (Opcional, pero si existe, máximo 100 caracteres)
        if (!correo.isEmpty()) {
            if (correo.length() > 100 || !correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                mostrarMensaje(parent, "El correo electrónico no es válido o supera los 100 caracteres.", "Correo Inválido", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }
    
    /**
    * Valida los datos para el registro de ingredientes.
    * Reglas: Nombre obligatorio (2-100 chars), stock y umbral numéricos positivos.
    */
   public static boolean validarIngrediente(Component parent, String nombre, int indexUnidad, String stock, String umbral) {

       // Validar campos vacíos
       if (nombre.isEmpty() || stock.isEmpty() || umbral.isEmpty()) {
           mostrarMensaje(parent, "Por favor, complete todos los campos obligatorios.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
           return false;
       }

       // Validar que seleccionó una unidad (index 0 suele ser "Seleccione...")
       if (indexUnidad <= 0) {
           mostrarMensaje(parent, "Debe seleccionar una unidad de medida válida.", "Unidad no seleccionada", JOptionPane.WARNING_MESSAGE);
           return false;
       }

       // Validar longitud y formato del nombre (letras, números y espacios permitidos en ingredientes)
       if (nombre.length() < 2 || nombre.length() > 100) {
           mostrarMensaje(parent, "El nombre del ingrediente debe tener entre 2 y 100 caracteres.", "Nombre Inválido", JOptionPane.ERROR_MESSAGE);
           return false;
       }

       // Validar que stock y umbral sean números válidos y positivos
       try {
           double s = Double.parseDouble(stock);
           double u = Double.parseDouble(umbral);

           if (s < 0 || u < 0) {
               mostrarMensaje(parent, "La cantidad y el umbral no pueden ser valores negativos.", "Valor Inválido", JOptionPane.ERROR_MESSAGE);
               return false;
           }
       } catch (NumberFormatException e) {
           mostrarMensaje(parent, "La cantidad en inventario y el umbral deben ser números válidos.", "Formato Numérico Incorrecto", JOptionPane.ERROR_MESSAGE);
           return false;
       }

       return true;
   }

    /**
    * Centraliza la creación de cuadros de diálogo para notificar al usuario.
    * @param parent Componente padre.
    * @param mensaje Cuerpo del mensaje.
    * @param titulo Título de la ventana.
    * @param tipo Constante de JOptionPane.
    */
    private static void mostrarMensaje(Component parent, String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(parent, mensaje, titulo, tipo);
    }
    
    /**
    * Valida los filtros para el reporte de clientes.
    * @param parent Componente padre para el diálogo.
    * @param nombre El nombre del cliente (Opcional).
    * @param visitasStr El texto ingresado en el campo de visitas.
    * @return true si los datos son válidos.
    */
   public static boolean validarFiltrosCliente(Component parent, String nombre, String visitasStr) {

       if (!nombre.isEmpty()) {
           if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{2,100}$")) {
               mostrarMensaje(parent, "El nombre a filtrar debe contener solo letras y tener al menos 2 caracteres.", 
                              "Filtro de Nombre Inválido", JOptionPane.WARNING_MESSAGE);
               return false;
           }
       }

       if (!visitasStr.isEmpty()) {
           try {
               int visitas = Integer.parseInt(visitasStr);
               if (visitas < 0) {
                   mostrarMensaje(parent, "El número mínimo de visitas no puede ser negativo.", 
                                  "Valor Inválido", JOptionPane.ERROR_MESSAGE);
                   return false;
               }
           } catch (NumberFormatException e) {
               mostrarMensaje(parent, "El número de visitas debe ser un número entero válido.", 
                              "Formato Incorrecto", JOptionPane.ERROR_MESSAGE);
               return false;
           }
       }

       return true;
   }
    
    
}
