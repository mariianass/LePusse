package Pantallas;

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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Mariana
 */
public class FrmRegistrarClienteFrecuente extends JFrame{
    
    private final Coordinador coordinador;
    
    private JTextField txtPrimerNombre;
    private JTextField txtSegundoNombre;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JTextField txtTelefono;
    private JTextField txtCorreo;

    public FrmRegistrarClienteFrecuente(Coordinador coordinador) {
        this.coordinador = coordinador;
        setTitle("Restaurante Le Pusse - Registrar Cliente Frecuente");
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

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(PaletaColores.BLANCO);
        cabecera.setBorder(new EmptyBorder(22, 28, 22, 28));

        JLabel titulo = new JLabel("Clientes Frecuentes");
        titulo.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 32));
        titulo.setForeground(PaletaColores.TEXTO_MARRON);
        cabecera.add(titulo, BorderLayout.WEST);

        contenido.add(cabecera, BorderLayout.NORTH);

        JPanel fondoCentral = new JPanel(new BorderLayout());
        fondoCentral.setBackground(new Color(239, 227, 204));
        fondoCentral.setBorder(new EmptyBorder(28, 34, 34, 34));

        fondoCentral.add(crearPanelFormulario(), BorderLayout.CENTER);

        contenido.add(fondoCentral, BorderLayout.CENTER);

        return contenido;
    }

    private JPanel crearPanelFormulario() {
        JPanel panelExterno = new JPanel(new BorderLayout());
        panelExterno.setOpaque(false);

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

        JLabel lblTitulo = new JLabel("Registrar Cliente Frecuente");
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblTitulo.setForeground(PaletaColores.TEXTO_MARRON);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formulario.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        txtPrimerNombre = new JTextField();
        txtSegundoNombre = new JTextField();
        txtApellidoPaterno = new JTextField();
        txtApellidoMaterno = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();

        estilizarCampo(txtPrimerNombre);
        estilizarCampo(txtSegundoNombre);
        estilizarCampo(txtApellidoPaterno);
        estilizarCampo(txtApellidoMaterno);
        estilizarCampo(txtTelefono);
        estilizarCampo(txtCorreo);

        agregarCampo(formulario, gbc, 0, 1, "Primer Nombre", true, txtPrimerNombre);
        agregarCampo(formulario, gbc, 1, 1, "Segundo Nombre", false, txtSegundoNombre);

        agregarCampo(formulario, gbc, 0, 3, "Apellido Paterno", true, txtApellidoPaterno);
        agregarCampo(formulario, gbc, 1, 3, "Apellido Materno", true, txtApellidoMaterno);

        agregarCampo(formulario, gbc, 0, 5, "Número de Teléfono", true, txtTelefono);
        agregarCampo(formulario, gbc, 0, 7, "Correo Electrónico", false, txtCorreo);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);

        BotonRedondeado btnCancelar = new BotonRedondeado("Cancelar", 18);
        btnCancelar.setPreferredSize(new Dimension(130, 40));
        btnCancelar.setBackground(new Color(232, 216, 182));
        btnCancelar.setForeground(PaletaColores.TEXTO_MARRON);
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        BotonRedondeado btnRegistrar = new BotonRedondeado("Registrar Cliente", 18);
        btnRegistrar.setPreferredSize(new Dimension(155, 40));
        btnRegistrar.setBackground(PaletaColores.MARRON_OSCURO);
        btnRegistrar.setForeground(PaletaColores.BLANCO);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        btnRegistrar.addActionListener(e -> {
                // Extraer los datos de los campos de texto
                String pNombre = txtPrimerNombre.getText().trim();
                String sNombre = txtSegundoNombre.getText().trim();
                String aPaterno = txtApellidoPaterno.getText().trim();
                String aMaterno = txtApellidoMaterno.getText().trim();
                String tel = txtTelefono.getText().trim();
                String correo = txtCorreo.getText().trim();

                // Crear y llenar el DTO
                ClienteFrecuenteDTO nuevoCliente = new ClienteFrecuenteDTO();
                nuevoCliente.setNombre(pNombre.concat(sNombre));
                nuevoCliente.setApellidoPaterno(aPaterno);
                nuevoCliente.setApellidoMaterno(aMaterno);
                nuevoCliente.setTelefono(tel);
                nuevoCliente.setCorreoElectronico(correo);
                nuevoCliente.setFechaRegistro(LocalDate.now());

            try {
                // Mandar el DTO al coordinador para procesar el registro
                coordinador.registrarClienteFrecuente(nuevoCliente);
            } catch (Exception ex) {
                System.out.println("Error al registrar cliente: " + ex.getMessage());
            }
                //Regresar a la tabla
                coordinador.regresarAGestionClientes();
        });
        
        
        btnCancelar.addActionListener(e -> {
            coordinador.regresarAGestionClientes();
        });

        panelBotones.add(btnCancelar);
        panelBotones.add(btnRegistrar);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(18, 12, 0, 12);
        formulario.add(panelBotones, gbc);

        panelExterno.add(formulario, BorderLayout.CENTER);

        return panelExterno;
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int columna, int filaBase,
            String textoEtiqueta, boolean obligatorio, JTextField campo) {

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
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

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

    private void limpiarCampos() {
        txtPrimerNombre.setText("");
        txtSegundoNombre.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }

    private void registrarCliente() {
        if (txtPrimerNombre.getText().trim().isEmpty()
                || txtApellidoPaterno.getText().trim().isEmpty()
                || txtApellidoMaterno.getText().trim().isEmpty()
                || txtTelefono.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Complete todos los campos obligatorios.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "¡Cliente creado exitosamente!",
                "Mensaje",
                JOptionPane.INFORMATION_MESSAGE
        );

        limpiarCampos();
    }
    
}
