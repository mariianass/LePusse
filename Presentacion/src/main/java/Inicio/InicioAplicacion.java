package Inicio;

import Controlador.Coordinador;

/**
 * Clase de entrada principal que arranca la ejecución de la aplicación.
 * Se encarga de configurar el aspecto visual del sistema e inicializar el
 * flujo de control a través del Coordinador.
 * @author Mariana, regina e isaac
 */
public class InicioAplicacion {

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        Coordinador coordinador = new Coordinador();

        coordinador.iniciarSistema();
    }

}