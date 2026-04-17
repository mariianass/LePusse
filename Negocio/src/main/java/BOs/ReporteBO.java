package BOs;

import dtos.ClienteFrecuenteDTO;
import dtos.ComandaDTO;
import excepciones.NegocioException;
import interfaces.IReporteBO;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Clase encargada de la lógica de negocio para la generación de reportes de
 * clientes frecuentes.
 *
 * Permite obtener, filtrar y generar reportes en formato Jasper y PDF a partir
 * de la información de clientes frecuentes.
 *
 * Sigue el patrón Singleton para asegurar una única instancia.
 *
 * @author Mariana, Regina, Isaac
 */
public class ReporteBO implements IReporteBO{

    /**
     * Instancia única de la clase ReporteBO.
     */
    private static ReporteBO instanciaReporteClienteBO;

    /**
     * Objeto de negocio para la gestión de clientes frecuentes.
     */
    private final ClienteFrecuenteBO clienteFrecuenteBO;

    /**
     * Objeto de negocio para la gestión de comandas.
     */
    private final ComandaBO comanda;

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private ReporteBO() {
        this.comanda = ComandaBO.getInstance();
        this.clienteFrecuenteBO = ClienteFrecuenteBO.getInstance();
    }

    /**
     * Obtiene la instancia única de ReporteBO.
     *
     * @return instancia única de ReporteBO.
     */
    public static ReporteBO getInstance() {
        if (instanciaReporteClienteBO == null) {
            instanciaReporteClienteBO = new ReporteBO();
        }
        return instanciaReporteClienteBO;
    }

    /**
     * Obtiene todos los clientes frecuentes registrados.
     *
     * @return lista de clientes frecuentes.
     * @throws NegocioException si ocurre un error en la obtención.
     */
    public List<ClienteFrecuenteDTO> obtenerDatosReporte() throws NegocioException {
        return clienteFrecuenteBO.obtenerTodos();
    }

    /**
     * Genera un reporte de clientes frecuentes en formato JasperPrint.
     * 
     * Permite aplicar filtros por nombre y número mínimo de visitas.
     * 
     * La información se transforma en un DataSource compatible con JasperReports.
     *
     * @param nombreFiltro filtro por nombre del cliente.
     * @param minimoVisitas filtro por número mínimo de visitas.
     * @return objeto JasperPrint listo para visualización.
     * @throws Exception si ocurre un error en la generación del reporte.
     */
    @Override
    public JasperPrint generarJasperClientesFrecuentes(String nombreFiltro, Integer minimoVisitas) throws Exception {
        try {
            List<ClienteFrecuenteDTO> listaOriginal;

            // 1. Lógica de filtrado original
            String nombre = (nombreFiltro == null) ? "" : nombreFiltro.trim();
            int visitas = (minimoVisitas == null) ? 0 : minimoVisitas;

            if (!nombre.isEmpty()) {
                listaOriginal = clienteFrecuenteBO.buscarPorFiltros(nombre);
            } else if (visitas > 0) {
                listaOriginal = clienteFrecuenteBO.obtenerTodos();
                listaOriginal.removeIf(c -> (c.getNumeroVisitas() == null ? 0 : c.getNumeroVisitas()) < visitas);
            } else {
                listaOriginal = clienteFrecuenteBO.obtenerTodos();
            }

            // Esto permite que el reporte reciba los datos ya procesados como Strings
            List<Map<String, Object>> listaParaReporte = new java.util.ArrayList<>();
            java.time.format.DateTimeFormatter formatoFecha = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (ClienteFrecuenteDTO cliente : listaOriginal) {
                Map<String, Object> fila = new java.util.HashMap<>();

                fila.put("nombre", cliente.getNombre());
                fila.put("apellidoPaterno", cliente.getApellidoPaterno());
                fila.put("numeroVisitas", cliente.getNumeroVisitas());
                fila.put("puntosFidelidad", cliente.getPuntosFidelidad()); // Asegúrate de que este también esté
                fila.put("totalGastado", cliente.getTotalGastado());      // Y este

                // --- ESTA ES LA LÍNEA QUE FALTA ---
                fila.put("telefono", cliente.getTelefono()); 
                // ----------------------------------

                String fechaTexto = (cliente.getFechaUltimaComanda() != null) 
                                    ? cliente.getFechaUltimaComanda().format(formatoFecha) 
                                    : "-";

                fila.put("fechaUltimaComanda", fechaTexto);

                listaParaReporte.add(fila);
            }

            // 3. Configuración de parámetros
            Map<String, Object> parametros = new java.util.HashMap<>();
            parametros.put("nombreReporte", "Reporte de Clientes Frecuentes");
            parametros.put("filtroNombre", !nombre.isEmpty() ? nombre : (visitas > 0 ? "Min. Visitas: " + visitas : "Todos"));

            // 4. Carga y compilación del reporte
            InputStream ruta = getClass().getResourceAsStream("/reportes/clientes.jrxml");
            if (ruta == null) {
                throw new excepciones.NegocioException("No se encontró el archivo (.jrxml). Revisa la ruta.");
            }

            if (listaParaReporte.isEmpty()) {
                throw new excepciones.NegocioException("No se encontraron coincidencias con los filtros aplicados.");
            }

            net.sf.jasperreports.engine.JasperReport report = net.sf.jasperreports.engine.JasperCompileManager.compileReport(ruta);

            // 5. Usamos la lista de mapas como fuente de datos
            net.sf.jasperreports.engine.data.JRBeanCollectionDataSource dataSource = 
                    new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(listaParaReporte);

            return net.sf.jasperreports.engine.JasperFillManager.fillReport(report, parametros, dataSource);

        } catch (net.sf.jasperreports.engine.JRException ex) {
            throw new excepciones.NegocioException("Error de Jasper: " + ex.getMessage());
        } catch (Exception ex) {
            throw new excepciones.NegocioException("Error al procesar el reporte: " + ex.getMessage());
        }
    }
    
    /**
     * Genera un reporte de comandas en formato JasperPrint dentro de un rango de fechas.
     * 
     * Prepara los parámetros necesarios y utiliza JasperReports para generar
     * el reporte con los datos obtenidos.
     *
     * @param inicio fecha inicial del rango.
     * @param fin fecha final del rango.
     * @return objeto JasperPrint con el reporte generado.
     * @throws NegocioException si ocurre un error en el proceso.
     */
    @Override
    public JasperPrint generarJasperReporteComandas(LocalDate inicio, LocalDate fin) throws NegocioException {
        try {
            List<ComandaDTO> listaCompleta = comanda.buscarPorFiltros(null); 

            List<Map<String, Object>> listaFiltrada = new java.util.ArrayList<>();
            DateTimeFormatter formatoDeseado = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (ComandaDTO c : listaCompleta) {
                LocalDate fechaComanda = c.getFechaHoraCreacion().toLocalDate();

                if ((fechaComanda.isAfter(inicio) || fechaComanda.isEqual(inicio)) && 
                    (fechaComanda.isBefore(fin) || fechaComanda.isEqual(fin))) {

                    Map<String, Object> fila = new HashMap<>();

                    // ESTOS NOMBRES DEBEN SER IDÉNTICOS AL XML
                    fila.put("folio", c.getFolio());
                    fila.put("nombreCliente", c.getNombreCliente());
                    fila.put("numeroMesa", c.getNumeroMesa());
                    fila.put("totalVenta", c.getTotalVenta()); // Cambiado de 'total' a 'totalVenta'

                    // Para la fecha, mandamos el String ya formateado
                    fila.put("fechaHoraCreacion", c.getFechaHoraCreacion().format(formatoDeseado)); 

                    listaFiltrada.add(fila);
                }
            }

            if (listaFiltrada.isEmpty()) {
                throw new NegocioException("No hay comandas en el rango seleccionado.");
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("fechaInicio", inicio.format(formatoDeseado));
            parametros.put("fechaFin", fin.format(formatoDeseado));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaFiltrada);

            InputStream reporteStream = getClass().getResourceAsStream("/reportes/comandas.jrxml");
            JasperReport report = JasperCompileManager.compileReport(reporteStream);

            return JasperFillManager.fillReport(report, parametros, dataSource);

        } catch (Exception ex) {
            throw new NegocioException("Error: " + ex.getMessage());
        }
    }
    
    /**
     * Genera y exporta el reporte de clientes frecuentes en formato PDF.
     * 
     * Primero genera el reporte en formato JasperPrint y posteriormente
     * lo exporta como archivo PDF en la ruta especificada.
     *
     * @param rutaSalidaPDF ruta donde se guardará el archivo PDF.
     * @param nombreFiltro filtro por nombre del cliente.
     * @param minimoVisitas filtro por número mínimo de visitas.
     * @throws Exception si ocurre un error durante la generación o exportación.
     */
    @Override
    public void generarReportePDF(String rutaSalidaPDF, String nombreFiltro, Integer minimoVisitas) throws Exception {
        if (rutaSalidaPDF == null || rutaSalidaPDF.trim().isEmpty()) {
            throw new NegocioException("La ruta de salida del PDF es obligatoria.");
        }

        JasperPrint jasperPrint = generarJasperClientesFrecuentes(nombreFiltro, minimoVisitas);
        asegurarCarpetaDestino(rutaSalidaPDF);
        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaSalidaPDF);
    }
    
    @Override
    public void generarReporteComandasPDF(String rutaSalidaPDF, LocalDate inicio, LocalDate fin) throws Exception {
        if (rutaSalidaPDF == null || rutaSalidaPDF.trim().isEmpty()) {
            throw new NegocioException("La ruta de salida del PDF es obligatoria.");
        }

        // Usa el método de comandas que ya tiene el filtro de fechas y Mapas
        JasperPrint jasperPrint = generarJasperReporteComandas(inicio, fin);
        asegurarCarpetaDestino(rutaSalidaPDF);
        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaSalidaPDF);
    }

    /**
     * Verifica que la carpeta destino exista, y si no, la crea.
     *
     * @param rutaSalidaPDF ruta del archivo PDF.
     * @throws NegocioException si no se puede crear la carpeta.
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