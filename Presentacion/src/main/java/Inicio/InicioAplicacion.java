package Inicio;

import Controlador.Coordinador;

/**
 *
 * @author Mariana
 */
public class InicioAplicacion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){}
        // Iniciamos el Coordinador
        Coordinador coordinador = new Coordinador();       
        // Abrimos la pantalla inicial
        coordinador.iniciarSistema();
    }
    
}
