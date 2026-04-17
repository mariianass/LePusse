package Controlador;

import BOs.ClienteFrecuenteBO;
import BOs.ComandaBO;
import BOs.IngredienteBO;
import BOs.ProductoBO;
import BOs.ReporteBO;
import Pantallas.FrmCatalogoProductosComanda;
import Pantallas.FrmClientesFrecuentes;
import Pantallas.FrmComandas;
import Pantallas.FrmEditarClienteFrecuente;
import Pantallas.FrmEditarComanda;
import Pantallas.FrmEditarIngrediente;
import Pantallas.FrmEditarProducto;
import Pantallas.FrmIngredientes;
import Pantallas.FrmMenuAcceso;
import Pantallas.FrmNuevaComanda;
import Pantallas.FrmNuevoIngrediente;
import Pantallas.FrmNuevoProducto;
import Pantallas.FrmProductos;
import Pantallas.FrmReportes;
import Pantallas.frmRegistrarClienteFrecuente;
import dtos.ClienteFrecuenteDTO;
import dtos.ComandaDTO;
import dtos.DetalleComandaDTO;
import dtos.IngredienteDTO;
import dtos.MesaDTO;
import dtos.ProductoDTO;
import enums.UnidadMedida;
import enumsDTO.TipoProductoDTO;
import enumsDTO.UnidadMedidaDTO;
import excepciones.NegocioException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Clase controladora central del sistema Restaurante Le Pusse. Gestiona la
 * navegación entre pantallas y la comunicación con la lógica de negocio.
 *
 * @author Mariana, Isaac y Regina
 */
public class Coordinador {

    // Capa Negocio (BOs)
    private final ComandaBO comandaBO;
    private final ClienteFrecuenteBO clienteFrecuenteBO;
    private final IngredienteBO ingredienteBO;
    private final ProductoBO productoBO;
    private final ReporteBO reporteClienteBO;

    // Capa Presentación (Pantallas)
    private FrmComandas frmComandas;
    private FrmNuevaComanda frmNuevaComanda;
    private FrmEditarComanda frmEditarComanda;
    private FrmCatalogoProductosComanda frmCatalogoProductosComanda;
    private FrmMenuAcceso frmMenuAcceso;
    private FrmClientesFrecuentes frmGestionarClientesFrecuentes;
    private frmRegistrarClienteFrecuente frmRegistrarClientesFrecuentes;
    private FrmEditarClienteFrecuente frmEditarClienteFrecuente;

    private FrmIngredientes frmIngredientes;
    private FrmNuevoIngrediente frmNuevoIngrediente;
    private FrmEditarIngrediente frmEditarIngrediente;

    private FrmProductos frmProductos;
    private FrmNuevoProducto frmNuevoProducto;
    private FrmEditarProducto frmEditarProducto;

    private FrmReportes frmReportes;

    private boolean catalogoEnModoEdicion = false;

    /**
     * Constructor que inicializa la lógica de negocio.
     */
    public Coordinador() {
        this.comandaBO = ComandaBO.getInstance();
        this.clienteFrecuenteBO = ClienteFrecuenteBO.getInstance();
        this.ingredienteBO = IngredienteBO.getInstance();
        this.productoBO = ProductoBO.getInstance();
        this.reporteClienteBO = ReporteBO.getInstance();
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
     * Cierra la sesión actual y regresa al menú de acceso.
     */
    public void cerrarSesion() {
        ocultarTodasLasPantallas();

        if (frmMenuAcceso == null) {
            frmMenuAcceso = new FrmMenuAcceso(this);
        }

        frmMenuAcceso.setVisible(true);
        frmMenuAcceso.toFront();
    }

    // =========================================================
    // COMANDAS
    // =========================================================
    /**
     * Registra una nueva comanda en el sistema.
     *
     * @param comanda DTO de la comanda a guardar.
     * @return Comanda registrada.
     * @throws Exception Si ocurre un error.
     */
    public ComandaDTO registrarComanda(ComandaDTO comanda) throws Exception {
        try {
            return comandaBO.guardar(comanda);
        } catch (Exception ex) {
            throw new Exception("Error al registrar la comanda: " + ex.getMessage(), ex);
        }
    }

    /**
     * Hace visible la pantalla principal de gestión de comandas.
     */
    public void mostrarGestionarComandas() {
        ocultarTodasLasPantallas();

        if (frmComandas == null) {
            frmComandas = new FrmComandas(this);
        }

        frmComandas.setVisible(true);
        frmComandas.toFront();
        frmComandas.recargarTabla();
    }

    /**
     * Muestra la pantalla de catálogo de productos para nueva comanda.
     */
    public void mostrarCatalogoProductosComanda() {
        catalogoEnModoEdicion = false;

        if (frmCatalogoProductosComanda == null) {
            frmCatalogoProductosComanda = new FrmCatalogoProductosComanda(this);
        }

        frmCatalogoProductosComanda.setVisible(true);
        frmCatalogoProductosComanda.toFront();
    }

    /**
     * Abre el catálogo de productos para edición de comanda.
     */
    public void mostrarCatalogoProductosParaEdicion() {
        catalogoEnModoEdicion = true;

        if (frmCatalogoProductosComanda == null) {
            frmCatalogoProductosComanda = new FrmCatalogoProductosComanda(this);
        }

        frmCatalogoProductosComanda.setVisible(true);
        frmCatalogoProductosComanda.toFront();
    }

    /**
     * Recibe los productos seleccionados desde el catálogo y los envía a la
     * pantalla correspondiente según el contexto actual.
     *
     * @param detallesSeleccionados Lista de detalles seleccionados.
     */
    public void recibirProductosSeleccionadosDesdeCatalogo(List<DetalleComandaDTO> detallesSeleccionados) {
        if (catalogoEnModoEdicion) {
            recibirProductosSeleccionadosEdicion(detallesSeleccionados);
        } else {
            recibirProductosSeleccionadosComanda(detallesSeleccionados);
        }
    }

    /**
     * Recibe los productos seleccionados desde el catálogo y los envía a la
     * pantalla de nueva comanda para actualizar su detalle.
     *
     * @param detallesSeleccionados Lista de detalles seleccionados.
     */
    public void recibirProductosSeleccionadosComanda(List<DetalleComandaDTO> detallesSeleccionados) {
        if (frmNuevaComanda != null) {
            frmNuevaComanda.cargarDetallesSeleccionados(detallesSeleccionados);
            frmNuevaComanda.setVisible(true);
            frmNuevaComanda.toFront();
        }

        if (frmCatalogoProductosComanda != null) {
            frmCatalogoProductosComanda.setVisible(false);
            frmCatalogoProductosComanda.dispose();
            frmCatalogoProductosComanda = null;
        }
    }

    /**
     * Recibe los productos seleccionados desde el catálogo y los envía a la
     * pantalla de edición de comanda para actualizar su detalle.
     *
     * @param detallesSeleccionados Lista de detalles seleccionados.
     */
    public void recibirProductosSeleccionadosEdicion(List<DetalleComandaDTO> detallesSeleccionados) {
        if (frmEditarComanda != null) {
            frmEditarComanda.agregarProductosDesdeCatalogo(detallesSeleccionados);
            frmEditarComanda.setVisible(true);
            frmEditarComanda.toFront();
        }

        if (frmCatalogoProductosComanda != null) {
            frmCatalogoProductosComanda.setVisible(false);
            frmCatalogoProductosComanda.dispose();
            frmCatalogoProductosComanda = null;
        }
    }

    /**
     * Obtiene todas las comandas registradas.
     *
     * @return Lista de comandas.
     * @throws Exception Si ocurre un error.
     */
    public List<ComandaDTO> obtenerComandas() throws Exception {
        try {
            return comandaBO.buscarPorFiltros(null);
        } catch (Exception ex) {
            throw new Exception("Error al obtener las comandas: " + ex.getMessage(), ex);
        }
    }

    /**
     * Busca comandas por filtro.
     *
     * @param filtro Texto de búsqueda.
     * @return Lista de comandas filtradas.
     * @throws Exception Si ocurre un error.
     */
    public List<ComandaDTO> buscarComandasPorFiltro(String filtro) throws Exception {
        try {
            return comandaBO.buscarPorFiltros(filtro);
        } catch (Exception ex) {
            throw new Exception("Error al filtrar las comandas.", ex);
        }
    }

    /**
     * Marca una comanda como entregada.
     *
     * @param idComanda ID de la comanda.
     * @throws Exception Si ocurre un error.
     */
    public void entregarComanda(Long idComanda) throws Exception {
        try {
            comandaBO.entregar(idComanda);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     * Actualiza una comanda existente.
     *
     * @param comanda DTO con la información actualizada.
     * @throws Exception Si ocurre un error.
     */
    public void actualizarComanda(ComandaDTO comanda) throws Exception {
        try {
            comandaBO.editar(comanda);
        } catch (Exception ex) {
            throw new Exception("Error al actualizar la comanda.", ex);
        }
    }

    /**
     * Marca una comanda como cancelada.
     *
     * @param idComanda ID de la comanda.
     * @throws Exception Si ocurre un error.
     */
    public void cancelarComanda(Long idComanda) throws Exception {
        try {
            comandaBO.cancelar(idComanda);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     * Muestra la pantalla para registrar una nueva comanda.
     */
    public void mostrarNuevaComanda() {
        ocultarTodasLasPantallas();

        if (frmNuevaComanda == null) {
            frmNuevaComanda = new FrmNuevaComanda(this);
        }

        frmNuevaComanda.setVisible(true);
    }

    /**
     * Muestra la pantalla para editar una comanda.
     *
     * @param idComanda ID de la comanda.
     */
    public void mostrarEditarComanda(Long idComanda) {
        try {
            ComandaDTO comanda = comandaBO.buscarPorId(idComanda);

            if (comanda == null) {
                throw new Exception("No se encontró la comanda seleccionada.");
            }

            ocultarTodasLasPantallas();

            frmEditarComanda = new FrmEditarComanda(this, comanda);
            frmEditarComanda.setVisible(true);
            frmEditarComanda.toFront();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al abrir la pantalla de edición: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Regresa a la pantalla principal de comandas desde edición.
     */
    public void regresarAGestionComandasDesdeEditar() {
        if (frmEditarComanda != null) {
            frmEditarComanda.setVisible(false);
            frmEditarComanda.dispose();
            frmEditarComanda = null;
        }

        if (frmComandas != null) {
            frmComandas.setVisible(true);
            frmComandas.toFront();
            frmComandas.recargarTabla();
        }
    }

    /**
     * Obtiene las mesas disponibles para registrar una nueva comanda.
     *
     * @return Lista de mesas disponibles.
     * @throws Exception Si ocurre un error.
     */
    public List<MesaDTO> obtenerMesasDisponibles() throws Exception {
        try {
            return comandaBO.obtenerMesasDisponibles();
        } catch (Exception ex) {
            throw new Exception("Error al obtener las mesas disponibles.", ex);
        }
    }

    /**
     * Obtiene los clientes frecuentes para selección opcional en la comanda.
     *
     * @return Lista de clientes frecuentes.
     * @throws Exception Si ocurre un error.
     */
    public List<ClienteFrecuenteDTO> obtenerClientesParaComanda() throws Exception {
        try {
            return clienteFrecuenteBO.obtenerTodos();
        } catch (Exception ex) {
            throw new Exception("Error al obtener los clientes para la comanda.", ex);
        }
    }

    // =========================================================
    // CLIENTES FRECUENTES
    // =========================================================
    /**
     * Hace visible la pantalla principal de gestión de clientes frecuentes.
     */
    public void mostrarGestionarClientesFrecuentes() {
        ocultarTodasLasPantallas();

        if (frmGestionarClientesFrecuentes == null) {
            frmGestionarClientesFrecuentes = new FrmClientesFrecuentes(this, true);
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
            frmRegistrarClientesFrecuentes = new frmRegistrarClienteFrecuente(this);
        }

        frmRegistrarClientesFrecuentes.setVisible(true);
        frmRegistrarClientesFrecuentes.toFront();
    }

    /**
     * Regresa a la pantalla de gestión de clientes frecuentes desde registro.
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
     * Regresa a la pantalla de gestión de clientes frecuentes desde edición.
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
     * Registra un nuevo cliente frecuente.
     *
     * @param cliente DTO del cliente frecuente.
     * @throws Exception Si ocurre un error.
     */
    public void registrarClienteFrecuente(ClienteFrecuenteDTO cliente) throws Exception {
        try {
            clienteFrecuenteBO.guardar(cliente);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     * Elimina un cliente frecuente.
     *
     * @param idCliente ID del cliente.
     * @throws Exception Si ocurre un error.
     */
    public void eliminarCliente(Long idCliente) throws Exception {
        try {
            clienteFrecuenteBO.eliminar(idCliente);
        } catch (Exception ex) {
            throw new Exception("Error al eliminar el cliente frecuente.", ex);
        }
    }

    /**
     * Obtiene todos los clientes frecuentes.
     *
     * @return Lista de clientes frecuentes.
     * @throws Exception Si ocurre un error.
     */
    public List<ClienteFrecuenteDTO> obtenerClientesFrecuentes() throws Exception {
        try {
            return clienteFrecuenteBO.obtenerTodos();
        } catch (Exception ex) {
            throw new Exception("Error al obtener los clientes frecuentes.", ex);
        }
    }

    /**
     * Abre la pantalla de edición de cliente frecuente.
     *
     * @param idCliente ID del cliente.
     */
    public void mostrarEditarClienteFrecuente(Long idCliente) {
        try {
            ClienteFrecuenteDTO cliente = clienteFrecuenteBO.buscarPorId(idCliente);

            if (cliente == null) {
                throw new Exception("No se encontró el cliente seleccionado.");
            }

            ocultarTodasLasPantallas();

            frmEditarClienteFrecuente = new FrmEditarClienteFrecuente(this, cliente);
            frmEditarClienteFrecuente.setVisible(true);
            frmEditarClienteFrecuente.toFront();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al abrir la pantalla de edición: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Actualiza un cliente frecuente.
     *
     * @param cliente DTO actualizado.
     * @throws Exception Si ocurre un error.
     */
    public void actualizarClienteFrecuente(ClienteFrecuenteDTO cliente) throws Exception {
        try {
            clienteFrecuenteBO.editar(cliente);
        } catch (Exception ex) {
            throw new Exception("Error al actualizar el cliente frecuente.", ex);
        }
    }

    /**
     * Busca clientes frecuentes por filtro.
     *
     * @param filtro Texto de búsqueda.
     * @return Lista filtrada.
     * @throws Exception Si ocurre un error.
     */
    public List<ClienteFrecuenteDTO> buscarClientesPorFiltro(String filtro) throws Exception {
        try {
            return clienteFrecuenteBO.buscarPorFiltros(filtro);
        } catch (Exception ex) {
            throw new Exception("Error al filtrar los clientes.", ex);
        }
    }

    public void registrarClienteGeneral() {
        try {
            clienteFrecuenteBO.registrarClienteGeneral();
            if (frmGestionarClientesFrecuentes != null) {
                frmGestionarClientesFrecuentes.recargarTabla();
            }
        } catch (Exception ex) {

        }
    }

    // =========================================================
    // INGREDIENTES
    // =========================================================
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
     * Obtiene todos los ingredientes.
     *
     * @return Lista de ingredientes.
     * @throws Exception Si ocurre un error.
     */
    public List<IngredienteDTO> obtenerIngredientes() throws Exception {
        try {
            return ingredienteBO.buscarPorNombreYUnidad("", null);
        } catch (Exception ex) {
            throw new Exception("Error al obtener los ingredientes.", ex);
        }
    }

    /**
     * Busca ingredientes por nombre y unidad.
     *
     * @param nombre Nombre parcial.
     * @param unidad Unidad de medida.
     * @return Lista filtrada.
     * @throws Exception Si ocurre un error.
     */
    public List<IngredienteDTO> buscarIngredientesPorNombreYUnidad(String nombre, UnidadMedidaDTO unidad) throws Exception {
        try {
            return ingredienteBO.buscarPorNombreYUnidad(nombre, unidad);
        } catch (Exception ex) {
            throw new Exception("Error al obtener los ingredientes.", ex);
        }
    }

    /**
     * Busca un ingrediente por ID.
     *
     * @param idIngrediente ID del ingrediente.
     * @return Ingrediente encontrado.
     * @throws Exception Si ocurre un error.
     */
    public IngredienteDTO buscarIngredientePorId(Long idIngrediente) throws Exception {
        try {
            return ingredienteBO.buscarPorId(idIngrediente);
        } catch (Exception ex) {
            throw new Exception("Error al buscar el ingrediente.", ex);
        }
    }

    /**
     * Muestra la pantalla de nuevo ingrediente.
     */
    public void mostrarNuevoIngrediente() {
        ocultarTodasLasPantallas();

        if (frmNuevoIngrediente == null) {
            frmNuevoIngrediente = new FrmNuevoIngrediente(this);
        }

        frmNuevoIngrediente.setVisible(true);
        frmNuevoIngrediente.toFront();
    }

    /**
     * Registra un nuevo ingrediente.
     *
     * @param ingrediente DTO del ingrediente.
     * @throws Exception Si ocurre un error.
     */
    public void registrarIngrediente(IngredienteDTO ingrediente) throws Exception {
        try {
            ingredienteBO.guardar(ingrediente);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     * Muestra la pantalla de edición de ingrediente.
     *
     * @param idIngrediente ID del ingrediente.
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
            JOptionPane.showMessageDialog(
                    null,
                    "Error al abrir la pantalla de edición: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Actualiza un ingrediente.
     *
     * @param ingrediente DTO actualizado.
     * @throws Exception Si ocurre un error.
     */
    public void actualizarIngrediente(IngredienteDTO ingrediente) throws Exception {
        try {
            ingredienteBO.editar(ingrediente);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     * Elimina un ingrediente.
     *
     * @param idIngrediente ID del ingrediente.
     * @throws Exception Si ocurre un error.
     */
    public void eliminarIngrediente(Long idIngrediente) throws Exception {
        try {
            ingredienteBO.eliminar(idIngrediente);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     * Regresa a la pantalla principal de ingredientes.
     */
    public void regresarAGestionIngredientes() {
        if (frmNuevoIngrediente != null) {
            frmNuevoIngrediente.setVisible(false);
            frmNuevoIngrediente.dispose();
            frmNuevoIngrediente = null;
        }

        if (frmEditarIngrediente != null) {
            frmEditarIngrediente.setVisible(false);
            frmEditarIngrediente.dispose();
            frmEditarIngrediente = null;
        }

        if (frmIngredientes != null) {
            frmIngredientes.setVisible(true);
            frmIngredientes.toFront();
            frmIngredientes.recargarTabla();
        }
    }

    /**
     * Hace visible la pantalla principal de gestión de productos.
     */
    public void mostrarGestionarProductos() {
        ocultarTodasLasPantallas();

        if (frmProductos == null) {
            frmProductos = new FrmProductos(this);
        }

        frmProductos.setVisible(true);
        frmProductos.toFront();
        frmProductos.recargarTabla();
    }

    /**
     * Muestra la pantalla para registrar un nuevo producto.
     */
    public void mostrarNuevoProducto() {
        ocultarTodasLasPantallas();

        if (frmNuevoProducto == null) {
            frmNuevoProducto = new FrmNuevoProducto(this);
        }

        frmNuevoProducto.setVisible(true);
        frmNuevoProducto.toFront();
    }

    /**
     * Regresa a la pantalla principal de productos.
     */
    public void regresarAGestionProductos() {
        if (frmNuevoProducto != null) {
            frmNuevoProducto.setVisible(false);
            frmNuevoProducto.dispose();
            frmNuevoProducto = null;
        }

        if (frmEditarProducto != null) {
            frmEditarProducto.setVisible(false);
            frmEditarProducto.dispose();
            frmEditarProducto = null;
        }

        if (frmProductos != null) {
            frmProductos.setVisible(true);
            frmProductos.toFront();
            frmProductos.recargarTabla();
        }
    }

    /**
     * Obtiene todos los productos.
     *
     * @return Lista de productos.
     * @throws Exception Si ocurre un error.
     */
    public List<ProductoDTO> obtenerProductos() throws Exception {
        try {
            return productoBO.obtenerTodos();
        } catch (Exception ex) {
            throw new Exception("Error al obtener los productos.", ex);
        }
    }

    /**
     * Busca productos por nombre y tipo.
     *
     * @param nombre Nombre o parte del nombre.
     * @param tipo Tipo de producto DTO.
     * @return Lista de productos filtrados.
     * @throws Exception Si ocurre un error.
     */
    public List<ProductoDTO> buscarProductosPorNombreYTipo(String nombre, TipoProductoDTO tipo) throws Exception {
        try {
            return productoBO.buscarPorNombreYTipo(nombre, tipo);
        } catch (Exception ex) {
            throw new Exception("Error al buscar productos.", ex);
        }
    }

    /**
     * Registra un nuevo producto.
     *
     * @param producto DTO del producto.
     * @throws Exception Si ocurre un error.
     */
    public void registrarProducto(ProductoDTO producto) throws Exception {
        try {
            productoBO.guardar(producto);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     * Muestra la pantalla de edición de producto.
     *
     * @param idProducto ID del producto.
     */
    public void mostrarEditarProducto(Long idProducto) {
        try {
            ProductoDTO producto = productoBO.buscarPorId(idProducto);

            if (producto == null) {
                throw new Exception("No se encontró el producto seleccionado.");
            }

            ocultarTodasLasPantallas();

            frmEditarProducto = new FrmEditarProducto(this, producto);
            frmEditarProducto.setVisible(true);
            frmEditarProducto.toFront();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al abrir la pantalla de edición: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Actualiza un producto.
     *
     * @param producto DTO actualizado.
     * @throws Exception Si ocurre un error.
     */
    public void actualizarProducto(ProductoDTO producto) throws Exception {
        try {
            productoBO.editar(producto);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     * Elimina un producto.
     *
     * @param idProducto ID del producto.
     * @throws Exception Si ocurre un error.
     */
    public void eliminarProducto(Long idProducto) throws Exception {
        try {
            productoBO.eliminar(idProducto);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     * Cambia el estado activo/inactivo de un producto.
     *
     * @param idProducto ID del producto.
     * @param activo Nuevo estado.
     * @throws Exception Si ocurre un error.
     */
    public void cambiarEstadoProducto(Long idProducto, Boolean activo) throws Exception {
        try {
            productoBO.cambiarEstadoActivo(idProducto, activo);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     * Actualiza la disponibilidad de un producto.
     *
     * @param idProducto ID del producto.
     * @throws Exception Si ocurre un error.
     */
    public void actualizarDisponibilidadProducto(Long idProducto) throws Exception {
        try {
            productoBO.actualizarDisponibilidad(idProducto);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    public void mostrarReportes() {
        ocultarTodasLasPantallas();

        if (frmReportes == null) {
            frmReportes = new FrmReportes(this);
        }

        frmReportes.setVisible(true);
        frmReportes.toFront();
    }

    public JasperPrint generarVistaReporteClientes(String nombre, Integer minimoVisitas) throws Exception {
        try {
            return reporteClienteBO.generarJasperClientesFrecuentes(nombre, minimoVisitas);
        } catch (NegocioException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    public void generarPDFReporteClientes(String rutaSalidaPDF, String nombre, Integer minimoVisitas) throws Exception {
        try {
            reporteClienteBO.generarReportePDF(rutaSalidaPDF, nombre, minimoVisitas);
        } catch (NegocioException ex) {
            throw new NegocioException("Error al generar el PDF del reporte de clientes: " + ex.getMessage(), ex);
        }
    }

    public JasperPrint generarVistaReporteComanda(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        try {
            return reporteClienteBO.generarJasperReporteComandas(fechaInicio, fechaFin);
        } catch (NegocioException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    public void generarPDFReporteComanda(String rutaSalidaPDF, String nombre, Integer minimoVisitas) throws Exception {
        try {
            reporteClienteBO.generarReportePDF(rutaSalidaPDF, nombre, minimoVisitas);
        } catch (NegocioException ex) {
            throw new NegocioException("Error al generar el PDF del reporte de clientes: " + ex.getMessage(), ex);
        }
    }
    
    /**
     * Abre la pantalla de clientes en modo selección (true).
     * Se activa desde el botón "Buscar" en la pestaña de Reporte de Clientes.
     */
    public void abrirSelectorClienteParaReporte() {
        FrmClientesFrecuentes frmSeleccion = new FrmClientesFrecuentes(this, false);
        frmSeleccion.setVisible(true);
        frmSeleccion.toFront();
    }

   /**
    * Recibe el ID del cliente seleccionado, busca sus datos y los manda a la pantalla de reportes.
    * @param idCliente ID del cliente seleccionado en la tabla.
    */
    public void recibirClienteSeleccionadoParaReporte(Long idCliente) {
        try {
            ClienteFrecuenteDTO cliente = clienteFrecuenteBO.buscarPorId(idCliente);

            if (cliente == null) {
                throw new Exception("No se pudo recuperar la información del cliente.");
            }
            if (frmReportes == null) {
                frmReportes = new FrmReportes(this);
            }
            frmReportes.setClienteSeleccionado(cliente);
            frmReportes.setVisible(true);
            frmReportes.toFront();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al seleccionar cliente para el reporte: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void generarPDFReporteComandas(String ruta, LocalDate inicio, LocalDate fin) throws Exception {
        this.reporteClienteBO.generarReporteComandasPDF(ruta, inicio, fin);
    }

    
    /**
     * Oculta todas las pantallas activas del sistema.
     */
    private void ocultarTodasLasPantallas() {
        if (frmMenuAcceso != null) {
            frmMenuAcceso.setVisible(false);
        }
        if (frmComandas != null) {
            frmComandas.setVisible(false);
        }
        if (frmNuevaComanda != null) {
            frmNuevaComanda.setVisible(false);
        }
        if (frmEditarComanda != null) {
            frmEditarComanda.setVisible(false);
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
        }
        if (frmProductos != null) {
            frmProductos.setVisible(false);
        }
        if (frmNuevoProducto != null) {
            frmNuevoProducto.setVisible(false);
        }
        if (frmEditarProducto != null) {
            frmEditarProducto.setVisible(false);
        }
        if (frmReportes != null) {
            frmReportes.setVisible(false);
        }
    }
}
