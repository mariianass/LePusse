package Controlador;

import BOs.ClienteFrecuenteBO;
import Pantallas.FrmClientesFrecuentes;
import Pantallas.FrmEditarClienteFrecuente;
import Pantallas.FrmMenuAcceso;
import Pantallas.FrmRegistrarClienteFrecuente;
import dtos.ClienteFrecuenteDTO;
import java.util.List;

/**
 * Clase Controladora central del sistema Restaurante Le Pusse.
 * Gestiona la navegación entre pantallas y la comunicación con la lógica de negocio.
 * @author Mariana, Isaac y Regina
 */
public class Coordinador {
    
    // Capa Negocio (BOs)
    private final ClienteFrecuenteBO clienteFrecuenteBO;
    
    // Capa de Presentación (Pantallas)
    private FrmMenuAcceso frmMenuAcceso;
    private FrmClientesFrecuentes frmGestionarClientesFrecuentes;
    private FrmRegistrarClienteFrecuente frmRegistrarClientesFrecuentes;
    private FrmEditarClienteFrecuente frmEditarClienteFrecuente;
    
    /**
     * Constructor que inicializa la instancia de la lógica de negocio.
     */
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
   
   /**
     * Finaliza la ventana de edición y retorna a la vista de gestión,
     * liberando recursos de memoria.
     */
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
     * Solicita a la capa de negocio persistir un nuevo cliente frecuente.
     * @param cliente Objeto DTO con la información del cliente.
     * @throws Exception Si ocurre un error durante el guardado.
     */
   public void registrarClienteFrecuente(ClienteFrecuenteDTO cliente) throws Exception{
        try {
            clienteFrecuenteBO.guardar(cliente);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
   }
   
    /**
     * Obtiene la lista completa de clientes frecuentes registrados.
     * @return Lista de ClienteFrecuenteDTO.
     * @throws Exception Si falla la consulta en la capa de negocio.
     */
    public List<ClienteFrecuenteDTO> obtenerClientesFrecuentes() throws Exception {
        try {
            return clienteFrecuenteBO.obtenerTodos();
        } catch (Exception ex) {
            throw new Exception("Error al obtener los clientes frecuentes.", ex);
        }
    }
    
    /**
     * Busca un cliente por ID y abre el formulario de edición con sus datos.
     * @param idCliente Identificador único del cliente a editar.
     */
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
    
    /**
     * Envía los cambios de un cliente existente a la capa de negocio para su actualización.
     * @param cliente DTO con la información actualizada.
     * @throws Exception Si ocurre un error en la actualización.
     */
    public void actualizarClienteFrecuente(ClienteFrecuenteDTO cliente) throws Exception {
        try {
            clienteFrecuenteBO.editar(cliente);
        } catch (Exception ex) {
            throw new Exception("Error al actualizar el cliente frecuente.", ex);
        }
    }
    
    /**
     * Realiza una búsqueda filtrada de clientes frecuentes en tiempo real.
     * @param filtro Cadena de texto (nombre, teléfono o correo).
     * @return Lista de clientes que coinciden con el filtro.
     * @throws Exception Si falla la búsqueda en persistencia.
     */
    public List<ClienteFrecuenteDTO> buscarClientesPorFiltro(String filtro) throws Exception {
        try {
            return clienteFrecuenteBO.buscarPorFiltros(filtro);
        } catch (Exception e) {
            throw new Exception("Error al filtrar los clientes.", e);
        }
    }

}
