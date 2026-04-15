package Pantallas;

import Componentes.BotonRedondeado;
import Componentes.MenuLateralPanel;
import Controlador.Coordinador;
import Estilos.Dimensiones;
import Estilos.PaletaColores;
import dtos.DetalleProductoIngredienteDTO;
import dtos.IngredienteDTO;
import dtos.ProductoDTO;
import enumsDTO.DisponibilidadProductoDTO;
import enumsDTO.TipoProductoDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class FrmNuevoProducto extends JFrame {

    private final Coordinador coordinador;

    private JTextField txtNombreProducto;
    private JTextField txtPrecio;
    private JComboBox<TipoProductoDTO> cmbTipoProducto;
    private JTextField txtRutaImagen;
    private JTextArea txtDescripcion;
    private JTable tablaIngredientes;
    private DefaultTableModel modeloIngredientes;

    private final List<DetalleProductoIngredienteDTO> detallesIngredientes;

    public FrmNuevoProducto(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.detallesIngredientes = new ArrayList<>();

        setTitle("Restaurante Le Pusse - Nuevo Producto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setMinimumSize(new Dimension(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new MenuLateralPanel("Productos", coordinador), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);
    }

    private JPanel crearContenidoPrincipal() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(PaletaColores.BEIGE_FONDO);

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(PaletaColores.BLANCO);
        cabecera.setBorder(new EmptyBorder(22, 28, 18, 28));

        JLabel titulo = new JLabel("Productos");
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

    private JPanel crearPanelFormulario() {
        JPanel formulario = new JPanel();
        formulario.setLayout(new BorderLayout());
        formulario.setBackground(PaletaColores.BLANCO_SUAVE);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 205, 195), 1),
                new EmptyBorder(24, 28, 22, 28)
        ));

        JPanel contenidoVertical = new JPanel();
        contenidoVertical.setOpaque(false);
        contenidoVertical.setLayout(new javax.swing.BoxLayout(contenidoVertical, javax.swing.BoxLayout.Y_AXIS));

        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel lblTitulo = new JLabel("Nuevo Producto");
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblTitulo.setForeground(PaletaColores.TEXTO_MARRON);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelSuperior.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        txtNombreProducto = new JTextField();
        txtPrecio = new JTextField();
        cmbTipoProducto = new JComboBox<>(TipoProductoDTO.values());
        txtRutaImagen = new JTextField();
        txtDescripcion = new JTextArea(4, 20);

        estilizarCampo(txtNombreProducto);
        estilizarCampo(txtPrecio);
        estilizarCombo(cmbTipoProducto);
        estilizarCampo(txtRutaImagen);
        estilizarAreaTexto(txtDescripcion);

        agregarCampo(panelSuperior, gbc, 0, 1, "Nombre del Producto", true, txtNombreProducto);
        agregarCampo(panelSuperior, gbc, 1, 1, "Precio", true, txtPrecio);
        agregarCampo(panelSuperior, gbc, 0, 3, "Tipo de Producto", true, cmbTipoProducto);

        JPanel panelFoto = new JPanel(new BorderLayout(8, 0));
        panelFoto.setOpaque(false);
        txtRutaImagen.setEditable(false);

        BotonRedondeado btnSeleccionarFoto = new BotonRedondeado("Seleccionar Foto", 18);
        btnSeleccionarFoto.setPreferredSize(new Dimension(165, 35));
        btnSeleccionarFoto.setBackground(PaletaColores.DORADO);
        btnSeleccionarFoto.setForeground(PaletaColores.MARRON_OSCURO);
        btnSeleccionarFoto.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSeleccionarFoto.addActionListener(e -> seleccionarImagen());

        panelFoto.add(txtRutaImagen, BorderLayout.CENTER);
        panelFoto.add(btnSeleccionarFoto, BorderLayout.EAST);

        agregarCampo(panelSuperior, gbc, 1, 3, "Foto del Producto", false, panelFoto);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 12, 4, 12);
        panelSuperior.add(crearEtiqueta("Descripción", true), gbc);

        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setPreferredSize(new Dimension(0, 120));
        scrollDescripcion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        scrollDescripcion.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1));
        scrollDescripcion.getViewport().setBackground(PaletaColores.BLANCO);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 12, 8, 12);
        panelSuperior.add(scrollDescripcion, gbc);

        panelSuperior.setAlignmentX(LEFT_ALIGNMENT);

        JPanel panelIngredientes = crearPanelIngredientes();
        panelIngredientes.setAlignmentX(LEFT_ALIGNMENT);

        contenidoVertical.add(panelSuperior);
        contenidoVertical.add(javax.swing.Box.createVerticalStrut(18));
        contenidoVertical.add(panelIngredientes);

        formulario.add(contenidoVertical, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);

        BotonRedondeado btnCancelar = new BotonRedondeado("Cancelar", 18);
        btnCancelar.setPreferredSize(new Dimension(130, 40));
        btnCancelar.setBackground(new Color(232, 216, 182));
        btnCancelar.setForeground(PaletaColores.TEXTO_MARRON);
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnCancelar.addActionListener(e -> coordinador.regresarAGestionProductos());

        BotonRedondeado btnGuardar = new BotonRedondeado("Guardar", 18);
        btnGuardar.setPreferredSize(new Dimension(125, 40));
        btnGuardar.setBackground(PaletaColores.MARRON_OSCURO);
        btnGuardar.setForeground(PaletaColores.BLANCO);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGuardar.addActionListener(e -> guardarProducto());

        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);

        formulario.add(panelBotones, BorderLayout.SOUTH);

        return formulario;
    }

    private JPanel crearPanelIngredientes() {
        JPanel contenedor = new JPanel(new BorderLayout(0, 10));
        contenedor.setOpaque(false);
        contenedor.setBorder(new EmptyBorder(8, 12, 0, 12));
        contenedor.setPreferredSize(new Dimension(0, 260));
        contenedor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));
        contenedor.setMinimumSize(new Dimension(0, 260));

        JLabel lblIngredientes = crearEtiquetaSimple("Ingredientes Requeridos");

        BotonRedondeado btnAgregarIngrediente = new BotonRedondeado("Agregar Ingrediente +", 18);
        btnAgregarIngrediente.setPreferredSize(new Dimension(230, 42));
        btnAgregarIngrediente.setBackground(PaletaColores.DORADO);
        btnAgregarIngrediente.setForeground(PaletaColores.MARRON_OSCURO);
        btnAgregarIngrediente.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAgregarIngrediente.addActionListener(e -> abrirSeleccionIngrediente());

        JPanel parteSuperior = new JPanel(new BorderLayout(0, 8));
        parteSuperior.setOpaque(false);
        parteSuperior.add(lblIngredientes, BorderLayout.NORTH);

        JPanel filaBoton = new JPanel(new BorderLayout());
        filaBoton.setOpaque(false);
        filaBoton.add(btnAgregarIngrediente, BorderLayout.WEST);
        parteSuperior.add(filaBoton, BorderLayout.SOUTH);

        String[] columnas = {"Nombre", "Unidad de Medida", "Cantidad Requerida"};
        modeloIngredientes = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaIngredientes = new JTable(modeloIngredientes);
        tablaIngredientes.setRowHeight(32);
        tablaIngredientes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaIngredientes.setForeground(PaletaColores.TEXTO_MARRON);
        tablaIngredientes.setBackground(PaletaColores.BLANCO);
        tablaIngredientes.setShowVerticalLines(true);
        tablaIngredientes.setShowHorizontalLines(true);
        tablaIngredientes.setGridColor(PaletaColores.LINEA_SUAVE);
        tablaIngredientes.setFillsViewportHeight(true);
        tablaIngredientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        tablaIngredientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaIngredientes.getSelectedRow();
                if (fila == -1) {
                    return;
                }

                if (e.getClickCount() == 2) {
                    editarCantidadIngredienteSeleccionado();
                }

                if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
                    tablaIngredientes.setRowSelectionInterval(fila, fila);

                    javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu();

                    javax.swing.JMenuItem itemEditar = new javax.swing.JMenuItem("Editar cantidad");
                    itemEditar.addActionListener(ev -> editarCantidadIngredienteSeleccionado());

                    javax.swing.JMenuItem itemEliminar = new javax.swing.JMenuItem("Eliminar ingrediente");
                    itemEliminar.addActionListener(ev -> eliminarIngredienteSeleccionado());

                    menu.add(itemEditar);
                    menu.add(itemEliminar);
                    menu.show(tablaIngredientes, e.getX(), e.getY());
                }
            }
        });

        javax.swing.table.JTableHeader header = tablaIngredientes.getTableHeader();
        header.setPreferredSize(new Dimension(0, 30));
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(
                tablaIngredientes,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1));
        scroll.getViewport().setBackground(PaletaColores.BLANCO);
        scroll.setPreferredSize(new Dimension(0, 180));
        scroll.setMinimumSize(new Dimension(0, 180));

        contenedor.add(parteSuperior, BorderLayout.NORTH);
        contenedor.add(scroll, BorderLayout.CENTER);

        return contenedor;
    }
    
    private void abrirSeleccionIngrediente() {
        try {
            List<IngredienteDTO> ingredientes = coordinador.obtenerIngredientes();

            if (ingredientes == null || ingredientes.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "No hay ingredientes registrados para seleccionar.",
                        "Ingredientes",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            FrmIngredientes selector = new FrmIngredientes(coordinador, true, this);
            selector.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar los ingredientes: " + obtenerMensajeRaiz(e),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private IngredienteDTO buscarIngredientePorIdEnLista(List<IngredienteDTO> ingredientes, Long idIngrediente) {
        for (IngredienteDTO ingrediente : ingredientes) {
            if (ingrediente.getIdIngrediente() != null && ingrediente.getIdIngrediente().equals(idIngrediente)) {
                return ingrediente;
            }
        }
        return null;
    }

    private void agregarDetalleIngrediente(IngredienteDTO ingrediente, Integer cantidadRequerida) {
        for (DetalleProductoIngredienteDTO detalle : detallesIngredientes) {
            if (detalle.getIngrediente() != null
                    && detalle.getIngrediente().getIdIngrediente() != null
                    && detalle.getIngrediente().getIdIngrediente().equals(ingrediente.getIdIngrediente())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ese ingrediente ya fue agregado al producto.",
                        "Ingrediente duplicado",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        DetalleProductoIngredienteDTO nuevoDetalle = new DetalleProductoIngredienteDTO();
        nuevoDetalle.setIdDetalleProductoIngrediente(null);
        nuevoDetalle.setIngrediente(ingrediente);
        nuevoDetalle.setCantidadRequerida(cantidadRequerida);

        detallesIngredientes.add(nuevoDetalle);
        recargarTablaIngredientes();
    }
    
    public void agregarIngredienteSeleccionado(IngredienteDTO ingrediente) {
        if (ingrediente == null) {
            return;
        }

        for (DetalleProductoIngredienteDTO detalle : detallesIngredientes) {
            if (detalle.getIngrediente() != null
                    && detalle.getIngrediente().getIdIngrediente() != null
                    && detalle.getIngrediente().getIdIngrediente().equals(ingrediente.getIdIngrediente())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ese ingrediente ya fue agregado al producto.",
                        "Ingrediente duplicado",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        String cantidadTexto = JOptionPane.showInputDialog(
                this,
                "Ingrese la cantidad requerida para \"" + ingrediente.getNombre() + "\":",
                "Cantidad requerida",
                JOptionPane.QUESTION_MESSAGE
        );

        if (cantidadTexto == null) {
            return;
        }

        cantidadTexto = cantidadTexto.trim();

        if (cantidadTexto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "La cantidad requerida es obligatoria.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            Integer cantidad = Integer.valueOf(cantidadTexto);

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "La cantidad requerida debe ser mayor que cero.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            agregarDetalleIngrediente(ingrediente, cantidad);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "La cantidad requerida debe ser un número entero válido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void recargarTablaIngredientes() {
        modeloIngredientes.setRowCount(0);

        for (DetalleProductoIngredienteDTO detalle : detallesIngredientes) {
            IngredienteDTO ingrediente = detalle.getIngrediente();

            modeloIngredientes.addRow(new Object[]{
                ingrediente != null ? ingrediente.getNombre() : "-",
                ingrediente != null && ingrediente.getUnidadMedida() != null
                        ? ingrediente.getUnidadMedida().toString()
                        : "-",
                detalle.getCantidadRequerida()
            });
        }

        tablaIngredientes.revalidate();
        tablaIngredientes.repaint();
    }

    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        int resultado = chooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            txtRutaImagen.setText(archivo.getAbsolutePath());
        }
    }

    private void guardarProducto() {
        try {
            String nombre = txtNombreProducto.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String precioTexto = txtPrecio.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del producto es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (precioTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El precio es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La descripción es obligatoria.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (detallesIngredientes.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Debes agregar al menos un ingrediente al producto.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Double precio = Double.valueOf(precioTexto);

            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor que cero.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ProductoDTO producto = new ProductoDTO();
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setTipo((TipoProductoDTO) cmbTipoProducto.getSelectedItem());
            producto.setRutaImagen(txtRutaImagen.getText().trim().isEmpty() ? null : txtRutaImagen.getText().trim());
            producto.setActivo(Boolean.TRUE);
            producto.setDisponibilidad(DisponibilidadProductoDTO.NO);
            producto.setDetallesIngredientes(new ArrayList<>(detallesIngredientes));

            coordinador.registrarProducto(producto);

            JOptionPane.showMessageDialog(
                    this,
                    "Producto registrado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            coordinador.regresarAGestionProductos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "El precio debe ser un número válido.",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            e.printStackTrace();

            String mensaje = obtenerMensajeRaiz(e);

            if (mensaje != null && mensaje.contains("The abstract schema type 'Producto' is unknown")) {
                JOptionPane.showMessageDialog(
                        this,
                        "JPA no está reconociendo la entidad Producto. Revisa tu persistence.xml y agrega la clase entidades.Producto dentro de la persistence unit.",
                        "Error de configuración",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        mensaje != null ? mensaje : "Ocurrió un error al guardar el producto.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    private void editarCantidadIngredienteSeleccionado() {
        int filaSeleccionada = tablaIngredientes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un ingrediente de la tabla.",
                    "Editar cantidad",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        DetalleProductoIngredienteDTO detalle = detallesIngredientes.get(filaSeleccionada);
        IngredienteDTO ingrediente = detalle.getIngrediente();

        String cantidadTexto = JOptionPane.showInputDialog(
                this,
                "Editar cantidad requerida para \"" + (ingrediente != null ? ingrediente.getNombre() : "Ingrediente") + "\":",
                detalle.getCantidadRequerida()
        );

        if (cantidadTexto == null) {
            return;
        }

        cantidadTexto = cantidadTexto.trim();

        if (cantidadTexto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "La cantidad requerida es obligatoria.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            Integer nuevaCantidad = Integer.valueOf(cantidadTexto);

            if (nuevaCantidad <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "La cantidad requerida debe ser mayor que cero.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            detalle.setCantidadRequerida(nuevaCantidad);
            recargarTablaIngredientes();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "La cantidad requerida debe ser un número entero válido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
    
    private void eliminarIngredienteSeleccionado() {
        int filaSeleccionada = tablaIngredientes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un ingrediente de la tabla.",
                    "Eliminar ingrediente",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas eliminar el ingrediente seleccionado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        detallesIngredientes.remove(filaSeleccionada);
        recargarTablaIngredientes();
    }

    private String obtenerMensajeRaiz(Throwable e) {
        Throwable causa = e;
        while (causa.getCause() != null) {
            causa = causa.getCause();
        }
        return causa.getMessage() != null ? causa.getMessage() : e.getMessage();
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int columna, int filaBase,
            String textoEtiqueta, boolean obligatorio, java.awt.Component campo) {

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

    private JPanel crearEtiqueta(String texto, boolean obligatorio) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTexto.setForeground(PaletaColores.TEXTO_MARRON);
        panel.add(lblTexto, BorderLayout.WEST);

        if (obligatorio) {
            JLabel lblAsterisco = new JLabel(" *");
            lblAsterisco.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblAsterisco.setForeground(new Color(220, 60, 90));
            panel.add(lblAsterisco, BorderLayout.EAST);
        }

        return panel;
    }

    private JLabel crearEtiquetaSimple(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setForeground(PaletaColores.TEXTO_MARRON);
        return lbl;
    }

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

    private void estilizarCombo(JComboBox<?> combo) {
        combo.setPreferredSize(new Dimension(235, 34));
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setForeground(PaletaColores.TEXTO_MARRON);
        combo.setBackground(PaletaColores.BLANCO);
    }

    private void estilizarAreaTexto(JTextArea area) {
        area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        area.setForeground(PaletaColores.TEXTO_MARRON);
        area.setBackground(PaletaColores.BLANCO);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(8, 8, 8, 8));
    }
}