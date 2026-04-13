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
        btnEliminar.setBackground(new Color(200, 70, 70));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> eliminarCliente());

        BotonRedondeado btnCancelar = new BotonRedondeado("Cancelar", 18);
        btnCancelar.addActionListener(e -> {
            coordinador.regresarAGestionClientes();
            dispose();
        });

        BotonRedondeado btnGuardar = new BotonRedondeado("Guardar", 18);
        btnGuardar.addActionListener(e -> guardarCambios());

        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        formulario.add(panelBotones, gbc);

        panelExterno.add(formulario, BorderLayout.CENTER);
        return panelExterno;
    }

    private void cargarDatosCliente() {
        if (clienteDTO == null) return;

        txtPrimerNombre.setText(clienteDTO.getNombre());
        txtApellidoPaterno.setText(clienteDTO.getApellidoPaterno());
        txtApellidoMaterno.setText(clienteDTO.getApellidoMaterno());
        txtTelefono.setText(clienteDTO.getTelefono()); // YA DESENCRIPTADO
        txtCorreo.setText(clienteDTO.getCorreoElectronico());
    }

    // 🔥 MÉTODO CLAVE CORREGIDO
    private void guardarCambios() {

        String nombre = txtPrimerNombre.getText().trim();
        String apP = txtApellidoPaterno.getText().trim();
        String apM = txtApellidoMaterno.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();

        // VALIDACIONES
        if (nombre.isEmpty() || apP.isEmpty() || apM.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
            return;
        }

        if (!telefono.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe tener exactamente 10 dígitos.");
            return;
        }

        if (!correo.isEmpty() && !correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Correo electrónico inválido.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Confirmas los cambios?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) return;

        try {
            clienteDTO.setNombre(nombre);
            clienteDTO.setApellidoPaterno(apP);
            clienteDTO.setApellidoMaterno(apM);
            clienteDTO.setTelefono(telefono); // 🔥 AQUÍ VA NORMAL (BO lo cifra)
            clienteDTO.setCorreoElectronico(correo.isEmpty() ? null : correo);

            coordinador.actualizarClienteFrecuente(clienteDTO);

            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
            coordinador.regresarDesdeEditarCliente();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente() {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar este cliente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) return;

        try {
            coordinador.eliminarCliente(clienteDTO.getIdCliente());
            JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente");
            coordinador.regresarDesdeEditarCliente();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
        }
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int col, int row, String texto, boolean obligatorio, JTextField campo) {
        gbc.gridx = col;
        gbc.gridy = row;
        panel.add(new JLabel(texto), gbc);
        gbc.gridy = row + 1;
        panel.add(campo, gbc);
    }

    private void estilizarCampo(JTextField campo) {
        campo.setPreferredSize(new Dimension(235, 34));
    }
}