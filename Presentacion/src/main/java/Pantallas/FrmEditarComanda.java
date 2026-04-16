/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

import Componentes.BotonRedondeado;
import Componentes.MenuLateralEmpleadoPanel;
import Controlador.Coordinador;
import Estilos.Dimensiones;
import Estilos.PaletaColores;
import dtos.ClienteFrecuenteDTO;
import dtos.ComandaDTO;
import dtos.DetalleComandaDTO;
import dtos.MesaDTO;
import enumsDTO.EstadoComandaDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Pantalla para editar una comanda existente dentro del sistema. Permite
 * visualizar y modificar la información general de la comanda, así como
 * confirmar cambios o cancelar lógicamente el pedido.
 *
 * @author regina, mariana e isaac
 */
public class FrmEditarComanda extends JFrame {

    private final Coordinador coordinador;
    private final ComandaDTO comandaOriginal;

    private JTextField txtFolio;
    private JTextField txtFecha;
    private JTextField txtHora;

    private JComboBox<MesaDTO> cmbMesa;
    private JComboBox<ClienteFrecuenteDTO> cmbCliente;

    private JTable tablaDetalle;
    private DefaultTableModel modeloTabla;

    private JLabel lblTotalValor;

    private List<DetalleComandaDTO> detallesSeleccionados;

    /**
     * Constructor principal de la pantalla de edición de comanda.
     *
     * @param coordinador Referencia al coordinador de navegación.
     * @param comanda Comanda seleccionada para edición.
     */
    public FrmEditarComanda(Coordinador coordinador, ComandaDTO comanda) {
        this.coordinador = coordinador;
        this.comandaOriginal = comanda;
        this.detallesSeleccionados = new ArrayList<>();

        setTitle("Restaurante Le Pusse - Editar Comanda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setMinimumSize(new Dimension(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new MenuLateralEmpleadoPanel(coordinador), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);

        configurarRenderersCombos();
        cargarDatosIniciales();
        cargarComandaEnPantalla();
    }

    /**
     * Crea el contenido principal de la pantalla.
     *
     * @return Panel principal de contenido.
     */
    private JPanel crearContenidoPrincipal() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(PaletaColores.BEIGE_FONDO);
        contenido.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(PaletaColores.BLANCO);
        cabecera.setBorder(new EmptyBorder(26, 28, 20, 28));

        JLabel titulo = new JLabel("Comandas");
        titulo.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 34));
        titulo.setForeground(PaletaColores.TEXTO_MARRON);
        cabecera.add(titulo, BorderLayout.WEST);

        contenido.add(cabecera, BorderLayout.NORTH);

        JPanel zonaCentral = new JPanel(new BorderLayout());
        zonaCentral.setBackground(PaletaColores.BEIGE_PANEL);
        zonaCentral.setBorder(new EmptyBorder(28, 34, 34, 34));
        zonaCentral.add(crearPanelFormulario(), BorderLayout.CENTER);

        contenido.add(zonaCentral, BorderLayout.CENTER);

        return contenido;
    }

    /**
     * Crea el panel principal del formulario de edición.
     *
     * @return Panel del formulario.
     */
    private JPanel crearPanelFormulario() {
        JPanel formulario = new JPanel(new BorderLayout());
        formulario.setBackground(PaletaColores.BLANCO_SUAVE);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 205, 195), 1),
                new EmptyBorder(24, 28, 22, 28)
        ));

        JPanel contenidoVertical = new JPanel();
        contenidoVertical.setOpaque(false);
        contenidoVertical.setLayout(new BoxLayout(contenidoVertical, BoxLayout.Y_AXIS));

        contenidoVertical.add(crearPanelDatosGenerales());
        contenidoVertical.add(Box.createVerticalStrut(18));
        contenidoVertical.add(crearPanelDetalleProductos());
        contenidoVertical.add(Box.createVerticalStrut(16));
        contenidoVertical.add(crearPanelTotal());

        formulario.add(contenidoVertical, BorderLayout.CENTER);
        formulario.add(crearPanelBotones(), BorderLayout.SOUTH);

        return formulario;
    }

    /**
     * Crea la sección superior con los datos generales de la comanda.
     *
     * @return Panel con folio, fecha, hora, mesa y cliente.
     */
    private JPanel crearPanelDatosGenerales() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel lblTitulo = new JLabel("Editar Comandas");
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblTitulo.setForeground(PaletaColores.TEXTO_MARRON);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        txtFolio = new JTextField();
        txtFecha = new JTextField();
        txtHora = new JTextField();

        txtFolio.setEditable(false);
        txtFecha.setEditable(false);
        txtHora.setEditable(false);

        estilizarCampo(txtFolio);
        estilizarCampo(txtFecha);
        estilizarCampo(txtHora);

        agregarCampo(panel, gbc, 0, 1, "Folio", false, txtFolio);
        agregarCampo(panel, gbc, 1, 1, "Fecha", false, txtFecha);
        agregarCampo(panel, gbc, 2, 1, "Hora", false, txtHora);

        cmbMesa = new JComboBox<>();
        cmbCliente = new JComboBox<>();

        estilizarCombo(cmbMesa);
        estilizarCombo(cmbCliente);

        agregarCampo(panel, gbc, 0, 3, "Mesa", true, cmbMesa);
        agregarCampo(panel, gbc, 1, 3, "Cliente", false, cmbCliente);

        return panel;
    }

    /**
     * Crea la sección de productos con tabla de detalle.
     *
     * @return Panel del detalle de productos.
     */
    private JPanel crearPanelDetalleProductos() {
        JPanel contenedor = new JPanel(new BorderLayout(0, 12));
        contenedor.setOpaque(false);
        contenedor.setBorder(new EmptyBorder(8, 12, 0, 12));
        contenedor.setPreferredSize(new Dimension(0, 300));
        contenedor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        contenedor.setMinimumSize(new Dimension(0, 300));

        JLabel lblProductos = crearEtiquetaSimple("Productos");

        BotonRedondeado btnAgregarProducto = new BotonRedondeado("Agregar Producto +", 18);
        btnAgregarProducto.setPreferredSize(new Dimension(170, 38));
        btnAgregarProducto.setBackground(PaletaColores.DORADO);
        btnAgregarProducto.setForeground(PaletaColores.MARRON_OSCURO);
        btnAgregarProducto.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAgregarProducto.addActionListener(e -> {
            coordinador.mostrarCatalogoProductosParaEdicion();
        });

        JPanel parteSuperior = new JPanel(new BorderLayout());
        parteSuperior.setOpaque(false);
        parteSuperior.add(lblProductos, BorderLayout.WEST);

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnAgregarProducto);
        parteSuperior.add(panelBoton, BorderLayout.EAST);

        contenedor.add(parteSuperior, BorderLayout.NORTH);
        contenedor.add(crearTablaDetalle(), BorderLayout.CENTER);

        return contenedor;
    }

    /**
     * Crea la tabla visual del detalle de productos de la comanda.
     *
     * @return Scroll con la tabla.
     */
    private JScrollPane crearTablaDetalle() {
        String[] columnas = {
            "Producto", "Cantidad", "Precio Unitario", "Subtotal", "Comentarios", "Eliminar"
        };

        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaDetalle = new JTable(modeloTabla);
        tablaDetalle.setRowHeight(42);
        tablaDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaDetalle.setForeground(PaletaColores.TEXTO_MARRON);
        tablaDetalle.setBackground(PaletaColores.BLANCO);
        tablaDetalle.setShowVerticalLines(false);
        tablaDetalle.setShowHorizontalLines(true);
        tablaDetalle.setGridColor(PaletaColores.LINEA_SUAVE);
        tablaDetalle.setFocusable(false);
        tablaDetalle.setIntercellSpacing(new Dimension(0, 1));
        tablaDetalle.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tablaDetalle.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaDetalle.getTableHeader().setBackground(PaletaColores.BLANCO);
        tablaDetalle.getTableHeader().setForeground(PaletaColores.TEXTO_MARRON);
        tablaDetalle.getTableHeader().setPreferredSize(new Dimension(0, 44));
        tablaDetalle.getTableHeader().setReorderingAllowed(false);
        tablaDetalle.getTableHeader().setResizingAllowed(false);
        tablaDetalle.getTableHeader().setBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, PaletaColores.LINEA_SUAVE)
        );

        tablaDetalle.getColumnModel().getColumn(0).setPreferredWidth(220);
        tablaDetalle.getColumnModel().getColumn(1).setPreferredWidth(110);
        tablaDetalle.getColumnModel().getColumn(2).setPreferredWidth(150);
        tablaDetalle.getColumnModel().getColumn(3).setPreferredWidth(130);
        tablaDetalle.getColumnModel().getColumn(4).setPreferredWidth(220);
        tablaDetalle.getColumnModel().getColumn(5).setPreferredWidth(80);

        DefaultTableCellRenderer centradoEliminar = new DefaultTableCellRenderer();
        centradoEliminar.setHorizontalAlignment(SwingConstants.CENTER);
        centradoEliminar.setBackground(PaletaColores.BLANCO);
        centradoEliminar.setForeground(PaletaColores.TEXTO_MARRON);

        tablaDetalle.getColumnModel().getColumn(5).setCellRenderer(centradoEliminar);

        JScrollPane scroll = new JScrollPane(tablaDetalle);
        scroll.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1));
        scroll.getViewport().setBackground(PaletaColores.BLANCO);
        scroll.setBackground(PaletaColores.BLANCO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tablaDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaDetalle.getSelectedRow();
                int columna = tablaDetalle.getSelectedColumn();

                if (fila == -1) {
                    return;
                }

                if (columna == 5) {
                    confirmarEliminacionProducto(fila);
                }
            }
        });

        return scroll;
    }

    /**
     * Crea el panel visual para mostrar el total acumulado de la comanda.
     *
     * @return Panel del total.
     */
    private JPanel crearPanelTotal() {
        JPanel panelTotal = new JPanel(new BorderLayout());
        panelTotal.setOpaque(false);
        panelTotal.setBorder(new EmptyBorder(0, 12, 0, 12));

        JLabel lblTotalTexto = new JLabel("Total:");
        lblTotalTexto.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalTexto.setForeground(PaletaColores.TEXTO_MARRON);

        lblTotalValor = new JLabel("$0.00");
        lblTotalValor.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalValor.setForeground(PaletaColores.MARRON_OSCURO);
        lblTotalValor.setHorizontalAlignment(SwingConstants.RIGHT);

        panelTotal.add(lblTotalTexto, BorderLayout.WEST);
        panelTotal.add(lblTotalValor, BorderLayout.EAST);

        return panelTotal;
    }

    /**
     * Crea el panel inferior con los botones principales de acción.
     *
     * @return Panel de botones.
     */
    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false);
        panelBotones.setBorder(new EmptyBorder(12, 12, 0, 12));

        BotonRedondeado btnEliminar = new BotonRedondeado("Eliminar", 18);
        btnEliminar.setPreferredSize(new Dimension(130, 40));
        btnEliminar.setBackground(PaletaColores.MARRON_OSCURO);
        btnEliminar.setForeground(PaletaColores.BLANCO);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEliminar.addActionListener(e -> confirmarEliminacion());

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        izquierda.setOpaque(false);
        izquierda.add(btnEliminar);

        BotonRedondeado btnCancelar = new BotonRedondeado("Cancelar", 18);
        btnCancelar.setPreferredSize(new Dimension(130, 40));
        btnCancelar.setBackground(new Color(232, 216, 182));
        btnCancelar.setForeground(PaletaColores.TEXTO_MARRON);
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnCancelar.addActionListener(e -> coordinador.regresarAGestionComandasDesdeEditar());

        BotonRedondeado btnGuardar = new BotonRedondeado("Guardar", 18);
        btnGuardar.setPreferredSize(new Dimension(125, 40));
        btnGuardar.setBackground(PaletaColores.MARRON_OSCURO);
        btnGuardar.setForeground(PaletaColores.BLANCO);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGuardar.addActionListener(e -> confirmarGuardado());

        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        derecha.setOpaque(false);
        derecha.add(btnCancelar);
        derecha.add(btnGuardar);

        panelBotones.add(izquierda, BorderLayout.WEST);
        panelBotones.add(derecha, BorderLayout.EAST);

        return panelBotones;
    }

    /**
     * Carga la información inicial necesaria para la edición de la comanda.
     */
    private void cargarDatosIniciales() {
        cargarMesasDisponibles();
        cargarClientesFrecuentes();
    }

    /**
     * Recibe los productos seleccionados desde el catálogo y los agrega al
     * detalle de la comanda.
     */
    public void agregarProductosDesdeCatalogo(List<DetalleComandaDTO> nuevosDetalles) {
        if (nuevosDetalles == null || nuevosDetalles.isEmpty()) {
            return;
        }

        for (DetalleComandaDTO nuevo : nuevosDetalles) {

            boolean encontrado = false;

            for (DetalleComandaDTO existente : detallesSeleccionados) {
                if (existente.getIdProducto().equals(nuevo.getIdProducto())) {

                    int nuevaCantidad = existente.getCantidad() + nuevo.getCantidad();
                    existente.setCantidad(nuevaCantidad);

                    double subtotal = nuevaCantidad * existente.getPrecio();
                    existente.setSubtotal(subtotal);

                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                detallesSeleccionados.add(nuevo);
            }
        }

        refrescarTablaDetalle();
    }

    /**
     * Refresca la tabla del detalle y recalcula el total.
     */
    private void refrescarTablaDetalle() {
        modeloTabla.setRowCount(0);

        double total = 0.0;

        for (DetalleComandaDTO detalle : detallesSeleccionados) {

            double subtotal = detalle.getCantidad() * detalle.getPrecio();
            detalle.setSubtotal(subtotal);

            total += subtotal;

            modeloTabla.addRow(new Object[]{
                detalle.getNombreProducto(),
                detalle.getCantidad(),
                "$" + String.format("%.2f", detalle.getPrecio()),
                "$" + String.format("%.2f", subtotal),
                detalle.getComentarioEspecial() != null ? detalle.getComentarioEspecial() : "",
                "🗑"
            });
        }

        lblTotalValor.setText("$" + String.format("%.2f", total));
    }

    /**
     * Carga las mesas disponibles en el combo correspondiente.
     */
    private void cargarMesasDisponibles() {
        try {
            cmbMesa.removeAllItems();

            List<MesaDTO> mesas = coordinador.obtenerMesasDisponibles();

            if (mesas != null) {
                for (MesaDTO mesa : mesas) {
                    cmbMesa.addItem(mesa);
                }
            }

            if (comandaOriginal != null && comandaOriginal.getIdMesa() != null && comandaOriginal.getNumeroMesa() != null) {
                cmbMesa.addItem(new MesaDTO(
                        comandaOriginal.getIdMesa(),
                        comandaOriginal.getNumeroMesa(),
                        null
                ));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar las mesas disponibles: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Carga los clientes frecuentes en el combo correspondiente.
     */
    private void cargarClientesFrecuentes() {
        try {
            cmbCliente.removeAllItems();
            cmbCliente.addItem(null);

            List<ClienteFrecuenteDTO> clientes = coordinador.obtenerClientesParaComanda();

            if (clientes != null) {
                for (ClienteFrecuenteDTO cliente : clientes) {
                    cmbCliente.addItem(cliente);
                }
            }

            cmbCliente.setSelectedItem(null);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar los clientes: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Carga en pantalla la información de la comanda recibida.
     */
    private void cargarComandaEnPantalla() {
        if (comandaOriginal == null) {
            return;
        }

        txtFolio.setText(comandaOriginal.getFolio() != null ? comandaOriginal.getFolio() : "");

        if (comandaOriginal.getFechaHoraCreacion() != null) {
            txtFecha.setText(comandaOriginal.getFechaHoraCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            txtHora.setText(comandaOriginal.getFechaHoraCreacion().format(DateTimeFormatter.ofPattern("HH:mm")));
        } else {
            txtFecha.setText("");
            txtHora.setText("");
        }

        seleccionarMesaOriginal();
        seleccionarClienteOriginal();
        cargarDetallesSeleccionados(comandaOriginal.getDetalles());

        if (comandaOriginal.getEstado() == EstadoComandaDTO.ENTREGADA
                || comandaOriginal.getEstado() == EstadoComandaDTO.CANCELADA) {
            cmbMesa.setEnabled(false);
            cmbCliente.setEnabled(false);
        }
    }

    /**
     * Selecciona en el combo la mesa actual de la comanda.
     */
    private void seleccionarMesaOriginal() {
        if (comandaOriginal.getIdMesa() == null) {
            return;
        }

        for (int i = 0; i < cmbMesa.getItemCount(); i++) {
            MesaDTO mesa = cmbMesa.getItemAt(i);

            if (mesa != null && mesa.getIdMesa() != null
                    && mesa.getIdMesa().equals(comandaOriginal.getIdMesa())) {
                cmbMesa.setSelectedIndex(i);
                return;
            }
        }
    }

    /**
     * Selecciona en el combo el cliente actual de la comanda.
     */
    private void seleccionarClienteOriginal() {
        if (comandaOriginal.getIdCliente() == null) {
            cmbCliente.setSelectedItem(null);
            return;
        }

        for (int i = 0; i < cmbCliente.getItemCount(); i++) {
            ClienteFrecuenteDTO cliente = cmbCliente.getItemAt(i);

            if (cliente != null && cliente.getIdCliente() != null
                    && cliente.getIdCliente().equals(comandaOriginal.getIdCliente())) {
                cmbCliente.setSelectedIndex(i);
                return;
            }
        }
    }

    /**
     * Carga en la tabla los detalles de la comanda y actualiza el total.
     *
     * @param detalles Lista de detalles a mostrar.
     */
    public void cargarDetallesSeleccionados(List<DetalleComandaDTO> detalles) {
        modeloTabla.setRowCount(0);
        this.detallesSeleccionados = new ArrayList<>();

        if (detalles == null || detalles.isEmpty()) {
            lblTotalValor.setText("$0.00");
            return;
        }

        double total = 0.0;

        for (DetalleComandaDTO detalle : detalles) {
            double subtotal = detalle.getSubtotal() != null ? detalle.getSubtotal() : 0.0;
            total += subtotal;

            this.detallesSeleccionados.add(detalle);

            modeloTabla.addRow(new Object[]{
                detalle.getNombreProducto(),
                detalle.getCantidad(),
                detalle.getPrecio() != null ? "$" + String.format("%.2f", detalle.getPrecio()) : "$0.00",
                "$" + String.format("%.2f", subtotal),
                detalle.getComentarioEspecial() != null ? detalle.getComentarioEspecial() : "",
                "🗑"
            });
        }

        lblTotalValor.setText("$" + String.format("%.2f", total));
    }

    /**
     * Solicita confirmación antes de guardar los cambios.
     */
    private void confirmarGuardado() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Confirmas los cambios?",
                "Confirmar cambios",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            guardarCambios();
        }
    }

    /**
     * Solicita confirmación antes de eliminar un producto del detalle.
     *
     * @param fila Índice del detalle seleccionado.
     */
    private void confirmarEliminacionProducto(int fila) {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas eliminar este producto del detalle?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            detallesSeleccionados.remove(fila);
            refrescarTablaDetalle();
        }
    }

    /**
     * Guarda los cambios realizados sobre la comanda.
     */
    private void guardarCambios() {
        try {
            MesaDTO mesaSeleccionada = (MesaDTO) cmbMesa.getSelectedItem();
            ClienteFrecuenteDTO clienteSeleccionado = (ClienteFrecuenteDTO) cmbCliente.getSelectedItem();

            if (mesaSeleccionada == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Debes seleccionar una mesa.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (detallesSeleccionados == null || detallesSeleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "La comanda debe contener al menos un producto.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            double total = 0.0;
            for (DetalleComandaDTO detalle : detallesSeleccionados) {
                total += detalle.getSubtotal() != null ? detalle.getSubtotal() : 0.0;
            }

            ComandaDTO comandaEditada = new ComandaDTO();
            comandaEditada.setIdComanda(comandaOriginal.getIdComanda());
            comandaEditada.setFolio(comandaOriginal.getFolio());
            comandaEditada.setFechaHoraCreacion(comandaOriginal.getFechaHoraCreacion());
            comandaEditada.setEstado(comandaOriginal.getEstado());
            comandaEditada.setTotalVenta(total);

            comandaEditada.setIdMesa(mesaSeleccionada.getIdMesa());
            comandaEditada.setNumeroMesa(mesaSeleccionada.getNumeroMesa());

            if (clienteSeleccionado != null) {
                comandaEditada.setIdCliente(clienteSeleccionado.getIdCliente());

                String nombreCompleto = (clienteSeleccionado.getNombre() + " "
                        + clienteSeleccionado.getApellidoPaterno() + " "
                        + clienteSeleccionado.getApellidoMaterno()).trim().replaceAll("\\s+", " ");

                comandaEditada.setNombreCliente(nombreCompleto);
            } else {
                comandaEditada.setIdCliente(null);
                comandaEditada.setNombreCliente(null);
            }

            comandaEditada.setDetalles(new ArrayList<>(detallesSeleccionados));

            coordinador.actualizarComanda(comandaEditada);

            JOptionPane.showMessageDialog(
                    this,
                    "La comanda fue actualizada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            coordinador.regresarAGestionComandasDesdeEditar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Solicita confirmación antes de cancelar la comanda.
     */
    private void confirmarEliminacion() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar el pedido?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            eliminarComanda();
        }
    }

    /**
     * Cancela lógicamente la comanda seleccionada.
     */
    private void eliminarComanda() {
        try {
            coordinador.cancelarComanda(comandaOriginal.getIdComanda());

            JOptionPane.showMessageDialog(
                    this,
                    "La comanda fue cancelada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            coordinador.regresarAGestionComandasDesdeEditar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Configura la forma en que se muestran las mesas y clientes en los combos.
     */
    private void configurarRenderersCombos() {
        cmbMesa.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    javax.swing.JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value == null) {
                    setText("");
                } else {
                    MesaDTO mesa = (MesaDTO) value;
                    setText("Mesa " + mesa.getNumeroMesa());
                }

                return this;
            }
        });

        cmbCliente.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    javax.swing.JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value == null) {
                    setText("Cliente General");
                } else {
                    ClienteFrecuenteDTO cliente = (ClienteFrecuenteDTO) value;

                    String nombreCompleto = cliente.getNombre() + " "
                            + cliente.getApellidoPaterno() + " "
                            + cliente.getApellidoMaterno();

                    setText(nombreCompleto.trim().replaceAll("\\s+", " "));
                }

                return this;
            }
        });
    }

    /**
     * Agrega una etiqueta y un campo dentro de un panel con GridBagLayout.
     *
     * @param panel Panel destino.
     * @param gbc Restricciones base.
     * @param columna Columna donde se agregará.
     * @param filaBase Fila base de la etiqueta.
     * @param textoEtiqueta Texto de la etiqueta.
     * @param obligatorio Indica si el campo es obligatorio.
     * @param campo Componente de captura o visualización.
     */
    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int columna, int filaBase,
            String textoEtiqueta, boolean obligatorio, Component campo) {

        gbc.gridx = columna;
        gbc.gridy = filaBase;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 12, 4, 12);
        panel.add(crearEtiqueta(textoEtiqueta, obligatorio), gbc);

        gbc.gridy = filaBase + 1;
        gbc.insets = new Insets(0, 12, 8, 12);
        panel.add(campo, gbc);
    }

    /**
     * Crea una etiqueta de campo, mostrando asterisco cuando es obligatorio.
     *
     * @param texto Texto de la etiqueta.
     * @param obligatorio Indica si el campo es obligatorio.
     * @return Panel con la etiqueta.
     */
    private JPanel crearEtiqueta(String texto, boolean obligatorio) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setOpaque(false);

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTexto.setForeground(PaletaColores.TEXTO_MARRON);
        panel.add(lblTexto);

        if (obligatorio) {
            JLabel lblAsterisco = new JLabel(" *");
            lblAsterisco.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblAsterisco.setForeground(new Color(220, 60, 90));
            panel.add(lblAsterisco);
        }

        return panel;
    }

    /**
     * Crea una etiqueta simple para secciones internas.
     *
     * @param texto Texto a mostrar.
     * @return Etiqueta estilizada.
     */
    private JLabel crearEtiquetaSimple(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setForeground(PaletaColores.TEXTO_MARRON);
        return lbl;
    }

    /**
     * Aplica el estilo base a un campo de texto.
     *
     * @param campo Campo a estilizar.
     */
    private void estilizarCampo(JTextField campo) {
        campo.setPreferredSize(new Dimension(235, 34));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setForeground(PaletaColores.TEXTO_MARRON);
        campo.setBackground(PaletaColores.BLANCO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1),
                new EmptyBorder(4, 8, 4, 8)
        ));
    }

    /**
     * Aplica el estilo base a un combo.
     *
     * @param combo Combo a estilizar.
     */
    private void estilizarCombo(JComboBox<?> combo) {
        combo.setPreferredSize(new Dimension(320, 34));
        combo.setMinimumSize(new Dimension(320, 34));
        combo.setMaximumSize(new Dimension(320, 34));
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setForeground(PaletaColores.TEXTO_MARRON);
        combo.setBackground(PaletaColores.BLANCO);
        combo.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1));
    }
}
