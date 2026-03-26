package Pantallas;

import Componentes.BotonRedondeado;
import Componentes.MenuLateralPanel;
import DAOs.ClienteDAO;
import Estilos.Dimensiones;
import Estilos.PaletaColores;
import entidades.Cliente;
import excepciones.PersistenciaException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
public class FrmEditarClienteFrecuente extends JFrame{
    
    private Cliente cliente;

    private JTextField txtPrimerNombre;
    private JTextField txtSegundoNombre;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JTextField txtTelefono;
    private JTextField txtCorreo;

    public FrmEditarClienteFrecuente(Cliente cliente) {
        this.cliente = cliente;

        setTitle("Restaurante Le Pusse - Editar Cliente Frecuente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setMinimumSize(new Dimension(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new MenuLateralPanel("Clientes Frecuentes"), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);

        cargarDatosCliente();
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

        JLabel lblTitulo = new JLabel("Editar Cliente Frecuente");
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

        BotonRedondeado btnGuardar = new BotonRedondeado("Guardar", 18);
        btnGuardar.setPreferredSize(new Dimension(125, 40));
        btnGuardar.setBackground(PaletaColores.MARRON_OSCURO);
        btnGuardar.setForeground(PaletaColores.BLANCO);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 13));

        btnCancelar.addActionListener(e -> dispose());
        btnGuardar.addActionListener(e -> guardarCambios());

        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);

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

    private void cargarDatosCliente() {
        if (cliente == null) {
            return;
        }

        txtPrimerNombre.setText(cliente.getNombre());
        txtApellidoPaterno.setText(cliente.getApellidoPaterno());
        txtApellidoMaterno.setText(cliente.getApellidoMaterno());
        txtTelefono.setText(cliente.getTelefono());
        txtCorreo.setText(cliente.getCorreoElectronico());

        txtSegundoNombre.setText("");
    }

    private void guardarCambios() {
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

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Confirmas los cambios?",
                "Seleccionar una opción",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            cliente.setNombre(txtPrimerNombre.getText().trim());
            cliente.setApellidoPaterno(txtApellidoPaterno.getText().trim());
            cliente.setApellidoMaterno(txtApellidoMaterno.getText().trim());
            cliente.setTelefono(txtTelefono.getText().trim());

            String correo = txtCorreo.getText().trim();
            cliente.setCorreoElectronico(correo.isEmpty() ? null : correo);

            ClienteDAO.getInstance().editar(cliente);

            JOptionPane.showMessageDialog(
                    this,
                    "¡Cliente actualizado exitosamente!",
                    "Mensaje",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar el cliente: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Cliente clientePrueba = new Cliente();
            clientePrueba.setId(1L);
            clientePrueba.setNombre("Isaac");
            clientePrueba.setApellidoPaterno("Fierro");
            clientePrueba.setApellidoMaterno("Gerhardus");
            clientePrueba.setTelefono("687-161-4264");
            clientePrueba.setCorreoElectronico("");

            new FrmEditarClienteFrecuente(clientePrueba).setVisible(true);
        });
    }
    
}
