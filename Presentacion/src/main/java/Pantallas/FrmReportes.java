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
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla de reportes
 * @author regina, mariana e isaac
 */
public class FrmReportes extends JFrame {

    private final Coordinador coordinador;
    private JPanel panelTarjeta;
    private JPanel panelFiltros;

    private BotonRedondeado btnReporteComandas;
    private BotonRedondeado btnReporteClientes;
    private BotonRedondeado btnConsultar;

    private JDateChooser dcFechaInicio;
    private JDateChooser dcFechaFin;
    private JTextField txtNombreCliente;
    private JTextField txtMinimoVisitas;

    private enum TipoReporte { COMANDAS, CLIENTES }
    private TipoReporte tipoActual = TipoReporte.COMANDAS;

    public FrmReportes(Coordinador coordinador) {
        this.coordinador = coordinador;

        setTitle("Restaurante Le Pusse - Reportes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setMinimumSize(new Dimension(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new MenuLateralPanel("Reportes", coordinador), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);

        mostrarReporteComandas();
    }

    private JPanel crearContenidoPrincipal() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(PaletaColores.BEIGE_FONDO);

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(PaletaColores.BLANCO);
        cabecera.setBorder(new EmptyBorder(26, 28, 20, 28));

        JLabel titulo = new JLabel("Reportes");
        titulo.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 34));
        titulo.setForeground(PaletaColores.TEXTO_MARRON);
        cabecera.add(titulo, BorderLayout.WEST);

        contenido.add(cabecera, BorderLayout.NORTH);

        JPanel zonaCentral = new JPanel(null); 
        zonaCentral.setBackground(PaletaColores.BEIGE_PANEL);

        btnReporteComandas = new BotonRedondeado("Reporte de Comandas", 20);
        btnReporteComandas.setBounds(130, 45, 230, 65);
        btnReporteComandas.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnReporteComandas.addActionListener(e -> mostrarReporteComandas());

        btnReporteClientes = new BotonRedondeado("Reporte de Clientes", 20);
        btnReporteClientes.setBounds(375, 45, 230, 65);
        btnReporteClientes.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnReporteClientes.addActionListener(e -> mostrarReporteClientes());

        panelTarjeta = new JPanel(null);
        panelTarjeta.setBackground(PaletaColores.BLANCO_SUAVE);
        panelTarjeta.setBorder(BorderFactory.createLineBorder(new Color(225, 217, 208), 1));
        panelTarjeta.setBounds(130, 125, 850, 450);

        JLabel lblSubtitulo = new JLabel("Filtros de Búsqueda");
        lblSubtitulo.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 24));
        lblSubtitulo.setForeground(PaletaColores.TEXTO_MARRON);
        lblSubtitulo.setBounds(35, 30, 300, 30);
        panelTarjeta.add(lblSubtitulo);

        panelFiltros = new JPanel(null);
        panelFiltros.setOpaque(false);
        panelFiltros.setBounds(0, 0, 850, 450);
        panelTarjeta.add(panelFiltros);

        btnConsultar = new BotonRedondeado("Consultar", 18);
        btnConsultar.setBounds(35, 360, 180, 50);
        btnConsultar.setBackground(PaletaColores.MARRON_OSCURO);
        btnConsultar.setForeground(Color.WHITE);
        btnConsultar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        panelTarjeta.add(btnConsultar);

        zonaCentral.add(btnReporteComandas);
        zonaCentral.add(btnReporteClientes);
        zonaCentral.add(panelTarjeta);

        contenido.add(zonaCentral, BorderLayout.CENTER);

        return contenido;
    }

    private void mostrarReporteComandas() {
        tipoActual = TipoReporte.COMANDAS;
        actualizarEstiloPestanias();
        panelFiltros.removeAll();

        agregarEtiqueta(panelFiltros, "Fecha Inicio", 35, 110);
        dcFechaInicio = new JDateChooser();
        dcFechaInicio.setBounds(35, 140, 350, 40);
        panelFiltros.add(dcFechaInicio);

        agregarEtiqueta(panelFiltros, "Fecha Fin", 420, 110);
        dcFechaFin = new JDateChooser();
        dcFechaFin.setBounds(420, 140, 350, 40);
        panelFiltros.add(dcFechaFin);

        panelFiltros.revalidate();
        panelFiltros.repaint();
    }

    private void mostrarReporteClientes() {
        tipoActual = TipoReporte.CLIENTES;
        actualizarEstiloPestanias();
        panelFiltros.removeAll();

        agregarEtiqueta(panelFiltros, "Nombre del Cliente", 35, 110);
        txtNombreCliente = crearCampoTexto();
        txtNombreCliente.setBounds(35, 140, 350, 40);
        panelFiltros.add(txtNombreCliente);

        agregarEtiqueta(panelFiltros, "Número Mínimo de Visitas", 420, 110);
        txtMinimoVisitas = crearCampoTexto();
        txtMinimoVisitas.setBounds(420, 140, 350, 40);
        panelFiltros.add(txtMinimoVisitas);

        panelFiltros.revalidate();
        panelFiltros.repaint();
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        return campo;
    }

    private void agregarEtiqueta(JPanel p, String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lbl.setForeground(PaletaColores.TEXTO_MARRON);
        lbl.setBounds(x, y, 300, 25);
        p.add(lbl);
    }

    private void actualizarEstiloPestanias() {
        if (tipoActual == TipoReporte.COMANDAS) {
            btnReporteComandas.setBackground(PaletaColores.MARRON_OSCURO);
            btnReporteComandas.setForeground(Color.WHITE);
            btnReporteClientes.setBackground(PaletaColores.BLANCO_SUAVE);
            btnReporteClientes.setForeground(PaletaColores.DORADO);
        } else {
            btnReporteClientes.setBackground(PaletaColores.MARRON_OSCURO);
            btnReporteClientes.setForeground(Color.WHITE);
            btnReporteComandas.setBackground(PaletaColores.BLANCO_SUAVE);
            btnReporteComandas.setForeground(PaletaColores.DORADO);
        }
    }
}