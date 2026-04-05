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
import dtos.IngredienteDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
 * Pantalla para la gestión de ingredientes.
 *
 * Muestra un listado dinámico de ingredientes con filtro por nombre y por
 * unidad de medida. Permite abrir la pantalla de registro de ingredientes
 * y también editar un ingrediente al hacer doble clic sobre la fila.
 *
 * @author regina, mariana e isaac
 */
public class FrmIngredientes extends JFrame {

    private final Coordinador coordinador;

    private JTable tablaIngredientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JComboBox<String> cmbUnidadMedida;

    private List<IngredienteDTO> ingredientesCache;

    public FrmIngredientes(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.ingredientesCache = new ArrayList<>();

        setTitle("Restaurante Le Pusse - Ingredientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setMinimumSize(new Dimension(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new MenuLateralPanel("Ingredientes", coordinador), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);

        cargarIngredientes();
    }
    
    private JPanel crearContenidoPrincipal() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(PaletaColores.BEIGE_FONDO);
        contenido.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(PaletaColores.BLANCO);
        cabecera.setBorder(new EmptyBorder(26, 28, 20, 28));

        JLabel titulo = new JLabel("Ingredientes");
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

        cmbUnidadMedida = new JComboBox<>();
        cmbUnidadMedida.setPreferredSize(new Dimension(180, 42));
        cmbUnidadMedida.setMaximumSize(new Dimension(180, 42));
        cmbUnidadMedida.setMinimumSize(new Dimension(180, 42));
        cmbUnidadMedida.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cmbUnidadMedida.setBackground(PaletaColores.BLANCO);
        cmbUnidadMedida.setForeground(PaletaColores.MARRON_OSCURO);
        cmbUnidadMedida.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1));
        cmbUnidadMedida.addActionListener(e -> aplicarFiltros());

        izquierda.add(txtBuscar);
        izquierda.add(Box.createRigidArea(new Dimension(18, 0)));
        izquierda.add(cmbUnidadMedida);

        BotonRedondeado btnNuevoIngrediente = new BotonRedondeado("Nuevo Ingrediente", 20);
        btnNuevoIngrediente.setPreferredSize(new Dimension(170, 42));
        btnNuevoIngrediente.setMinimumSize(new Dimension(170, 42));
        btnNuevoIngrediente.setMaximumSize(new Dimension(170, 42));
        btnNuevoIngrediente.setBackground(PaletaColores.DORADO);
        btnNuevoIngrediente.setForeground(PaletaColores.MARRON_OSCURO);
        btnNuevoIngrediente.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnNuevoIngrediente);

        superior.add(izquierda, BorderLayout.WEST);
        superior.add(panelBoton, BorderLayout.EAST);

        return superior;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PaletaColores.BLANCO);
        panel.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1));
        panel.setPreferredSize(new Dimension(0, 420));

        panel.add(crearTablaIngredientes(), BorderLayout.CENTER);
        return panel;
    }

    private JScrollPane crearTablaIngredientes() {
        String[] columnas = {
            "ID", "Nombre", "Unidad de Medida", "Stock Actual", "Disponibilidad", "Acciones"
        };

        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaIngredientes = new JTable(modeloTabla);
        tablaIngredientes.setRowHeight(49);
        tablaIngredientes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaIngredientes.setForeground(PaletaColores.TEXTO_MARRON);
        tablaIngredientes.setBackground(PaletaColores.BLANCO);
        tablaIngredientes.setShowVerticalLines(false);
        tablaIngredientes.setShowHorizontalLines(true);
        tablaIngredientes.setGridColor(PaletaColores.LINEA_SUAVE);
        tablaIngredientes.setFocusable(false);
        tablaIngredientes.setSelectionBackground(PaletaColores.BLANCO_SUAVE);
        tablaIngredientes.setSelectionForeground(PaletaColores.TEXTO_MARRON);
        tablaIngredientes.setIntercellSpacing(new Dimension(0, 1));
        tablaIngredientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader header = tablaIngredientes.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(PaletaColores.BLANCO);
        header.setForeground(PaletaColores.TEXTO_MARRON);
        header.setPreferredSize(new Dimension(0, 54));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PaletaColores.LINEA_SUAVE));

        tablaIngredientes.getColumnModel().getColumn(0).setMinWidth(0);
        tablaIngredientes.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaIngredientes.getColumnModel().getColumn(0).setPreferredWidth(0);

        tablaIngredientes.getColumnModel().getColumn(1).setPreferredWidth(275);
        tablaIngredientes.getColumnModel().getColumn(2).setPreferredWidth(220);
        tablaIngredientes.getColumnModel().getColumn(3).setPreferredWidth(210);
        tablaIngredientes.getColumnModel().getColumn(4).setPreferredWidth(190);
        tablaIngredientes.getColumnModel().getColumn(5).setPreferredWidth(120);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        centrado.setBackground(PaletaColores.BLANCO);
        centrado.setForeground(PaletaColores.TEXTO_MARRON);

        tablaIngredientes.getColumnModel().getColumn(2).setCellRenderer(centrado);
        tablaIngredientes.getColumnModel().getColumn(3).setCellRenderer(centrado);
        tablaIngredientes.getColumnModel().getColumn(4).setCellRenderer(centrado);
        tablaIngredientes.getColumnModel().getColumn(5).setCellRenderer(new BotonEditar());

        tablaIngredientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tablaIngredientes.getSelectedRow() == -1) {
                    return;
                }

                if (e.getClickCount() == 2) {
                    int filaSeleccionada = tablaIngredientes.getSelectedRow();
                    Long idIngrediente = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);

                }
            }
        });

        JScrollPane scroll = new JScrollPane(tablaIngredientes);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(PaletaColores.BLANCO);
        scroll.setBackground(PaletaColores.BLANCO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scroll;
    }

    private void cargarIngredientes() {
        try {
            ingredientesCache = coordinador.obtenerIngredientes();
            cargarComboUnidades();
            llenarTabla(ingredientesCache);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los ingredientes: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cargarComboUnidades() {
        cmbUnidadMedida.removeAllItems();
        cmbUnidadMedida.addItem("Todas las unidades");

        Set<String> unidades = new LinkedHashSet<>();

        for (IngredienteDTO ingrediente : ingredientesCache) {
            String unidad = obtenerUnidadMedida(ingrediente);
            if (unidad != null && !unidad.isBlank()) {
                unidades.add(unidad);
            }
        }

        for (String unidad : unidades) {
            cmbUnidadMedida.addItem(unidad);
        }
    }

    private void aplicarFiltros() {
        String texto = txtBuscar.getText() != null ? txtBuscar.getText().trim() : "";
        String unidadSeleccionada = cmbUnidadMedida.getSelectedItem() != null
                ? cmbUnidadMedida.getSelectedItem().toString()
                : "Todas las unidades";

        boolean buscarTodo = texto.isEmpty() || "Buscar por nombre...".equalsIgnoreCase(texto);
        boolean todasLasUnidades = "Todas las unidades".equalsIgnoreCase(unidadSeleccionada);

        List<IngredienteDTO> filtrados = new ArrayList<>();

        for (IngredienteDTO ingrediente : ingredientesCache) {
            String nombre = obtenerNombre(ingrediente);
            String unidad = obtenerUnidadMedida(ingrediente);

            boolean coincideNombre = buscarTodo
                    || (nombre != null && nombre.toLowerCase().contains(texto.toLowerCase()));

            boolean coincideUnidad = todasLasUnidades
                    || (unidad != null && unidad.equalsIgnoreCase(unidadSeleccionada));

            if (coincideNombre && coincideUnidad) {
                filtrados.add(ingrediente);
            }
        }

        llenarTabla(filtrados);
    }

    private void llenarTabla(List<IngredienteDTO> ingredientes) {
        modeloTabla.setRowCount(0);

        for (IngredienteDTO ingrediente : ingredientes) {
            Object[] fila = {
                obtenerIdIngrediente(ingrediente),
                obtenerNombre(ingrediente),
                obtenerUnidadMedida(ingrediente),
                construirTextoStock(ingrediente),
                calcularDisponibilidad(ingrediente),
                ""
            };

            modeloTabla.addRow(fila);
        }
    }

    public void recargarTabla() {
        cargarIngredientes();
    }

    private Long obtenerIdIngrediente(IngredienteDTO ingrediente) {
        return ingrediente.getIdIngrediente();
    }

    private String obtenerNombre(IngredienteDTO ingrediente) {
        return ingrediente.getNombre() != null ? ingrediente.getNombre() : "-";
    }

    private String obtenerUnidadMedida(IngredienteDTO ingrediente) {
        if (ingrediente.getUnidadMedida() == null) {
            return "-";
        }
        return ingrediente.getUnidadMedida().toString();
    }

    private String construirTextoStock(IngredienteDTO ingrediente) {
        String unidad = obtenerUnidadMedida(ingrediente);
        double stock = obtenerValorNumerico(ingrediente.getStockActual());

        if (stock == (long) stock) {
            return String.format("%d %s", (long) stock, unidad);
        }

        return String.format("%.2f %s", stock, unidad);
    }

    private String calcularDisponibilidad(IngredienteDTO ingrediente) {
        double stock = obtenerValorNumerico(ingrediente.getStockActual());
        double umbral = obtenerValorNumerico(ingrediente.getUmbral());

        if (stock <= 0) {
            return "Agotado";
        }

        if (stock <= umbral) {
            return "Stock Bajo";
        }

        return "Disponible";
    }

    private double obtenerValorNumerico(Object valor) {
        if (valor == null) {
            return 0;
        }

        if (valor instanceof Number) {
            return ((Number) valor).doubleValue();
        }

        try {
            return Double.parseDouble(valor.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}