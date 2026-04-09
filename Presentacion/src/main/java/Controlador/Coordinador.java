package Controlador;

import BOs.ClienteFrecuenteBO;
import BOs.IngredienteBO;
import Pantallas.FrmClientesFrecuentes;
import Pantallas.FrmEditarClienteFrecuente;
import Pantallas.FrmEditarIngrediente;
import Pantallas.FrmIngredientes;
import Pantallas.FrmMenuAcceso;
import Pantallas.FrmNuevoIngrediente;
import Pantallas.FrmRegistrarClienteFrecuente;
import dtos.ClienteFrecuenteDTO;
import dtos.IngredienteDTO;
import enums.UnidadMedida;
import java.util.List;

/**
 * Clase Controladora central del sistema Restaurante Le Pusse. Gestiona la
 * navegación entre pantallas y la comunicación con la lógica de negocio.
 *
 * @author Mariana, Isaac y Regina
 */
public class Coordinador {

    // Capa Negocio (BOs)
    private final ClienteFrecuenteBO clienteFrecuenteBO;
    private final IngredienteBO ingredienteBO;

    // Capa de Presentación (Pantallas)
    private FrmMenuAcceso frmMenuAcceso;
    private FrmClientesFrecuentes frmGestionarClientesFrecuentes;
    private FrmRegistrarClienteFrecuente frmRegistrarClientesFrecuentes;
    private FrmEditarClienteFrecuente frmEditarClienteFrecuente;
    private FrmIngredientes frmIngredientes;
    private FrmNuevoIngrediente frmNuevoIngrediente;
    private FrmEditarIngrediente frmEditarIngrediente;


    /**
     * Constructor que inicializa la instancia de la lógica de negocio.
     */
    public Coordinador() {
        this.clienteFrecuenteBO = ClienteFrecuenteBO.getInstance();
        this.ingredienteBO = IngredienteBO.getInstance();
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
        
        ocultarTodasLasPantallas();
        
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
        
        ocultarTodasLasPantallas();
        
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
     * Finaliza la ventana de edición y retorna a la vista de gestión, liberando
     * recursos de memoria.
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
        
        ocultarTodasLasPantallas();
        
        if (frmMenuAcceso == null) {
            frmMenuAcceso = new FrmMenuAcceso(this);
        }

        frmMenuAcceso.setVisible(true);
    }

    /**
     * Solicita a la capa de negocio persistir un nuevo cliente frecuente.
     *
     * @param cliente Objeto DTO con la información del cliente.
     * @throws Exception Si ocurre un error durante el guardado.
     */
    public void registrarClienteFrecuente(ClienteFrecuenteDTO cliente) throws Exception {
        try {
            clienteFrecuenteBO.guardar(cliente);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    /**
     * Elimina un cliente frecuente del sistema.
     *
     * @param idCliente Identificador del cliente a eliminar.
     * @throws Exception Si ocurre un error durante la eliminación.
     */
    public void eliminarCliente(Long idCliente) throws Exception {
        try {
            clienteFrecuenteBO.eliminar(idCliente);
        } catch (Exception ex) {
            throw new Exception("Error al eliminar el cliente frecuente.", ex);
        }
    }

    /**
     * Obtiene la lista completa de clientes frecuentes registrados.
     *
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
     *
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
     * Envía los cambios de un cliente existente a la capa de negocio para su
     * actualización.
     *
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
     *
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

    /**
     * Hace visible la pantalla principal de gestión de ingredientes.
     */
    public void mostrarGestionarIngredientes() {
        
        ocultarTodasLasPantallas();
         
        if (frmIngredientes == null) {
            frmIngredientes = new FrmIngredientes(this, false);
        }
        frmIngredientes.setVisible(true);
        frmIngredientes.toFront();
        frmIngredientes.recargarTabla();
    }
    
    /**
    * Obtiene la lista completa de ingredientes registrados.
    *
    * @return Lista de IngredienteDTO.
    * @throws Exception Si falla la consulta en la capa de negocio.
    */
   public List<IngredienteDTO> obtenerIngredientes() throws Exception {
       try {
           return ingredienteBO.buscarPorNombreYUnidad("", null);
       } catch (Exception ex) {
           throw new Exception("Error al obtener los ingredientes.", ex);
       }
   }
    
    /**
     * Obtiene la lista de ingredientes aplicando filtros opcionales por nombre
     * y unidad de medida.
     *
     * @param nombre Nombre o parte del nombre del ingrediente.
     * @param unidad Unidad de medida seleccionada.
     * @return Lista de IngredienteDTO.
     * @throws Exception Si falla la consulta en la capa de negocio.
     */
    public List<IngredienteDTO> buscarIngredientesPorNombreYUnidad(String nombre, UnidadMedida unidad) throws Exception {
        try {
            return ingredienteBO.buscarPorNombreYUnidad(nombre, unidad);
        } catch (Exception ex) {
            throw new Exception("Error al obtener los ingredientes.", ex);
        }
    }
    
    /**
     * Busca un ingrediente por su identificador único.
     *
     * @param idIngrediente Identificador único del ingrediente.
     * @return IngredienteDTO encontrado.
     * @throws Exception Si ocurre un error durante la búsqueda.
     */
    public IngredienteDTO buscarIngredientePorId(Long idIngrediente) throws Exception {
        try {
            return ingredienteBO.buscarPorId(idIngrediente);
        } catch (Exception ex) {
            throw new Exception("Error al buscar el ingrediente.", ex);
        }
    }
    
    public void mostrarNuevoIngrediente() {
        ocultarTodasLasPantallas();

        if (frmNuevoIngrediente == null) {
            frmNuevoIngrediente = new FrmNuevoIngrediente(this);
        }
        frmNuevoIngrediente.setVisible(true);
        frmNuevoIngrediente.toFront();
    }
    
    /**
    * Solicita a la capa de negocio persistir un nuevo ingrediente.
    *
    * @param ingrediente Objeto DTO con la información del ingrediente.
    * @throws Exception Si ocurre un error durante el guardado.
    */
   public void registrarIngrediente(IngredienteDTO ingrediente) throws Exception {
       try {
           ingredienteBO.guardar(ingrediente);
       } catch (Exception ex) {
           throw new Exception(ex.getMessage());
       }
   }
    
    private void ocultarTodasLasPantallas() {
        if (frmMenuAcceso != null) {
            frmMenuAcceso.setVisible(false);
        }
        if (frmGestionarClientesFrecuentes != null) {
            frmGestionarClientesFrecuentes.setVisible(false);
        }
        if (frmRegistrarClientesFrecuentes != null) {
            frmRegistrarClientesFrecuentes.setVisible(false);
        }
        if (frmEditarClienteFrecuente != null) {
            frmEditarClienteFrecuente.setVisible(false);
        }
        if (frmIngredientes != null) {
            frmIngredientes.setVisible(false);
        }
        if (frmNuevoIngrediente != null) {
            frmNuevoIngrediente.setVisible(false);
        }
        if (frmEditarIngrediente != null) {
        frmEditarIngrediente.setVisible(false);
        frmEditarIngrediente.dispose();
        frmEditarIngrediente = null;
    }
    }
    
    /**
     * Busca un ingrediente por ID y abre el formulario de edición.
     * @param idIngrediente Identificador del ingrediente a editar.
     */
    public void mostrarEditarIngrediente(Long idIngrediente) {
        try {
            IngredienteDTO ingrediente = ingredienteBO.buscarPorId(idIngrediente);

            if (ingrediente == null) {
                throw new Exception("No se encontró el ingrediente seleccionado.");
            }

            ocultarTodasLasPantallas();

            frmEditarIngrediente = new FrmEditarIngrediente(this, ingrediente);
            frmEditarIngrediente.setVisible(true);
            frmEditarIngrediente.toFront();

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
     * Envía los cambios de un ingrediente a la capa de negocio para su actualización.
     * @param ingrediente DTO con la información actualizada.
     * @throws Exception Si ocurre un error en la validación o persistencia.
     */
    public void actualizarIngrediente(IngredienteDTO ingrediente) throws Exception {
        try {
            ingredienteBO.editar(ingrediente);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * Elimina un ingrediente del sistema.
     * @param idIngrediente ID del ingrediente a borrar.
     * @throws Exception Si el ingrediente está siendo usado o no existe.
     */
    public void eliminarIngrediente(Long idIngrediente) throws Exception {
        try {
            ingredienteBO.eliminar(idIngrediente);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * Regresa a la pantalla principal de ingredientes y refresca la tabla.
     */
    public void regresarAGestionIngredientes() {
        ocultarTodasLasPantallas();
        mostrarGestionarIngredientes();
    }
    
}
