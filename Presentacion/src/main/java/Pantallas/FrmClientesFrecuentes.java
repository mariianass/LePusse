package Pantallas;

import Componentes.BotonEditar;
import Componentes.BotonRedondeado;
import Componentes.MenuLateralPanel;
import Controlador.Coordinador;
import Estilos.Dimensiones;
import Estilos.PaletaColores;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
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
        
        btnNuevo.addActionListener(e -> {
            coordinador.mostrarRegistroClienteFrecuente();
        });

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
            "Nombre Completo", "Teléfono", "Correo", "Fecha Registro",
            "Visitas", "Total Gastado", "Puntos", "Acciones"
        };

        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        modelo.addRow(new Object[]{"Isaac Iran Fierro Gerhardus", "687-161-4264", "-", "20/03/2026", "3", "$850.00", "42 pts", ""});
        modelo.addRow(new Object[]{"Regina Jiménez Meneses", "644-249-1867", "reginamenesesj09@gmail.com", "18/02/2026", "12", "$3240.00", "162 pts", ""});
        modelo.addRow(new Object[]{"Jesus Cisneros Valenzuela", "644-202-6452", "-", "03/03/2026", "6", "$1320.00", "66 pts", ""});
        modelo.addRow(new Object[]{"Jose Trista Rosales", "644-206-3355", "jose.tristar@gmail.com", "15/03/2026", "1", "$200.00", "10 pts", ""});
        modelo.addRow(new Object[]{"German Fetuccini Ruiz", "644-158-5678", "germanfetuccini01@hotmail.com", "12/02/2026", "12", "$6800.00", "340 pts", ""});

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(46);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setForeground(PaletaColores.TEXTO_MARRON);
        tabla.setBackground(PaletaColores.BLANCO);
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(PaletaColores.LINEA_SUAVE);
        tabla.setFocusable(false);
        tabla.setSelectionBackground(PaletaColores.BLANCO_SUAVE);
        tabla.setSelectionForeground(PaletaColores.TEXTO_MARRON);
        tabla.setIntercellSpacing(new Dimension(0, 1));
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(PaletaColores.BLANCO);
        header.setForeground(PaletaColores.TEXTO_MARRON);
        header.setPreferredSize(new Dimension(0, 54));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PaletaColores.LINEA_SUAVE));

        tabla.getColumnModel().getColumn(0).setPreferredWidth(240);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(83);
        tabla.getColumnModel().getColumn(7).setPreferredWidth(80);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        centrado.setBackground(PaletaColores.BLANCO);
        centrado.setForeground(PaletaColores.TEXTO_MARRON);

        tabla.getColumnModel().getColumn(3).setCellRenderer(centrado);
        tabla.getColumnModel().getColumn(4).setCellRenderer(centrado);
        tabla.getColumnModel().getColumn(5).setCellRenderer(centrado);
        tabla.getColumnModel().getColumn(6).setCellRenderer(centrado);
        tabla.getColumnModel().getColumn(7).setCellRenderer(new BotonEditar());

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(PaletaColores.BLANCO);
        scroll.setBackground(PaletaColores.BLANCO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scroll;
    }

}
