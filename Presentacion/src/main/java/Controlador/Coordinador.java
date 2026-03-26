package Controlador;

import BOs.ClienteBO;
import Pantallas.FrmClientesFrecuentes;
import Pantallas.FrmMenuAcceso;
import Pantallas.FrmRegistrarClienteFrecuente;

/**
 *
 * @author Mariana
 */
public class Coordinador {
    
    // Capa Negocio (BOs)
    private final ClienteBO clienteBO;
    
    // Capa de Presentación (Pantallas)
    private FrmMenuAcceso frmMenuAcceso;
    private FrmClientesFrecuentes frmGestionarClientesFrecuentes;
    private FrmRegistrarClienteFrecuente frmRegistrarClientesFrecuentes;
    
    public Coordinador() {
        this.clienteBO = ClienteBO.getInstance();
    }
    
    /**
     * Inicia la aplicación mostrando el menú principal.
     */
    public void iniciarSistema() {
        if (frmMenuAcceso == null) {
            frmMenuAcceso = new FrmMenuAcceso(this);
        }
        frmMenuAcceso.setVisible(true);
    }
    
    /**
     * Hace visible la pantalla principal de gestión de clientes frecuentes. 
     */
    public void mostrarGestionarClientesFrecuentes() {
        if (frmMenuAcceso != null) {
            frmMenuAcceso.setVisible(false);
        }
        if (frmGestionarClientesFrecuentes == null) {
            frmGestionarClientesFrecuentes = new FrmClientesFrecuentes(this);
        }
        frmGestionarClientesFrecuentes.setVisible(true);
        frmGestionarClientesFrecuentes.toFront();
    }
    
    /**
     * Muestra la pantalla para registrar un nuevo cliente frecuente.
     */
    public void mostrarRegistroClienteFrecuente() {
        if (frmGestionarClientesFrecuentes != null) frmGestionarClientesFrecuentes.setVisible(false);
        if (frmRegistrarClientesFrecuentes == null) {
            frmRegistrarClientesFrecuentes = new FrmRegistrarClienteFrecuente(this);
        }
        frmRegistrarClientesFrecuentes.setVisible(true);
        frmRegistrarClientesFrecuentes.toFront();
    }
    
    /**
    * Cierra la pantalla de registro y regresa a la gestión de clientes.
    */
   public void regresarAGestionClientes() {
       if (frmRegistrarClientesFrecuentes != null) {
           frmRegistrarClientesFrecuentes.setVisible(false);
           frmRegistrarClientesFrecuentes.dispose(); 
       }
       if (frmGestionarClientesFrecuentes != null) {
           frmGestionarClientesFrecuentes.setVisible(true);
           frmGestionarClientesFrecuentes.toFront();
       }
   }
   
   /**
    * Cierra la sesión actual y regresa al menú de acceso.
    */
   public void cerrarSesion() {
       if (frmGestionarClientesFrecuentes != null) {
           frmGestionarClientesFrecuentes.setVisible(false);
       }
       if (frmRegistrarClientesFrecuentes != null) {
           frmRegistrarClientesFrecuentes.setVisible(false);
       }
       if (frmMenuAcceso == null) {
           frmMenuAcceso = new FrmMenuAcceso(this);
       }
       frmMenuAcceso.setVisible(true);
   }

}
