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
import com.toedter.calendar.JDateChooser;
import dtos.ClienteFrecuenteDTO;
import excepciones.NegocioException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Pantalla de reportes para el sistema Restaurante Le Pusse.
 * @author regina, mariana e isaac
 */
public class FrmReportes extends JFrame {

    private final Coordinador coordinador;
    private JPanel panelTarjeta;
    private JPanel panelFiltros;

    private BotonRedondeado btnReporteComandas;
    private BotonRedondeado btnReporteClientes;
    private BotonRedondeado btnConsultar;
    private BotonRedondeado btnDescargarPDF;

    private JDateChooser dcFechaInicio;
    private JDateChooser dcFechaFin;
    private JLabel lblClienteVisualizacion;
    private JTextField txtMinimoVisitas;
    private ClienteFrecuenteDTO clienteSeleccionado;

    private enum TipoReporte {
        COMANDAS, CLIENTES
    }
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

        btnDescargarPDF = new BotonRedondeado("Descargar PDF", 18);
        btnDescargarPDF.setBounds(235, 360, 180, 50);
        btnDescargarPDF.setBackground(PaletaColores.DORADO);
        btnDescargarPDF.setForeground(PaletaColores.MARRON_OSCURO);
        btnDescargarPDF.setFont(new Font("Segoe UI", Font.BOLD, 15));
        panelTarjeta.add(btnDescargarPDF);

        btnConsultar.addActionListener(e -> {
            try {
                consultarReporte();
            } catch (Exception ex) {
                Logger.getLogger(FrmReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnDescargarPDF.addActionListener(e -> descargarPDF());

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

        agregarEtiqueta(panelFiltros, "Mínimo de Visitas", 35, 110);
        txtMinimoVisitas = crearCampoTexto();
        txtMinimoVisitas.setBounds(35, 140, 350, 40);
        panelFiltros.add(txtMinimoVisitas);

        agregarEtiqueta(panelFiltros, "Seleccionar Cliente:", 35, 200);
        lblClienteVisualizacion = new JLabel(" Ninguno seleccionado");
        lblClienteVisualizacion.setBounds(35, 230, 250, 40);
        lblClienteVisualizacion.setOpaque(true);
        lblClienteVisualizacion.setBackground(Color.WHITE);
        lblClienteVisualizacion.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE));
        panelFiltros.add(lblClienteVisualizacion);

        BotonRedondeado btnBuscar = new BotonRedondeado("Buscar", 15);
        btnBuscar.setBounds(295, 230, 90, 40);
        btnBuscar.setBackground(PaletaColores.DORADO);
        btnBuscar.addActionListener(e -> coordinador.abrirSelectorClienteParaReporte());
        panelFiltros.add(btnBuscar);

        panelFiltros.revalidate();
        panelFiltros.repaint();
    }

    public void setClienteSeleccionado(ClienteFrecuenteDTO cliente) {
        this.clienteSeleccionado = cliente;
        if (cliente != null) {
            this.lblClienteVisualizacion.setText(" " + construirNombreCompleto(cliente));
            this.lblClienteVisualizacion.setForeground(PaletaColores.TEXTO_MARRON);
        }
    }

    private String construirNombreCompleto(ClienteFrecuenteDTO c) {
        return (c.getNombre() + " " + c.getApellidoPaterno()).trim();
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
        lbl.setBounds(x, y, 350, 25);
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

    private void consultarReporte() throws Exception {
        try {
            if (tipoActual == TipoReporte.CLIENTES) {
                String nombreFiltro = (clienteSeleccionado != null) ? clienteSeleccionado.getNombre() : "";
                String visitasTexto = (txtMinimoVisitas != null) ? txtMinimoVisitas.getText().trim() : "0";

                if (!Validadores.validarFiltrosCliente(this, nombreFiltro, visitasTexto)) {
                    return;
                }

                Integer minimoVisitas = visitasTexto.isEmpty() ? 0 : Integer.parseInt(visitasTexto);

                JasperPrint jasperPrint = coordinador.generarVistaReporteClientes(nombreFiltro, minimoVisitas);
                if (jasperPrint.getPages().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontraron clientes con esos filtros.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JasperViewer.viewReport(jasperPrint, false);
                }
                
            } else { 
                java.util.Date dateIni = dcFechaInicio.getDate();
                java.util.Date dateFin = dcFechaFin.getDate();

                if (dateIni == null || dateFin == null) {
                    JOptionPane.showMessageDialog(this, "Por favor, seleccione ambas fechas.", "Filtros incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                java.time.LocalDate fechaInicio = dateIni.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                java.time.LocalDate fechaFin = dateFin.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

                if (fechaInicio.isAfter(fechaFin)) {
                    JOptionPane.showMessageDialog(this, "La fecha de inicio no puede ser posterior a la fecha de fin.", "Error de fechas", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JasperPrint jasperPrint = coordinador.generarVistaReporteComanda(fechaInicio, fechaFin);

                if (jasperPrint.getPages().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontraron comandas en el rango seleccionado.", "Sin coincidencias", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JasperViewer.viewReport(jasperPrint, false);
                }
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error al consultar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void descargarPDF() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte PDF");

            if (tipoActual == TipoReporte.CLIENTES) {
                // --- Lógica para Clientes ---
                String nombreFiltro = (clienteSeleccionado != null) ? clienteSeleccionado.getNombre() : "";
                String visitasTexto = (txtMinimoVisitas != null) ? txtMinimoVisitas.getText().trim() : "0";
                Integer minimoVisitas = visitasTexto.isEmpty() ? 0 : Integer.parseInt(visitasTexto);

                fileChooser.setSelectedFile(new File("ReporteClientes.pdf"));
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    String ruta = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!ruta.toLowerCase().endsWith(".pdf")) {
                        ruta += ".pdf";
                    }
                    coordinador.generarPDFReporteClientes(ruta, nombreFiltro, minimoVisitas);
                    JOptionPane.showMessageDialog(this, "Reporte de clientes generado con éxito.");
                }
            } else {
                // --- Lógica para COMANDAS (ESTO ES LO QUE CAMBIA) ---
                java.util.Date dateIni = dcFechaInicio.getDate();
                java.util.Date dateFin = dcFechaFin.getDate();

                if (dateIni == null || dateFin == null) {
                    JOptionPane.showMessageDialog(this, "Por favor, seleccione ambas fechas para el PDF.", "Filtros incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                java.time.LocalDate fechaInicio = dateIni.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                java.time.LocalDate fechaFin = dateFin.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

                fileChooser.setSelectedFile(new File("ReporteComandas_" + fechaInicio + "_a_" + fechaFin + ".pdf"));
                
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    String ruta = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!ruta.toLowerCase().endsWith(".pdf")) {
                        ruta += ".pdf";
                    }
                    
                    // Asegúrate de que el Coordinador tenga este método
                    coordinador.generarPDFReporteComandas(ruta, fechaInicio, fechaFin);
                    
                    JOptionPane.showMessageDialog(this, "Reporte de comandas guardado exitosamente en:\n" + ruta);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}