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
import enumsDTO.UnidadMedidaDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class FrmEditarIngrediente extends JFrame {

    private final Coordinador coordinador;
    private final IngredienteDTO ingredienteDTO;
    private JTextField txtNombre, txtStock;
    private JComboBox<UnidadMedidaDTO> cbUnidadMedida;

    public FrmEditarIngrediente(Coordinador coordinador, IngredienteDTO ingredienteDTO) {
        this.coordinador = coordinador;
        this.ingredienteDTO = ingredienteDTO;

        setTitle("Restaurante Le Pusse - Editar Ingrediente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        add(new MenuLateralPanel("Ingredientes", coordinador), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);

        cargarDatosIngrediente();
    }

    private JPanel crearContenidoPrincipal() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(PaletaColores.BEIGE_FONDO);

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(PaletaColores.BLANCO);
        cabecera.setBorder(new EmptyBorder(22, 28, 22, 28));
        JLabel titulo = new JLabel("Ingredientes");
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
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(PaletaColores.BLANCO_SUAVE);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 205, 195), 1),
                new EmptyBorder(20, 24, 18, 24)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblSubtitulo = new JLabel("Editar Ingrediente");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSubtitulo.setForeground(PaletaColores.TEXTO_MARRON);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formulario.add(lblSubtitulo, gbc);

        txtNombre = new JTextField();
        cbUnidadMedida = new JComboBox<>(UnidadMedidaDTO.values());
        txtStock = new JTextField();
        estilizarCampo(txtNombre);
        estilizarCampo(cbUnidadMedida);
        estilizarCampo(txtStock);

        gbc.gridwidth = 1;
        gbc.weightx = 0.5; 
        agregarFila(formulario, gbc, 1, "Nombre del Ingrediente", txtNombre);
        agregarFila(formulario, gbc, 3, "Unidad de Medida", cbUnidadMedida);
        agregarFila(formulario, gbc, 5, "Cantidad en Inventario", txtStock);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.5;
        formulario.add(new javax.swing.Box.Filler(new Dimension(0,0), new Dimension(0,0), new Dimension(32767,0)), gbc);


        JPanel pnlBotones = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 0));
        pnlBotones.setOpaque(false);

        BotonRedondeado btnEliminar = new BotonRedondeado("Eliminar", 18);
        btnEliminar.setBackground(new Color(185, 70, 70));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setPreferredSize(new Dimension(120, 40));
        btnEliminar.addActionListener(e -> eliminarIngrediente());

        BotonRedondeado btnCancelar = new BotonRedondeado("Cancelar", 18);
        btnCancelar.setBackground(new Color(232, 216, 182));
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.addActionListener(e -> coordinador.regresarAGestionIngredientes());

        BotonRedondeado btnGuardar = new BotonRedondeado("Guardar", 18);
        btnGuardar.setBackground(PaletaColores.MARRON_OSCURO);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(120, 40));
        btnGuardar.addActionListener(e -> guardarCambios());

        pnlBotones.add(btnEliminar);
        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnGuardar);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(30, 0, 10, 12);
        formulario.add(pnlBotones, gbc);

        return formulario;
    }

    private void agregarFila(JPanel p, GridBagConstraints g, int fila, String texto, javax.swing.JComponent comp) {
        g.gridx = 0; g.gridy = fila;
        g.insets = new Insets(10, 12, 2, 12);
        p.add(crearEtiqueta(texto), g);
        g.gridy = fila + 1;
        g.insets = new Insets(0, 12, 10, 12);
        p.add(comp, g);
    }

    private JPanel crearEtiqueta(String t) {
        JPanel p = new JPanel(new BorderLayout()); p.setOpaque(false);
        JLabel l = new JLabel(t); l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        l.setForeground(PaletaColores.TEXTO_MARRON);
        JLabel a = new JLabel(" *"); a.setForeground(new Color(200, 50, 50));
        p.add(l, BorderLayout.WEST); p.add(a, BorderLayout.CENTER);
        return p;
    }

    private void estilizarCampo(javax.swing.JComponent c) {
        c.setPreferredSize(new Dimension(280, 35));
        c.setBackground(Color.WHITE);
        if (c instanceof JTextField) {
            ((JTextField) c).setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), new EmptyBorder(5, 10, 5, 10)));
        }
    }

    private void cargarDatosIngrediente() {
        if (ingredienteDTO != null) {
            txtNombre.setText(ingredienteDTO.getNombre());
            cbUnidadMedida.setSelectedItem(ingredienteDTO.getUnidadMedida());
            txtStock.setText(String.valueOf(ingredienteDTO.getStockActual()));
        }
    }

    private void guardarCambios() {
        if (Validadores.validarIngrediente(this, txtNombre.getText(), cbUnidadMedida.getSelectedIndex() + 1, txtStock.getText(), "0")) {
            if (JOptionPane.showConfirmDialog(this, "¿Guardar cambios?", "Confirmar", JOptionPane.YES_NO_OPTION) == 0) {
                try {
                    ingredienteDTO.setNombre(txtNombre.getText());
                    ingredienteDTO.setUnidadMedida((UnidadMedidaDTO) cbUnidadMedida.getSelectedItem());
                    ingredienteDTO.setStockActual(Double.parseDouble(txtStock.getText()));
                    
                    coordinador.actualizarIngrediente(ingredienteDTO); 
                    
                    JOptionPane.showMessageDialog(this, "Guardado correctamente");
                    coordinador.regresarAGestionIngredientes();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
                }
            }
        }
    }

    private void eliminarIngrediente() {
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar este ingrediente?", "Aviso", JOptionPane.YES_NO_OPTION) == 0) {
            try {
                coordinador.eliminarIngrediente(ingredienteDTO.getIdIngrediente());
                JOptionPane.showMessageDialog(this, "Eliminado correctamente");
                coordinador.regresarAGestionIngredientes();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }
}