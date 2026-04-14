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
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla de Edición de Ingredientes 
 * @author regina, mariana e isaac
 */
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
        cabecera.setPreferredSize(new Dimension(0, 80));
        cabecera.setBorder(new EmptyBorder(0, 30, 0, 30));
        
        JLabel titulo = new JLabel("Ingredientes");
        titulo.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 32));
        titulo.setForeground(PaletaColores.TEXTO_MARRON);
        cabecera.add(titulo, BorderLayout.WEST);

        contenido.add(cabecera, BorderLayout.NORTH);

        JPanel fondoCentral = new JPanel(new GridBagLayout());
        fondoCentral.setBackground(new Color(239, 227, 204));
        
        fondoCentral.add(crearPanelFormulario());
        contenido.add(fondoCentral, BorderLayout.CENTER);

        return contenido;
    }

    private JPanel crearPanelFormulario() {
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Color.WHITE);
        formulario.setPreferredSize(new Dimension(750, 550));
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 220, 210), 1),
                new EmptyBorder(40, 50, 40, 50)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        
        JLabel lblSubtitulo = new JLabel("Editar Ingrediente");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblSubtitulo.setForeground(PaletaColores.TEXTO_MARRON);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        formulario.add(lblSubtitulo, gbc);

        txtNombre = new JTextField();
        cbUnidadMedida = new JComboBox<>(UnidadMedidaDTO.values());
        txtStock = new JTextField();

        estilizarCampo(txtNombre);
        estilizarCampo(cbUnidadMedida);
        estilizarCampo(txtStock);

        int fila = 1;
        fila = agregarSeccion(formulario, gbc, "Nombre del Ingrediente", txtNombre, fila);
        fila = agregarSeccion(formulario, gbc, "Unidad de Medida", cbUnidadMedida, fila);
        fila = agregarSeccion(formulario, gbc, "Cantidad en Inventario", txtStock, fila);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlBotones.setOpaque(false);

        BotonRedondeado btnEliminar = new BotonRedondeado("Eliminar", 16);
        btnEliminar.setBackground(new Color(185, 70, 70));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setPreferredSize(new Dimension(130, 40));
        btnEliminar.addActionListener(e -> eliminarIngrediente());

        BotonRedondeado btnCancelar = new BotonRedondeado("Cancelar", 16);
        btnCancelar.setBackground(new Color(230, 215, 185)); 
        btnCancelar.setPreferredSize(new Dimension(130, 40));
        btnCancelar.addActionListener(e -> coordinador.regresarAGestionIngredientes());

        BotonRedondeado btnGuardar = new BotonRedondeado("Guardar", 16);
        btnGuardar.setBackground(PaletaColores.MARRON_OSCURO);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(130, 40));
        btnGuardar.addActionListener(e -> guardarCambios());

        pnlBotones.add(btnEliminar);
        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnGuardar);

        gbc.gridy = fila;
        gbc.insets = new Insets(40, 0, 0, 0);
        formulario.add(pnlBotones, gbc);

        return formulario;
    }

    private int agregarSeccion(JPanel p, GridBagConstraints g, String texto, JComponent comp, int fila) {
        JPanel pnlLabel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlLabel.setOpaque(false);
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        l.setForeground(new Color(100, 90, 80));
        JLabel a = new JLabel(" *");
        a.setForeground(new Color(200, 50, 50));
        pnlLabel.add(l);
        pnlLabel.add(a);

        g.gridy = fila++;
        g.insets = new Insets(10, 0, 5, 0);
        p.add(pnlLabel, g);

        g.gridy = fila++;
        g.insets = new Insets(0, 0, 15, 0);
        p.add(comp, g);
        
        return fila;
    }

    private void estilizarCampo(JComponent c) {
        c.setPreferredSize(new Dimension(0, 38));
        c.setBackground(Color.WHITE);
        c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        if (c instanceof JTextField) {
            ((JTextField) c).setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)), 
                new EmptyBorder(5, 12, 5, 12)));
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