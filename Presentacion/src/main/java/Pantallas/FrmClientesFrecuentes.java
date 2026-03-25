package Pantallas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Mariana
 */
public class FrmClientesFrecuentes extends JFrame{
    
    private final Color MARRON_OSCURO = new Color(92, 70, 60);
    private final Color BEIGE_FONDO = new Color(235, 225, 210);
    private final Color DORADO = new Color(210, 195, 170);
    private final Color BLANCO = new Color(255, 255, 255);

    public FrmClientesFrecuentes() {
        setTitle("Restaurante Le Pusse - Clientes Frecuentes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750); // Mantenemos tu tamaño exacto
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearSidebar(), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setBackground(MARRON_OSCURO);

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(30, 15, 0, 15));

        JLabel tit = new JLabel("Restaurante Le Pusse");
        tit.setForeground(Color.WHITE);
        tit.setFont(new Font("Segoe UI", Font.BOLD, 20));
        menu.add(tit);
        menu.add(Box.createRigidArea(new Dimension(0, 40)));

        String[] opciones = {"Inicio", "Productos", "Ingredientes", "Clientes Frecuentes", "Reportes"};
        for (String opt : opciones) {
            JButton btn = new JButton(opt);
            btn.setMaximumSize(new Dimension(240, 45));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setOpaque(false); // IMPORTANTE: Evita el cuadro blanco al pasar el cursor
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            if(opt.equals("Clientes Frecuentes")) {
                btn.setOpaque(true);
                btn.setBackground(new Color(255, 255, 255, 40));
                btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            }
            
            menu.add(btn);
            menu.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        sidebar.add(menu, BorderLayout.CENTER);
        return sidebar;
    }

    private JPanel crearContenidoPrincipal() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BEIGE_FONDO);
        main.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTitulo = new JLabel("Clientes Frecuentes");
        lblTitulo.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 36));
        lblTitulo.setForeground(MARRON_OSCURO);
        main.add(lblTitulo, BorderLayout.NORTH);

        // Panel de Control
        JPanel panelControl = new JPanel(new BorderLayout());
        panelControl.setOpaque(false);
        panelControl.setBorder(new EmptyBorder(30, 0, 20, 0));

        // Buscador y Filtros
        JPanel buscadorFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buscadorFiltros.setOpaque(false);
        
        JTextField txtBuscar = new JTextField("  Buscar por nombre...");
        txtBuscar.setPreferredSize(new Dimension(300, 40));
        txtBuscar.setBorder(new LineBorder(new Color(180, 180, 180), 1));
        buscadorFiltros.add(txtBuscar);

        buscadorFiltros.add(crearBotonFiltro("Nombre", true));
        buscadorFiltros.add(crearBotonFiltro("Teléfono", false));
        buscadorFiltros.add(crearBotonFiltro("Correo", false));

        // Botón Nuevo
        JButton btnNuevo = new JButton("Nuevo Cliente —");
        btnNuevo.setBackground(DORADO);
        btnNuevo.setForeground(MARRON_OSCURO);
        btnNuevo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnNuevo.setPreferredSize(new Dimension(160, 40));
        btnNuevo.setFocusPainted(false);
        btnNuevo.setBorder(BorderFactory.createLineBorder(MARRON_OSCURO, 1));

        panelControl.add(buscadorFiltros, BorderLayout.WEST);
        panelControl.add(btnNuevo, BorderLayout.EAST);

        // Tabla
        String[] col = {"Nombre Completo", "Teléfono", "Correo", "Registro", "Visitas", "Gasto", "Puntos", "Acciones"};
        DefaultTableModel model = new DefaultTableModel(null, col) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        model.addRow(new Object[]{"Isaac Iran Fierro", "687-161-4264", "-", "20/03/26", "3", "$850", "42 pts", ""});
        model.addRow(new Object[]{"Regina Jiménez", "644-249-1867", "regina@mail.com", "18/02/26", "12", "$3240", "162 pts", ""});

        JTable tabla = new JTable(model);
        tabla.setRowHeight(50);
        tabla.setShowGrid(false);
        tabla.setFocusable(false); // Quita el borde azul de selección
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.getTableHeader().setReorderingAllowed(false);
        
        // Estilo Header
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        tabla.getColumnModel().getColumn(7).setCellRenderer(new IconRenderer("/editar.png"));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        scroll.getViewport().setBackground(Color.WHITE);
        
        JPanel tablaContainer = new JPanel(new BorderLayout());
        tablaContainer.add(panelControl, BorderLayout.NORTH);
        tablaContainer.add(scroll, BorderLayout.CENTER);
        
        main.add(tablaContainer, BorderLayout.CENTER);

        return main;
    }

    private JButton crearBotonFiltro(String texto, boolean activo) {
        JButton b = new JButton(texto);
        b.setPreferredSize(new Dimension(90, 40));
        b.setBackground(activo ? MARRON_OSCURO : DORADO);
        b.setForeground(activo ? Color.WHITE : MARRON_OSCURO);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return b;
    }

    class IconRenderer extends DefaultTableCellRenderer {
        private JLabel label = new JLabel();
        private ImageIcon icon;

        public IconRenderer(String path) {
            URL url = getClass().getResource(path);
            if (url != null) {
                icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            }
            label.setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            label.setIcon(icon);
            label.setOpaque(true);
            label.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmClientesFrecuentes().setVisible(true));
    }
}
