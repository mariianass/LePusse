/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

import Componentes.BotonRedondeado;
import Componentes.MenuLateralPanel;
import Controlador.Coordinador;
import Estilos.Dimensiones;
import Estilos.PaletaColores;
import Validadores.Validadores;
import dtos.IngredienteDTO;
import enums.UnidadMedida;
import enumsDTO.UnidadMedidaDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla para registrar un nuevo ingrediente.
 *
 * @author regina, mariana e isaac
 */
public class FrmNuevoIngrediente extends JFrame {

    private final Coordinador coordinador;

    private JTextField txtNombreIngrediente;
    private JComboBox<String> cmbUnidadMedida;
    private JTextField txtCantidadInventario;
    private JTextField txtUmbral;

    public FrmNuevoIngrediente(Coordinador coordinador) {
        this.coordinador = coordinador;

        setTitle("Restaurante Le Pusse - Nuevo Ingrediente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setMinimumSize(new Dimension(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new MenuLateralPanel("Ingredientes", coordinador), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);
    }

    private JPanel crearContenidoPrincipal() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(PaletaColores.BEIGE_FONDO);
        contenido.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(PaletaColores.BLANCO);
        cabecera.setBorder(new EmptyBorder(22, 28, 18, 28));

        JLabel titulo = new JLabel("Ingredientes");
        titulo.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 34));
        titulo.setForeground(PaletaColores.TEXTO_MARRON);
        cabecera.add(titulo, BorderLayout.WEST);

        contenido.add(cabecera, BorderLayout.NORTH);

        JPanel zonaCentral = new JPanel(new BorderLayout());
        zonaCentral.setBackground(PaletaColores.BEIGE_PANEL);
        zonaCentral.setBorder(new EmptyBorder(34, 54, 34, 54));

        zonaCentral.add(crearPanelFormularioConSombra(), BorderLayout.CENTER);
        contenido.add(zonaCentral, BorderLayout.CENTER);

        return contenido;
    }

    private JPanel crearPanelFormularioConSombra() {
        JPanel contenedorSombra = new JPanel(new BorderLayout());
        contenedorSombra.setOpaque(false);

        JPanel sombra = new JPanel(new BorderLayout());
        sombra.setOpaque(false);
        sombra.setBorder(new EmptyBorder(8, 8, 8, 8));

        JPanel panelPrincipal = crearPanelFormulario();
        sombra.add(panelPrincipal, BorderLayout.CENTER);

        contenedorSombra.add(sombra, BorderLayout.CENTER);
        return contenedorSombra;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PaletaColores.BLANCO_SUAVE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 217, 208), 1),
                new EmptyBorder(26, 34, 26, 34)
        ));

        panel.add(crearCamposFormulario(), BorderLayout.CENTER);
        panel.add(crearPanelBotones(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearCamposFormulario() {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setOpaque(false);

        JPanel panelCampos = new JPanel();
        panelCampos.setOpaque(false);
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
        panelCampos.setPreferredSize(new Dimension(820, 420));
        panelCampos.setMaximumSize(new Dimension(820, 420));

        JLabel lblTitulo = new JLabel("Nuevo Ingrediente");
        lblTitulo.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 25));
        lblTitulo.setForeground(PaletaColores.TEXTO_MARRON);
        lblTitulo.setAlignmentX(LEFT_ALIGNMENT);

        panelCampos.add(lblTitulo);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 24)));

        JPanel filaNombre = new JPanel(new BorderLayout());
        filaNombre.setOpaque(false);
        filaNombre.add(crearEtiquetaConAsterisco("Nombre del Ingrediente"), BorderLayout.CENTER);
        panelCampos.add(filaNombre);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 10)));

        txtNombreIngrediente = crearCampoTexto();
        panelCampos.add(txtNombreIngrediente);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 18)));

        JPanel filaUnidad = new JPanel(new BorderLayout());
        filaUnidad.setOpaque(false);
        filaUnidad.add(crearEtiquetaConAsterisco("Unidad de Medida"), BorderLayout.CENTER);
        panelCampos.add(filaUnidad);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 10)));

        cmbUnidadMedida = crearComboUnidadMedida();
        panelCampos.add(cmbUnidadMedida);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 18)));

        JPanel filaCantidad = new JPanel(new BorderLayout());
        filaCantidad.setOpaque(false);
        filaCantidad.add(crearEtiquetaConAsterisco("Cantidad en Inventario"), BorderLayout.CENTER);
        panelCampos.add(filaCantidad);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 10)));

        txtCantidadInventario = crearCampoTexto();
        panelCampos.add(txtCantidadInventario);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 18)));

        JPanel filaUmbral = new JPanel(new BorderLayout());
        filaUmbral.setOpaque(false);
        filaUmbral.add(crearEtiquetaConAsterisco("Umbral"), BorderLayout.CENTER);
        panelCampos.add(filaUmbral);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 10)));

        txtUmbral = crearCampoTexto();
        panelCampos.add(txtUmbral);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 36)));

        contenedor.add(panelCampos, BorderLayout.CENTER);
        return contenedor;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setBorder(new EmptyBorder(6, 0, 0, 0));
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));

        panelBotones.add(Box.createHorizontalGlue());

        BotonRedondeado btnCancelar = new BotonRedondeado("Cancelar", 18);
        btnCancelar.setPreferredSize(new Dimension(175, 42));
        btnCancelar.setMinimumSize(new Dimension(175, 42));
        btnCancelar.setMaximumSize(new Dimension(175, 42));
        btnCancelar.setBackground(new Color(234, 220, 188));
        btnCancelar.setForeground(PaletaColores.MARRON_OSCURO);
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        btnCancelar.addActionListener(e -> {
            dispose();
            coordinador.mostrarGestionarIngredientes();
        });

        BotonRedondeado btnGuardar = new BotonRedondeado("Guardar", 18);
        btnGuardar.setPreferredSize(new Dimension(175, 42));
        btnGuardar.setMinimumSize(new Dimension(175, 42));
        btnGuardar.setMaximumSize(new Dimension(175, 42));
        btnGuardar.setBackground(PaletaColores.MARRON_OSCURO);
        btnGuardar.setForeground(PaletaColores.BLANCO);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnGuardar.addActionListener(e -> guardarIngrediente());

        panelBotones.add(btnCancelar);
        panelBotones.add(Box.createRigidArea(new Dimension(14, 0)));
        panelBotones.add(btnGuardar);

        return panelBotones;
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setPreferredSize(new Dimension(0, 40));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campo.setMinimumSize(new Dimension(200, 40));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campo.setForeground(PaletaColores.TEXTO_MARRON);
        campo.setBackground(PaletaColores.BLANCO_SUAVE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(221, 214, 205), 1),
                new EmptyBorder(0, 12, 0, 12)
        ));
        return campo;
    }

    private JComboBox<String> crearComboUnidadMedida() {
        JComboBox<String> combo = new JComboBox<>();
        combo.setPreferredSize(new Dimension(0, 40));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        combo.setMinimumSize(new Dimension(200, 40));
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        combo.setForeground(PaletaColores.MARRON_OSCURO);
        combo.setBackground(PaletaColores.BLANCO_SUAVE);
        combo.setBorder(BorderFactory.createLineBorder(new Color(221, 214, 205), 1));

        combo.addItem("Seleccione una unidad");
        for (UnidadMedida unidad : UnidadMedida.values()) {
            combo.addItem(unidad.toString());
        }

        return combo;
    }

    private JPanel crearEtiquetaConAsterisco(String texto) {
        JPanel panelEtiqueta = new JPanel();
        panelEtiqueta.setOpaque(false);
        panelEtiqueta.setLayout(new BoxLayout(panelEtiqueta, BoxLayout.X_AXIS));

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 24));
        etiqueta.setForeground(PaletaColores.TEXTO_MARRON);

        JLabel asterisco = new JLabel(" *");
        asterisco.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 24));
        asterisco.setForeground(new Color(255, 70, 95));

        panelEtiqueta.add(etiqueta);
        panelEtiqueta.add(asterisco);
        panelEtiqueta.add(Box.createHorizontalGlue());

        return panelEtiqueta;
    }

    private void guardarIngrediente() {
        String nombre = txtNombreIngrediente.getText().trim();
        int indexUnidad = cmbUnidadMedida.getSelectedIndex();
        String stockTxt = txtCantidadInventario.getText().trim();
        String umbralTxt = txtUmbral.getText().trim();

        if (!Validadores.validarIngrediente(this, nombre, indexUnidad, stockTxt, umbralTxt)) {
            return; 
        }

        try {
            IngredienteDTO ingredienteDTO = new IngredienteDTO();
            ingredienteDTO.setNombre(nombre);
            ingredienteDTO.setUnidadMedida(enumsDTO.UnidadMedidaDTO.values()[indexUnidad - 1]);

            ingredienteDTO.setStockActual(Double.valueOf(stockTxt));
            ingredienteDTO.setUmbral(Double.valueOf(umbralTxt));
            coordinador.registrarIngrediente(ingredienteDTO);

            JOptionPane.showMessageDialog(this, 
                    "Ingrediente registrado correctamente.", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();
            coordinador.mostrarGestionarIngredientes();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    e.getMessage(), 
                    "Error de Sistema", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
}