/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

import Componentes.BotonEditar;
import Componentes.BotonRedondeado;
import Componentes.MenuLateralPanel;
import Controlador.Coordinador;
import Estilos.Dimensiones;
import Estilos.PaletaColores;
import dtos.ProductoDTO;
import enumsDTO.TipoProductoDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Pantalla principal para la gestión de productos.
 *
 * @author regina, mariana e isaac
 */
public class FrmProductos extends JFrame {

    private final Coordinador coordinador;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JComboBox<String> cmbTipoProducto;

    public FrmProductos(Coordinador coordinador) {
        this.coordinador = coordinador;

        setTitle("Restaurante Le Pusse - Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setMinimumSize(new Dimension(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new MenuLateralPanel("Productos", coordinador), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);

        cargarProductosEnTabla();
    }

    private JPanel crearContenidoPrincipal() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(PaletaColores.BEIGE_FONDO);
        contenido.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(PaletaColores.BLANCO);
        cabecera.setBorder(new EmptyBorder(26, 28, 20, 28));

        JLabel titulo = new JLabel("Productos");
        titulo.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 34));
        titulo.setForeground(PaletaColores.TEXTO_MARRON);
        cabecera.add(titulo, BorderLayout.WEST);

        contenido.add(cabecera, BorderLayout.NORTH);

        JPanel zonaCentral = new JPanel(new BorderLayout());
        zonaCentral.setBackground(PaletaColores.BEIGE_PANEL);
        zonaCentral.setBorder(new EmptyBorder(22, 22, 22, 22));

        zonaCentral.add(crearPanelSuperior(), BorderLayout.NORTH);
        zonaCentral.add(crearPanelTabla(), BorderLayout.CENTER);

        contenido.add(zonaCentral, BorderLayout.CENTER);

        return contenido;
    }

    private JPanel crearPanelSuperior() {
        JPanel superior = new JPanel(new BorderLayout());
        superior.setOpaque(false);
        superior.setBorder(new EmptyBorder(10, 26, 18, 26));

        JPanel izquierda = new JPanel();
        izquierda.setOpaque(false);
        izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.X_AXIS));

        txtBuscar = new JTextField("Buscar por nombre...");
        txtBuscar.setPreferredSize(new Dimension(310, 42));
        txtBuscar.setMaximumSize(new Dimension(310, 42));
        txtBuscar.setMinimumSize(new Dimension(310, 42));
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBuscar.setForeground(new Color(180, 155, 130));
        txtBuscar.setBackground(PaletaColores.BLANCO);
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1),
                new EmptyBorder(0, 14, 0, 14)
        ));

        txtBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ("Buscar por nombre...".equals(txtBuscar.getText())) {
                    txtBuscar.setText("");
                    txtBuscar.setForeground(PaletaColores.TEXTO_MARRON);
                }
            }
        });

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltros();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                aplicarFiltros();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                aplicarFiltros();
            }
        });

        cmbTipoProducto = new JComboBox<>();
        cmbTipoProducto.setPreferredSize(new Dimension(180, 42));
        cmbTipoProducto.setMaximumSize(new Dimension(180, 42));
        cmbTipoProducto.setMinimumSize(new Dimension(180, 42));
        cmbTipoProducto.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cmbTipoProducto.setBackground(PaletaColores.BLANCO);
        cmbTipoProducto.setForeground(PaletaColores.MARRON_OSCURO);
        cmbTipoProducto.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1));

        cmbTipoProducto.addItem("Todos los tipos");
        cmbTipoProducto.addItem("PLATILLO");
        cmbTipoProducto.addItem("BEBIDA");
        cmbTipoProducto.addItem("POSTRE");

        cmbTipoProducto.addActionListener(e -> aplicarFiltros());

        izquierda.add(txtBuscar);
        izquierda.add(Box.createRigidArea(new Dimension(18, 0)));
        izquierda.add(cmbTipoProducto);

        BotonRedondeado btnNuevoProducto = new BotonRedondeado("Nuevo Producto", 20);
        btnNuevoProducto.setPreferredSize(new Dimension(160, 42));
        btnNuevoProducto.setBackground(PaletaColores.DORADO);
        btnNuevoProducto.setForeground(PaletaColores.MARRON_OSCURO);
        btnNuevoProducto.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnNuevoProducto.addActionListener(e -> coordinador.mostrarNuevoProducto());

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnNuevoProducto);

        superior.add(izquierda, BorderLayout.WEST);
        superior.add(panelBoton, BorderLayout.EAST);

        return superior;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PaletaColores.BLANCO);
        panel.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1));
        panel.setPreferredSize(new Dimension(0, 420));

        panel.add(crearTablaProductos(), BorderLayout.CENTER);

        return panel;
    }

    private JScrollPane crearTablaProductos() {
        String[] columnas = {
            "ID", "Nombre", "Precio", "Tipo", "Disponibilidad", "Estado", "Acciones"
        };

        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setRowHeight(49);
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaProductos.setForeground(PaletaColores.TEXTO_MARRON);
        tablaProductos.setBackground(PaletaColores.BLANCO);
        tablaProductos.setShowVerticalLines(false);
        tablaProductos.setShowHorizontalLines(true);
        tablaProductos.setGridColor(PaletaColores.LINEA_SUAVE);
        tablaProductos.setFocusable(false);
        tablaProductos.setSelectionBackground(PaletaColores.BLANCO_SUAVE);
        tablaProductos.setSelectionForeground(PaletaColores.TEXTO_MARRON);
        tablaProductos.setIntercellSpacing(new Dimension(0, 1));
        tablaProductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader header = tablaProductos.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(PaletaColores.BLANCO);
        header.setForeground(PaletaColores.TEXTO_MARRON);
        header.setPreferredSize(new Dimension(0, 54));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PaletaColores.LINEA_SUAVE));

        tablaProductos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(0);

        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(300);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(140);
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(140);
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(120);
        tablaProductos.getColumnModel().getColumn(6).setPreferredWidth(90);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        centrado.setBackground(PaletaColores.BLANCO);
        centrado.setForeground(PaletaColores.TEXTO_MARRON);

        tablaProductos.getColumnModel().getColumn(2).setCellRenderer(centrado);
        tablaProductos.getColumnModel().getColumn(3).setCellRenderer(centrado);
        tablaProductos.getColumnModel().getColumn(4).setCellRenderer(centrado);
        tablaProductos.getColumnModel().getColumn(5).setCellRenderer(centrado);
        tablaProductos.getColumnModel().getColumn(6).setCellRenderer(new BotonEditar());

        tablaProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaProductos.getSelectedRow();
                if (fila == -1) {
                    return;
                }

                if (e.getClickCount() == 2) {
                    Long idProducto = (Long) modeloTabla.getValueAt(fila, 0);
                    coordinador.mostrarEditarProducto(idProducto);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(PaletaColores.BLANCO);
        scroll.setBackground(PaletaColores.BLANCO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scroll;
    }

    private void cargarProductosEnTabla() {
        try {
            modeloTabla.setRowCount(0);

            List<ProductoDTO> productos = coordinador.obtenerProductos();

            for (ProductoDTO producto : productos) {
                modeloTabla.addRow(new Object[]{
                    producto.getIdProducto(),
                    producto.getNombre(),
                    producto.getPrecio() != null ? "$" + String.format("%.2f", producto.getPrecio()) : "$0.00",
                    producto.getTipo() != null ? producto.getTipo().toString() : "-",
                    producto.getDisponibilidad() != null ? producto.getDisponibilidad().toString() : "-",
                    (producto.getActivo() != null && producto.getActivo()) ? "Activo" : "Inactivo",
                    ""
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los productos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void aplicarFiltros() {
        try {
            String texto = txtBuscar.getText().trim();
            String tipoSeleccionado = cmbTipoProducto.getSelectedItem().toString();

            if (texto.isEmpty() || "Buscar por nombre...".equals(texto)) {
                texto = "";
            }

            TipoProductoDTO tipoDTO = null;

            if (!"Todos los tipos".equals(tipoSeleccionado)) {
                tipoDTO = TipoProductoDTO.valueOf(tipoSeleccionado);
            }

            modeloTabla.setRowCount(0);

            List<ProductoDTO> productosFiltrados = coordinador.buscarProductosPorNombreYTipo(texto, tipoDTO);

            for (ProductoDTO producto : productosFiltrados) {
                modeloTabla.addRow(new Object[]{
                    producto.getIdProducto(),
                    producto.getNombre(),
                    producto.getPrecio() != null ? "$" + String.format("%.2f", producto.getPrecio()) : "$0.00",
                    producto.getTipo() != null ? producto.getTipo().toString() : "-",
                    producto.getDisponibilidad() != null ? producto.getDisponibilidad().toString() : "-",
                    (producto.getActivo() != null && producto.getActivo()) ? "Activo" : "Inactivo",
                    ""
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al filtrar los productos: " + e.getMessage(),
                    "Error de Búsqueda",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void recargarTabla() {
        cargarProductosEnTabla();
    }
}
