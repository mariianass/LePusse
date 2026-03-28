package Pantallas;

import Componentes.BotonRedondeado;
import Componentes.MenuLateralPanel;
import Controlador.Coordinador;
import Estilos.Dimensiones;
import Estilos.PaletaColores;
import dtos.ClienteFrecuenteDTO;
import interfaces.IClienteFrecuenteBO;
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
public class FrmEditarClienteFrecuente extends JFrame {

    private final Coordinador coordinador;
    private final ClienteFrecuenteDTO clienteDTO;

    private JTextField txtPrimerNombre;
    private JTextField txtSegundoNombre;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JTextField txtTelefono;
    private JTextField txtCorreo;

    public FrmEditarClienteFrecuente(Coordinador coordinador, ClienteFrecuenteDTO clienteDTO) {
        this.coordinador = coordinador;
        this.clienteDTO = clienteDTO;

        setTitle("Restaurante Le Pusse - Editar Cliente Frecuente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        add(new MenuLateralPanel("Clientes Frecuentes", coordinador), BorderLayout.WEST);
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

        BotonRedondeado btnEliminar = new BotonRedondeado("Eliminar", 18);
        btnEliminar.setPreferredSize(new Dimension(125, 40));
        btnEliminar.setBackground(new Color(200, 70, 70)); // rojo elegante
        btnEliminar.setForeground(PaletaColores.BLANCO);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));

        btnEliminar.addActionListener(e -> eliminarCliente());

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

        btnCancelar.addActionListener(e -> {
            coordinador.regresarAGestionClientes();
            dispose();
        });

        btnGuardar.addActionListener(e -> guardarCambios());

        panelBotones.add(btnEliminar);
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
        if (clienteDTO == null) {
            return;
        }

        txtPrimerNombre.setText(clienteDTO.getNombre() != null ? clienteDTO.getNombre() : "");
        txtApellidoPaterno.setText(clienteDTO.getApellidoPaterno() != null ? clienteDTO.getApellidoPaterno() : "");
        txtApellidoMaterno.setText(clienteDTO.getApellidoMaterno() != null ? clienteDTO.getApellidoMaterno() : "");
        txtTelefono.setText(clienteDTO.getTelefono() != null ? clienteDTO.getTelefono() : "");
        txtCorreo.setText(clienteDTO.getCorreoElectronico() != null ? clienteDTO.getCorreoElectronico() : "");
        txtSegundoNombre.setText("");
    }

    private void guardarCambios() {
        if (txtPrimerNombre.getText().trim().isEmpty()
                || txtApellidoPaterno.getText().trim().isEmpty()
                || txtApellidoMaterno.getText().trim().isEmpty()
                || txtTelefono.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Confirmas los cambios?",
                "Seleccionar una opción",
                JOptionPane.YES_NO_CANCEL_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            clienteDTO.setNombre(txtPrimerNombre.getText().trim());
            clienteDTO.setApellidoPaterno(txtApellidoPaterno.getText().trim());
            clienteDTO.setApellidoMaterno(txtApellidoMaterno.getText().trim());
            clienteDTO.setTelefono(txtTelefono.getText().trim());

            String correo = txtCorreo.getText().trim();
            clienteDTO.setCorreoElectronico(correo.isEmpty() ? null : correo);

            coordinador.actualizarClienteFrecuente(clienteDTO);

            JOptionPane.showMessageDialog(this, "¡Cliente actualizado exitosamente!");
            coordinador.regresarDesdeEditarCliente();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarCliente() {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar este cliente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            coordinador.eliminarCliente(clienteDTO.getIdCliente());

            JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente");

            coordinador.regresarDesdeEditarCliente();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
        }
    }

}
