package Inicio;

import Controlador.Coordinador;

/**
 * @author Mariana
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