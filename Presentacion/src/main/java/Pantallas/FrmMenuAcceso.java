package Pantallas;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Interfaz de Acceso al Sistema
 * 
 * @author Mariana
 */
public class FrmMenuAcceso extends JFrame {
    private final Color COLOR_BEIGE_FONDO = new Color(245, 241, 235);
    private final Color COLOR_MARRON_TEXTO = new Color(92, 70, 60);
    private final Color COLOR_CREMA_TARJETA = new Color(252, 250, 247);

    public FrmMenuAcceso() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Acceso al Sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setSize(1100, 750); 
        setLocationRelativeTo(null); 
        
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(COLOR_BEIGE_FONDO);
        setContentPane(panelPrincipal);

        JPanel contenedorBusqueda = new JPanel();
        contenedorBusqueda.setOpaque(false);
        contenedorBusqueda.setLayout(new BoxLayout(contenedorBusqueda, BoxLayout.Y_AXIS));

        // Títulos
        JLabel lblBienvenido = new JLabel("Bienvenido al Sistema");
        lblBienvenido.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblBienvenido.setForeground(COLOR_MARRON_TEXTO);
        lblBienvenido.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSeleccione = new JLabel("Seleccione su tipo de acceso");
        lblSeleccione.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSeleccione.setForeground(COLOR_MARRON_TEXTO);
        lblSeleccione.setAlignmentX(CENTER_ALIGNMENT);
        lblSeleccione.setBorder(new EmptyBorder(0, 0, 50, 0));

        contenedorBusqueda.add(lblBienvenido);
        contenedorBusqueda.add(lblSeleccione);

        JPanel panelTarjetas = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        panelTarjetas.setOpaque(false);

        panelTarjetas.add(crearTarjeta("Empleado", "/usuario.png"));
        panelTarjetas.add(crearTarjeta("Administrador", "/escudo.png"));

        contenedorBusqueda.add(panelTarjetas);
        
        JSeparator linea = new JSeparator();
        linea.setForeground(COLOR_MARRON_TEXTO);
        JPanel pLinea = new JPanel(new BorderLayout());
        pLinea.setOpaque(false);
        pLinea.setBorder(new EmptyBorder(100, 0, 0, 0)); // Ajustado margen de la línea
        pLinea.add(linea);
        contenedorBusqueda.add(pLinea);

        panelPrincipal.add(contenedorBusqueda);
    }

    private JPanel crearTarjeta(String texto, String rutaImagen) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(COLOR_CREMA_TARJETA);
        
        tarjeta.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1), 
            new EmptyBorder(40, 60, 40, 60)
        ));

        JLabel lblTitulo = new JLabel(texto);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(COLOR_MARRON_TEXTO);
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblIcono = new JLabel();
        try {
            // Intento de carga robusta
            URL url = getClass().getResource(rutaImagen);
            if (url == null) {
                // Segundo intento si el primero falla 
                url = getClass().getResource("/resources" + rutaImagen);
            }

            if (url != null) {
                ImageIcon iconoOriginal = new ImageIcon(url);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                lblIcono.setIcon(new ImageIcon(imgEscalada));
            } else {
                lblIcono.setText("<html><center>Icono<br>no encontrado</center></html>");
                lblIcono.setForeground(Color.RED);
            }
        } catch (Exception e) {
            lblIcono.setText("Error");
        }
        lblIcono.setAlignmentX(CENTER_ALIGNMENT);
        lblIcono.setBorder(new EmptyBorder(25, 0, 35, 0));

        JButton btn = new JButton("Ingresar");
        btn.setBackground(COLOR_MARRON_TEXTO);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_MARRON_TEXTO),
            BorderFactory.createEmptyBorder(12, 45, 12, 45)
        ));
        btn.setAlignmentX(CENTER_ALIGNMENT);

        tarjeta.add(lblTitulo);
        tarjeta.add(lblIcono);
        tarjeta.add(btn);
        
        return tarjeta;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmMenuAcceso().setVisible(true));
    }
}