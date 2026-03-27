package Pantallas;

import Componentes.BotonEditar;
import Componentes.BotonRedondeado;
import Componentes.MenuLateralPanel;
import Controlador.Coordinador;
import Estilos.Dimensiones;
import Estilos.PaletaColores;
import dtos.ClienteFrecuenteDTO;
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
import javax.swing.table.JTableHeader;

/**
 *
 * @author Mariana
 */
public class FrmClientesFrecuentes extends JFrame{
    
    private final Coordinador coordinador;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public FrmClientesFrecuentes(Coordinador coordinador) {
        this.coordinador = coordinador;
        setTitle("Restaurante Le Pusse - Clientes Frecuentes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setMinimumSize(new Dimension(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new MenuLateralPanel("Clientes Frecuentes", coordinador), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);

        cargarClientesEnTabla();
    }

    private JPanel crearContenidoPrincipal() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(PaletaColores.BEIGE_FONDO);
        contenido.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(PaletaColores.BLANCO);
        cabecera.setBorder(new EmptyBorder(26, 28, 20, 28));

        JLabel titulo = new JLabel("Clientes Frecuentes");
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
        superior.setBorder(new EmptyBorder(0, 0, 18, 0));

        JPanel izquierda = new JPanel();
        izquierda.setOpaque(false);
        izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.X_AXIS));

        JTextField txtBuscar = new JTextField("Buscar por nombre...");
        txtBuscar.setPreferredSize(new Dimension(340, 42));
        txtBuscar.setMaximumSize(new Dimension(340, 42));
        txtBuscar.setMinimumSize(new Dimension(340, 42));
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBuscar.setForeground(new Color(180, 155, 130));
        txtBuscar.setBackground(PaletaColores.BLANCO);
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1),
                new EmptyBorder(0, 14, 0, 14)
        ));

        izquierda.add(txtBuscar);
        izquierda.add(Box.createRigidArea(new Dimension(16, 0)));
        izquierda.add(crearBotonFiltro("Nombre", true));
        izquierda.add(Box.createRigidArea(new Dimension(10, 0)));
        izquierda.add(crearBotonFiltro("Teléfono", false));
        izquierda.add(Box.createRigidArea(new Dimension(10, 0)));
        izquierda.add(crearBotonFiltro("Correo", false));

        BotonRedondeado btnNuevo = new BotonRedondeado("Nuevo Cliente", 20);
        btnNuevo.setPreferredSize(new Dimension(145, 40));
        btnNuevo.setMinimumSize(new Dimension(145, 40));
        btnNuevo.setMaximumSize(new Dimension(145, 40));
        btnNuevo.setBackground(PaletaColores.DORADO);
        btnNuevo.setForeground(PaletaColores.MARRON_OSCURO);
        btnNuevo.setFont(new Font("Segoe UI", Font.BOLD, 13));

        btnNuevo.addActionListener(e -> coordinador.mostrarRegistroClienteFrecuente());

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnNuevo);

        superior.add(izquierda, BorderLayout.WEST);
        superior.add(panelBoton, BorderLayout.EAST);

        return superior;
    }

    private BotonRedondeado crearBotonFiltro(String texto, boolean activo) {
        BotonRedondeado boton = new BotonRedondeado(texto, 18);
        boton.setPreferredSize(new Dimension(110, 40));
        boton.setMaximumSize(new Dimension(110, 40));
        boton.setMinimumSize(new Dimension(110, 40));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));

        if (activo) {
            boton.setBackground(PaletaColores.MARRON_OSCURO);
            boton.setForeground(PaletaColores.BLANCO);
        } else {
            boton.setBackground(PaletaColores.DORADO);
            boton.setForeground(PaletaColores.MARRON_OSCURO);
        }

        return boton;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PaletaColores.BLANCO);
        panel.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1));

        panel.add(crearTablaClientes(), BorderLayout.CENTER);
        return panel;
    }

    private JScrollPane crearTablaClientes() {
        String[] columnas = {
            "ID", "Nombre Completo", "Teléfono", "Correo", "Fecha Registro",
            "Visitas", "Total Gastado", "Puntos", "Acciones"
        };

        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setRowHeight(46);
        tablaClientes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaClientes.setForeground(PaletaColores.TEXTO_MARRON);
        tablaClientes.setBackground(PaletaColores.BLANCO);
        tablaClientes.setShowVerticalLines(false);
        tablaClientes.setShowHorizontalLines(true);
        tablaClientes.setGridColor(PaletaColores.LINEA_SUAVE);
        tablaClientes.setFocusable(false);
        tablaClientes.setSelectionBackground(PaletaColores.BLANCO_SUAVE);
        tablaClientes.setSelectionForeground(PaletaColores.TEXTO_MARRON);
        tablaClientes.setIntercellSpacing(new Dimension(0, 1));
        tablaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader header = tablaClientes.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(PaletaColores.BLANCO);
        header.setForeground(PaletaColores.TEXTO_MARRON);
        header.setPreferredSize(new Dimension(0, 54));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PaletaColores.LINEA_SUAVE));

        tablaClientes.getColumnModel().getColumn(0).setMinWidth(0);
        tablaClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(0);

        tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(240);
        tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(200);
        tablaClientes.getColumnModel().getColumn(4).setPreferredWidth(120);
        tablaClientes.getColumnModel().getColumn(5).setPreferredWidth(80);
        tablaClientes.getColumnModel().getColumn(6).setPreferredWidth(120);
        tablaClientes.getColumnModel().getColumn(7).setPreferredWidth(83);
        tablaClientes.getColumnModel().getColumn(8).setPreferredWidth(80);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        centrado.setBackground(PaletaColores.BLANCO);
        centrado.setForeground(PaletaColores.TEXTO_MARRON);

        tablaClientes.getColumnModel().getColumn(4).setCellRenderer(centrado);
        tablaClientes.getColumnModel().getColumn(5).setCellRenderer(centrado);
        tablaClientes.getColumnModel().getColumn(6).setCellRenderer(centrado);
        tablaClientes.getColumnModel().getColumn(7).setCellRenderer(centrado);
        tablaClientes.getColumnModel().getColumn(8).setCellRenderer(new BotonEditar());

        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int filaSeleccionada = tablaClientes.getSelectedRow();
                    Long idCliente = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
                    coordinador.mostrarEditarClienteFrecuente(idCliente);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tablaClientes);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(PaletaColores.BLANCO);
        scroll.setBackground(PaletaColores.BLANCO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scroll;
    }

    private void cargarClientesEnTabla() {
        try {
            modeloTabla.setRowCount(0);

            List<ClienteFrecuenteDTO> clientes = coordinador.obtenerClientesFrecuentes();

            for (ClienteFrecuenteDTO cliente : clientes) {
                Object[] fila = {
                    cliente.getIdCliente(),
                    construirNombreCompleto(cliente),
                    cliente.getTelefono() != null ? cliente.getTelefono() : "-",
                    (cliente.getCorreoElectronico() != null && !cliente.getCorreoElectronico().isBlank())
                            ? cliente.getCorreoElectronico() : "-",
                    cliente.getFechaRegistro() != null ? cliente.getFechaRegistro().toString() : "-",
                    cliente.getNumeroVisitas() != null ? cliente.getNumeroVisitas() : 0,
                    cliente.getTotalGastado() != null ? "$" + String.format("%.2f", cliente.getTotalGastado()) : "$0.00",
                    cliente.getPuntosFidelidad() != null ? cliente.getPuntosFidelidad() + " pts" : "0 pts",
                    ""
                };

                modeloTabla.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los clientes frecuentes: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String construirNombreCompleto(ClienteFrecuenteDTO cliente) {
        StringBuilder nombreCompleto = new StringBuilder();

        if (cliente.getNombre() != null && !cliente.getNombre().isBlank()) {
            nombreCompleto.append(cliente.getNombre().trim());
        }
        if (cliente.getApellidoPaterno() != null && !cliente.getApellidoPaterno().isBlank()) {
            if (nombreCompleto.length() > 0) {
                nombreCompleto.append(" ");
            }
            nombreCompleto.append(cliente.getApellidoPaterno().trim());
        }
        if (cliente.getApellidoMaterno() != null && !cliente.getApellidoMaterno().isBlank()) {
            if (nombreCompleto.length() > 0) {
                nombreCompleto.append(" ");
            }
            nombreCompleto.append(cliente.getApellidoMaterno().trim());
        }

        return nombreCompleto.toString();
    }

    public void recargarTabla() {
        cargarClientesEnTabla();
    }

}
