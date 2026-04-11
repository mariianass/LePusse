/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

import Componentes.BotonRedondeado;
import Componentes.MenuLateralPanel;
import Controlador.Coordinador;
import Estilos.Dimensiones;
import Estilos.PaletaColores;
import dtos.DetalleProductoIngredienteDTO;
import dtos.ProductoDTO;
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
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Pantalla para editar un producto existente.
 *
 * @author regina, mariana e isaac
 */
public class FrmEditarProducto extends JFrame {

    private final Coordinador coordinador;
    private final ProductoDTO productoDTO;

    private JTextField txtNombreProducto;
    private JTextField txtPrecio;
    private JComboBox<TipoProductoDTO> cmbTipoProducto;
    private JTextField txtRutaImagen;
    private JTextArea txtDescripcion;
    private JTable tablaIngredientes;
    private DefaultTableModel modeloIngredientes;
    private JCheckBox chkActivo;

    public FrmEditarProducto(Coordinador coordinador, ProductoDTO productoDTO) {
        this.coordinador = coordinador;
        this.productoDTO = productoDTO;

        setTitle("Restaurante Le Pusse - Editar Producto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setMinimumSize(new Dimension(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new MenuLateralPanel("Productos", coordinador), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);

        cargarDatosProducto();
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
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(PaletaColores.BLANCO_SUAVE);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 205, 195), 1),
                new EmptyBorder(20, 24, 18, 24)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Editar Producto");
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblTitulo.setForeground(PaletaColores.TEXTO_MARRON);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formulario.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        txtNombreProducto = new JTextField();
        txtPrecio = new JTextField();
        cmbTipoProducto = new JComboBox<>(TipoProductoDTO.values());
        txtRutaImagen = new JTextField();
        txtDescripcion = new JTextArea(4, 20);
        chkActivo = new JCheckBox("Producto activo");

        estilizarCampo(txtNombreProducto);
        estilizarCampo(txtPrecio);
        estilizarCombo(cmbTipoProducto);
        estilizarCampo(txtRutaImagen);
        estilizarAreaTexto(txtDescripcion);

        chkActivo.setOpaque(false);
        chkActivo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkActivo.setForeground(PaletaColores.TEXTO_MARRON);

        agregarCampo(formulario, gbc, 0, 1, "Nombre del Producto", true, txtNombreProducto);
        agregarCampo(formulario, gbc, 1, 1, "Precio", true, txtPrecio);

        agregarCampo(formulario, gbc, 0, 3, "Tipo de Producto", true, cmbTipoProducto);

        JPanel panelFoto = new JPanel(new BorderLayout(8, 0));
        panelFoto.setOpaque(false);
        txtRutaImagen.setEditable(false);

        BotonRedondeado btnSeleccionarFoto = new BotonRedondeado("Seleccionar Foto", 18);
        btnSeleccionarFoto.setPreferredSize(new Dimension(150, 35));
        btnSeleccionarFoto.setBackground(PaletaColores.DORADO);
        btnSeleccionarFoto.setForeground(PaletaColores.MARRON_OSCURO);
        btnSeleccionarFoto.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSeleccionarFoto.addActionListener(e -> seleccionarImagen());

        panelFoto.add(txtRutaImagen, BorderLayout.CENTER);
        panelFoto.add(btnSeleccionarFoto, BorderLayout.EAST);

        agregarCampo(formulario, gbc, 1, 3, "Foto del Producto", false, panelFoto);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 12, 4, 12);
        formulario.add(crearEtiqueta("Descripción", true), gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 12, 8, 12);
        formulario.add(new JScrollPane(txtDescripcion), gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(8, 12, 8, 12);
        formulario.add(chkActivo, gbc);

        gbc.gridy = 8;
        gbc.insets = new Insets(14, 12, 4, 12);
        formulario.add(crearEtiquetaSimple("Ingredientes Requeridos"), gbc);

        gbc.gridy = 9;
        gbc.insets = new Insets(0, 12, 8, 12);
        formulario.add(crearPanelIngredientes(), gbc);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);

        BotonRedondeado btnEliminar = new BotonRedondeado("Eliminar", 18);
        btnEliminar.setPreferredSize(new Dimension(125, 40));
        btnEliminar.setBackground(new Color(200, 70, 70));
        btnEliminar.setForeground(PaletaColores.BLANCO);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEliminar.addActionListener(e -> eliminarProducto());

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
        btnGuardar.addActionListener(e -> guardarCambios());

        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);

        gbc.gridy = 10;
        gbc.insets = new Insets(18, 12, 0, 12);
        formulario.add(panelBotones, gbc);

        return formulario;
    }

    private JPanel crearPanelIngredientes() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        BotonRedondeado btnAgregarIngrediente = new BotonRedondeado("Agregar Ingrediente +", 18);
        btnAgregarIngrediente.setPreferredSize(new Dimension(190, 38));
        btnAgregarIngrediente.setBackground(PaletaColores.DORADO);
        btnAgregarIngrediente.setForeground(PaletaColores.MARRON_OSCURO);
        btnAgregarIngrediente.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAgregarIngrediente.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Aquí después conectaremos la selección de ingredientes.",
                    "Pendiente",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        panelSuperior.add(btnAgregarIngrediente, BorderLayout.WEST);

        String[] columnas = {"Nombre", "Unidad de Medida", "Cantidad Requerida"};
        modeloIngredientes = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaIngredientes = new JTable(modeloIngredientes);
        tablaIngredientes.setRowHeight(35);
        tablaIngredientes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaIngredientes.setForeground(PaletaColores.TEXTO_MARRON);
        tablaIngredientes.setBackground(PaletaColores.BLANCO);

        JScrollPane scroll = new JScrollPane(tablaIngredientes);
        scroll.setPreferredSize(new Dimension(600, 160));

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void cargarDatosProducto() {
        if (productoDTO == null) {
            return;
        }

        txtNombreProducto.setText(productoDTO.getNombre() != null ? productoDTO.getNombre() : "");
        txtPrecio.setText(productoDTO.getPrecio() != null ? String.valueOf(productoDTO.getPrecio()) : "");
        cmbTipoProducto.setSelectedItem(productoDTO.getTipo());
        txtRutaImagen.setText(productoDTO.getRutaImagen() != null ? productoDTO.getRutaImagen() : "");
        txtDescripcion.setText(productoDTO.getDescripcion() != null ? productoDTO.getDescripcion() : "");
        chkActivo.setSelected(productoDTO.getActivo() != null && productoDTO.getActivo());

        modeloIngredientes.setRowCount(0);

        if (productoDTO.getDetallesIngredientes() != null) {
            for (DetalleProductoIngredienteDTO detalle : productoDTO.getDetallesIngredientes()) {
                String nombre = detalle.getIngrediente() != null ? detalle.getIngrediente().getNombre() : "-";
                String unidad = (detalle.getIngrediente() != null && detalle.getIngrediente().getUnidadMedida() != null)
                        ? detalle.getIngrediente().getUnidadMedida().toString() : "-";

                modeloIngredientes.addRow(new Object[]{
                    nombre,
                    unidad,
                    detalle.getCantidadRequerida()
                });
            }
        }
    }

    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        int resultado = chooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            txtRutaImagen.setText(archivo.getAbsolutePath());
        }
    }

    private void guardarCambios() {
        try {
            productoDTO.setNombre(txtNombreProducto.getText().trim());
            productoDTO.setDescripcion(txtDescripcion.getText().trim());
            productoDTO.setPrecio(Double.valueOf(txtPrecio.getText().trim()));
            productoDTO.setTipo((TipoProductoDTO) cmbTipoProducto.getSelectedItem());
            productoDTO.setRutaImagen(txtRutaImagen.getText().trim().isEmpty() ? null : txtRutaImagen.getText().trim());
            productoDTO.setActivo(chkActivo.isSelected());

            if (productoDTO.getDetallesIngredientes() == null) {
                productoDTO.setDetallesIngredientes(new ArrayList<>());
            }

            coordinador.actualizarProducto(productoDTO);

            JOptionPane.showMessageDialog(
                    this,
                    "Producto actualizado correctamente.",
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
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarProducto() {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            coordinador.eliminarProducto(productoDTO.getIdProducto());

            JOptionPane.showMessageDialog(
                    this,
                    "Producto eliminado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            coordinador.regresarAGestionProductos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int columna, int filaBase,
            String textoEtiqueta, boolean obligatorio, java.awt.Component campo) {

        gbc.gridx = columna;
        gbc.gridy = filaBase;
        gbc.weightx = 1.0;
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
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1),
                new EmptyBorder(8, 8, 8, 8)
        ));
    }
}
