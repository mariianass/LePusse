package Componentes;

import Controlador.Coordinador;
import Estilos.PaletaColores;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Mariana
 */
public class MenuLateralPanel extends JPanel{
    
    private final Coordinador coordinador;
    
    public MenuLateralPanel(String opcionActiva, Coordinador coordinador) {
        this.coordinador = coordinador;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(265, 768));
        setBackground(PaletaColores.MARRON_OSCURO);

        add(crearParteSuperior(opcionActiva), BorderLayout.CENTER);
        add(crearParteInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearParteSuperior(String opcionActiva) {
        JPanel superior = new JPanel();
        superior.setOpaque(false);
        superior.setLayout(new BoxLayout(superior, BoxLayout.Y_AXIS));
        superior.setBorder(new EmptyBorder(18, 16, 0, 16));

        JLabel titulo = new JLabel("Restaurante Le Pusse");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Sistema de Gestión");
        subtitulo.setForeground(new Color(245, 239, 232));
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel lineaSuperior = new JPanel();
        lineaSuperior.setBackground(new Color(139, 100, 83));
        lineaSuperior.setMaximumSize(new Dimension(220, 1));
        lineaSuperior.setPreferredSize(new Dimension(220, 1));
        lineaSuperior.setAlignmentX(Component.LEFT_ALIGNMENT);

        superior.add(titulo);
        superior.add(Box.createRigidArea(new Dimension(0, 8)));
        superior.add(subtitulo);
        superior.add(Box.createRigidArea(new Dimension(0, 18)));
        superior.add(lineaSuperior);
        superior.add(Box.createRigidArea(new Dimension(0, 28)));

        String[] opciones = {"Inicio", "Productos", "Ingredientes", "Clientes Frecuentes", "Reportes"};

        for (String opcion : opciones) {
            superior.add(crearBotonMenu(opcion, opcion.equals(opcionActiva)));
            superior.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        return superior;
    }

    private JPanel crearParteInferior() {
        JPanel inferior = new JPanel();
        inferior.setOpaque(false);
        inferior.setLayout(new BoxLayout(inferior, BoxLayout.Y_AXIS));
        inferior.setBorder(new EmptyBorder(0, 18, 22, 16));

        JPanel lineaInferior = new JPanel();
        lineaInferior.setBackground(new Color(139, 100, 83));
        lineaInferior.setMaximumSize(new Dimension(220, 1));
        lineaInferior.setPreferredSize(new Dimension(220, 1));
        lineaInferior.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nombre = new JLabel("Mariana Orduño");
        nombre.setForeground(Color.WHITE);
        nombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel rol = new JLabel("Administrador");
        rol.setForeground(new Color(245, 239, 232));
        rol.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        rol.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton cerrarSesion = new JButton("Cerrar Sesión");
        cerrarSesion.setForeground(Color.WHITE);
        cerrarSesion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cerrarSesion.setFocusPainted(false);
        cerrarSesion.setBorderPainted(false);
        cerrarSesion.setContentAreaFilled(false);
        cerrarSesion.setHorizontalAlignment(SwingConstants.LEFT);
        cerrarSesion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        cerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cerrarSesion.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        cerrarSesion.addActionListener(e -> {
        coordinador.cerrarSesion();
        });

        inferior.add(lineaInferior);
        inferior.add(Box.createRigidArea(new Dimension(0, 18)));
        inferior.add(nombre);
        inferior.add(Box.createRigidArea(new Dimension(0, 4)));
        inferior.add(rol);
        inferior.add(Box.createRigidArea(new Dimension(0, 16)));
        inferior.add(cerrarSesion);

        return inferior;
    }

    private JButton crearBotonMenu(String texto, boolean activo) {
        JButton boton = new JButton(texto);
        boton.setMaximumSize(new Dimension(220, 36));
        boton.setPreferredSize(new Dimension(220, 36));
        boton.setMinimumSize(new Dimension(220, 36));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));

        if (activo) {
            boton.setBackground(new Color(240, 228, 210));
            boton.setForeground(PaletaColores.MARRON_OSCURO);
            boton.setOpaque(true);
            boton.setContentAreaFilled(true);
            boton.setBorderPainted(false);
        } else {
            boton.setOpaque(false);
            boton.setContentAreaFilled(false);
            boton.setBorderPainted(false);
            boton.setForeground(Color.WHITE);
        }

        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        return boton;
    }
    
}
