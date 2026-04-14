/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import dtos.ClienteFrecuenteDTO;
import excepciones.NegocioException;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Lógica de negocio para generar el reporte de clientes frecuentes en PDF
 * usando JasperReports.
 * 
 * Esta clase obtiene la información desde ClienteFrecuenteBO y la exporta
 * a un archivo PDF a partir de una plantilla .jrxml.
 * 
 * @author regina, mariana e isaac
 */
public class ReporteClienteBO {

    private static ReporteClienteBO instanciaReporteClienteBO;
    private final ClienteFrecuenteBO clienteFrecuenteBO;

    /**
     * Constructor privado para Singleton.
     */
    private ReporteClienteBO() {
        this.clienteFrecuenteBO = ClienteFrecuenteBO.getInstance();
    }

    /**
     * Obtiene la instancia única de ReporteClienteBO.
     *
     * @return instancia única
     */
    public static ReporteClienteBO getInstance() {
        if (instanciaReporteClienteBO == null) {
            instanciaReporteClienteBO = new ReporteClienteBO();
        }
        return instanciaReporteClienteBO;
    }

    /**
     * Obtiene los datos actuales del reporte de clientes frecuentes.
     *
     * @return lista de clientes frecuentes
     * @throws NegocioException si ocurre un error al obtener los datos
     */
    public List<ClienteFrecuenteDTO> obtenerDatosReporte() throws NegocioException {
        return clienteFrecuenteBO.obtenerTodos();
    }

    /**
     * Genera el reporte PDF de clientes frecuentes.
     *
     * @param rutaSalidaPDF ruta completa donde se guardará el archivo PDF
     * @throws NegocioException si ocurre un error al generar el reporte
     */
    public void generarReportePDF(String rutaSalidaPDF) throws NegocioException {
        List<ClienteFrecuenteDTO> clientes = obtenerDatosReporte();
        generarReportePDF(rutaSalidaPDF, clientes);
    }

    /**
     * Genera el reporte PDF de clientes frecuentes usando una lista dada.
     *
     * @param rutaSalidaPDF ruta completa donde se guardará el archivo PDF
     * @param clientes lista de clientes frecuentes
     * @throws NegocioException si ocurre un error al generar el reporte
     */
    public void generarReportePDF(String rutaSalidaPDF, List<ClienteFrecuenteDTO> clientes) throws NegocioException {
        if (rutaSalidaPDF == null || rutaSalidaPDF.trim().isEmpty()) {
            throw new NegocioException("La ruta de salida del PDF es obligatoria.");
        }

        if (clientes == null) {
            throw new NegocioException("La lista de clientes para el reporte no puede ser nula.");
        }

        try {
            InputStream reporteStream = getClass().getClassLoader()
                    .getResourceAsStream("reportes/reporte_clientes_frecuentes.jrxml");

            if (reporteStream == null) {
                throw new NegocioException(
                        "No se encontró la plantilla del reporte en resources/reportes/reporte_clientes_frecuentes.jrxml"
                );
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reporteStream);

            Map<String, Object> parametros = construirParametros(clientes);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(clientes);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

            asegurarCarpetaDestino(rutaSalidaPDF);

            JasperExportManager.exportReportToPdfFile(jasperPrint, rutaSalidaPDF);

        } catch (JRException e) {
            throw new NegocioException("Error al generar el reporte PDF de clientes frecuentes.", e);
        }
    }

    /**
     * Construye los parámetros que usará el reporte Jasper.
     *
     * @param clientes lista de clientes frecuentes
     * @return mapa de parámetros
     */
    private Map<String, Object> construirParametros(List<ClienteFrecuenteDTO> clientes) {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("tituloReporte", "Reporte de Clientes Frecuentes");
        parametros.put("fechaGeneracion", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        parametros.put("totalClientes", clientes.size());
        parametros.put("totalVisitas", calcularTotalVisitas(clientes));
        parametros.put("totalPuntos", calcularTotalPuntos(clientes));
        parametros.put("totalGastado", calcularTotalGastado(clientes));

        return parametros;
    }

    /**
     * Calcula el total de visitas de todos los clientes.
     *
     * @param clientes lista de clientes
     * @return total de visitas
     */
    private int calcularTotalVisitas(List<ClienteFrecuenteDTO> clientes) {
        int total = 0;

        for (ClienteFrecuenteDTO cliente : clientes) {
            if (cliente.getNumeroVisitas() != null) {
                total += cliente.getNumeroVisitas();
            }
        }

        return total;
    }

    /**
     * Calcula el total de puntos de fidelidad.
     *
     * @param clientes lista de clientes
     * @return total de puntos
     */
    private int calcularTotalPuntos(List<ClienteFrecuenteDTO> clientes) {
        int total = 0;

        for (ClienteFrecuenteDTO cliente : clientes) {
            if (cliente.getPuntosFidelidad() != null) {
                total += cliente.getPuntosFidelidad();
            }
        }

        return total;
    }

    /**
     * Calcula el total gastado acumulado.
     *
     * @param clientes lista de clientes
     * @return total gastado
     */
    private double calcularTotalGastado(List<ClienteFrecuenteDTO> clientes) {
        double total = 0.0;

        for (ClienteFrecuenteDTO cliente : clientes) {
            if (cliente.getTotalGastado() != null) {
                total += cliente.getTotalGastado();
            }
        }

        return total;
    }

    /**
     * Crea la carpeta destino si no existe.
     *
     * @param rutaSalidaPDF ruta completa del PDF
     * @throws NegocioException si no se puede crear la carpeta
     */
    private void asegurarCarpetaDestino(String rutaSalidaPDF) throws NegocioException {
        File archivo = new File(rutaSalidaPDF);
        File carpetaPadre = archivo.getParentFile();

        if (carpetaPadre != null && !carpetaPadre.exists()) {
            boolean creada = carpetaPadre.mkdirs();

            if (!creada) {
                throw new NegocioException("No se pudo crear la carpeta destino del reporte.");
            }
        }
    }
}