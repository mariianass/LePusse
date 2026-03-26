package Pantallas;

import Componentes.BotonRedondeado;
import Estilos.Dimensiones;
import Estilos.PaletaColores;
import Recursos.CargadorRecursos;
import java.awt.*;
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
    
    public FrmMenuAcceso() {
            initComponents();
    }

    private void initComponents() {
        setTitle("Acceso al Sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setMinimumSize(new Dimension(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA));
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(PaletaColores.BLANCO);
        panelPrincipal.setBorder(new EmptyBorder(40, 40, 40, 40));
        setContentPane(panelPrincipal);

        JPanel contenedorCentral = new JPanel();
        contenedorCentral.setOpaque(false);
        contenedorCentral.setLayout(new BoxLayout(contenedorCentral, BoxLayout.Y_AXIS));

        JLabel lblBienvenido = new JLabel("Bienvenido al Sistema");
        lblBienvenido.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblBienvenido.setForeground(PaletaColores.TEXTO_MARRON);
        lblBienvenido.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSeleccione = new JLabel("Seleccione su tipo de acceso");
        lblSeleccione.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSeleccione.setForeground(PaletaColores.TEXTO_MARRON);
        lblSeleccione.setAlignmentX(CENTER_ALIGNMENT);

        contenedorCentral.add(Box.createVerticalStrut(40));
        contenedorCentral.add(lblBienvenido);
        contenedorCentral.add(Box.createVerticalStrut(8));
        contenedorCentral.add(lblSeleccione);
        contenedorCentral.add(Box.createVerticalStrut(55));

        JPanel panelTarjetas = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panelTarjetas.setOpaque(false);

        panelTarjetas.add(crearTarjeta("Empleado", "/usuario.png"));
        panelTarjetas.add(crearTarjeta("Administrador", "/escudo.png"));

        contenedorCentral.add(panelTarjetas);
        contenedorCentral.add(Box.createVerticalStrut(90));

        JSeparator linea = new JSeparator();
        linea.setForeground(new Color(140, 120, 105));
        linea.setMaximumSize(new Dimension(950, 1));
        linea.setPreferredSize(new Dimension(950, 1));

        JPanel panelLinea = new JPanel();
        panelLinea.setOpaque(false);
        panelLinea.add(linea);

        contenedorCentral.add(panelLinea);

        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);
    }

    private JPanel crearTarjeta(String titulo, String rutaIcono) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(PaletaColores.BLANCO_SUAVE);
        tarjeta.setPreferredSize(new Dimension(310, 440));
        tarjeta.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(35, 40, 35, 40)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(PaletaColores.TEXTO_MARRON);
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblIcono = new JLabel();
        lblIcono.setAlignmentX(CENTER_ALIGNMENT);
        lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcono.setBorder(new EmptyBorder(35, 0, 40, 0));

        ImageIcon icono = CargadorRecursos.cargarIcono(rutaIcono, 120, 120);
        if (icono != null) {
            lblIcono.setIcon(icono);
        } else {
            lblIcono.setText("Icono no encontrado");
            lblIcono.setForeground(Color.RED);
        }

        BotonRedondeado btnIngresar = new BotonRedondeado("Ingresar", 25);
        btnIngresar.setAlignmentX(CENTER_ALIGNMENT);
        btnIngresar.setBackground(PaletaColores.MARRON_OSCURO);
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setPreferredSize(new Dimension(210, 54));
        btnIngresar.setMaximumSize(new Dimension(210, 54));
        btnIngresar.setMinimumSize(new Dimension(210, 54));

        tarjeta.add(lblTitulo);
        tarjeta.add(lblIcono);
        
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnIngresar);
        
        tarjeta.add(panelBoton);

        return tarjeta;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmMenuAcceso().setVisible(true));
    }

}