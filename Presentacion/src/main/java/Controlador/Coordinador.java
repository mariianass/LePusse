package Controlador;

import BOs.ClienteFrecuenteBO;
import Pantallas.FrmClientesFrecuentes;
import Pantallas.FrmEditarClienteFrecuente;
import Pantallas.FrmMenuAcceso;
import Pantallas.FrmRegistrarClienteFrecuente;
import dtos.ClienteFrecuenteDTO;
import java.util.List;

/**
 *
 * @author Mariana
 */
public class Coordinador {
    
    // Capa Negocio (BOs)
    private final ClienteFrecuenteBO clienteFrecuenteBO;
    
    // Capa de Presentación (Pantallas)
    private FrmMenuAcceso frmMenuAcceso;
    private FrmClientesFrecuentes frmGestionarClientesFrecuentes;
    private FrmRegistrarClienteFrecuente frmRegistrarClientesFrecuentes;
    private FrmEditarClienteFrecuente frmEditarClienteFrecuente;
    
    public Coordinador() {
       this.clienteFrecuenteBO = ClienteFrecuenteBO.getInstance();
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
        frmGestionarClientesFrecuentes.recargarTabla();
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
           frmRegistrarClientesFrecuentes = null;
       }
       if (frmGestionarClientesFrecuentes != null) {
           frmGestionarClientesFrecuentes.setVisible(true);
           frmGestionarClientesFrecuentes.toFront();
           frmGestionarClientesFrecuentes.recargarTabla();
       }
   }
   
   public void regresarDesdeEditarCliente() {
        if (frmEditarClienteFrecuente != null) {
            frmEditarClienteFrecuente.setVisible(false);
            frmEditarClienteFrecuente.dispose();
            frmEditarClienteFrecuente = null;
        }

        if (frmGestionarClientesFrecuentes != null) {
            frmGestionarClientesFrecuentes.setVisible(true);
            frmGestionarClientesFrecuentes.toFront();
            frmGestionarClientesFrecuentes.recargarTabla();
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
   
   /**
    * 
    */
   public void registrarClienteFrecuente(ClienteFrecuenteDTO cliente) throws Exception{
        try {
            clienteFrecuenteBO.guardar(cliente);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
   }
   
    /**
     *
     * @return
     * @throws Exception
     */
    public List<ClienteFrecuenteDTO> obtenerClientesFrecuentes() throws Exception {
        try {
            return clienteFrecuenteBO.obtenerTodos();
        } catch (Exception ex) {
            throw new Exception("Error al obtener los clientes frecuentes.", ex);
        }
    }
    
    public void mostrarEditarClienteFrecuente(Long idCliente) {
        try {
            ClienteFrecuenteDTO cliente = clienteFrecuenteBO.buscarPorId(idCliente);

            if (cliente == null) {
                throw new Exception("No se encontró el cliente seleccionado.");
            }

            if (frmGestionarClientesFrecuentes != null) {
                frmGestionarClientesFrecuentes.setVisible(false);
            }

            frmEditarClienteFrecuente = new FrmEditarClienteFrecuente(this, cliente);
            frmEditarClienteFrecuente.setVisible(true);
            frmEditarClienteFrecuente.toFront();

        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Error al abrir la pantalla de edición: " + ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void actualizarClienteFrecuente(ClienteFrecuenteDTO cliente) throws Exception {
        try {
            clienteFrecuenteBO.editar(cliente);
        } catch (Exception ex) {
            throw new Exception("Error al actualizar el cliente frecuente.", ex);
        }
    }

}
