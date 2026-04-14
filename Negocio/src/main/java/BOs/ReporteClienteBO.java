package BOs;

import dtos.ClienteFrecuenteDTO;
import excepciones.NegocioException;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Clase encargada de la lógica de negocio para la generación de reportes
 * de clientes frecuentes.
 * 
 * Permite obtener, filtrar y generar reportes en formato Jasper y PDF
 * a partir de la información de clientes frecuentes.
 * 
 * Sigue el patrón Singleton para asegurar una única instancia.
 * 
 * @author Mariana, Regina, Isaac
 */
public class ReporteClienteBO {

    private static ReporteClienteBO instanciaReporteClienteBO;
    private final ClienteFrecuenteBO clienteFrecuenteBO;

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private ReporteClienteBO() {
        this.clienteFrecuenteBO = ClienteFrecuenteBO.getInstance();
    }

    /**
     * Obtiene la instancia única de ReporteClienteBO.
     * 
     * @return instancia única de ReporteClienteBO
     */
    public static ReporteClienteBO getInstance() {
        if (instanciaReporteClienteBO == null) {
            instanciaReporteClienteBO = new ReporteClienteBO();
        }
        return instanciaReporteClienteBO;
    }

    /**
     * Obtiene todos los clientes frecuentes registrados.
     * 
     * @return lista de clientes frecuentes
     * @throws NegocioException si ocurre un error en la obtención
     */
    public List<ClienteFrecuenteDTO> obtenerDatosReporte() throws NegocioException {
        return clienteFrecuenteBO.obtenerTodos();
    }

    /**
     * Filtra los clientes frecuentes según un nombre y un mínimo de visitas.
     * 
     * @param nombreFiltro texto para filtrar por nombre (puede ser parcial)
     * @param minimoVisitas número mínimo de visitas requerido
     * @return lista de clientes que cumplen con los filtros
     * @throws NegocioException si ocurre un error en la consulta
     */
    public List<ClienteFrecuenteDTO> filtrarClientes(String nombreFiltro, Integer minimoVisitas) throws NegocioException {
        List<ClienteFrecuenteDTO> todos = clienteFrecuenteBO.obtenerTodos();
        List<ClienteFrecuenteDTO> filtrados = new ArrayList<>();

        String nombreLimpio = nombreFiltro != null ? nombreFiltro.trim().toLowerCase() : "";
        int minimo = minimoVisitas != null ? minimoVisitas : 0;

        for (ClienteFrecuenteDTO c : todos) {
            String nombreCompleto = construirNombreCompleto(c).toLowerCase();
            int visitas = c.getNumeroVisitas() != null ? c.getNumeroVisitas() : 0;

            boolean cumpleNombre = nombreLimpio.isEmpty() || nombreCompleto.contains(nombreLimpio);
            boolean cumpleVisitas = visitas >= minimo;

            if (cumpleNombre && cumpleVisitas) {
                filtrados.add(c);
            }
        }

        return filtrados;
    }

    /**
     * Genera un reporte JasperPrint de clientes frecuentes aplicando filtros.
     * 
     * @param nombreFiltro filtro por nombre del cliente
     * @param minimoVisitas filtro por número mínimo de visitas
     * @return objeto JasperPrint listo para visualizar
     * @throws Exception si ocurre un error en la generación del reporte
     */
    public JasperPrint generarJasperClientesFrecuentes(String nombreFiltro, Integer minimoVisitas) throws Exception {
        List<ClienteFrecuenteDTO> lista = filtrarClientes(nombreFiltro, minimoVisitas);

        InputStream reporteStream = getClass().getClassLoader()
                .getResourceAsStream("reportes/ReporteClientesFrecuentes.jrxml");

        if (reporteStream == null) {
            throw new NegocioException("No se encontró la plantilla ReporteClientesFrecuentes.jrxml");
        }

        JasperReport report = JasperCompileManager.compileReport(reporteStream);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombreReporte", "Reporte de Clientes Frecuentes");
        parametros.put("fechaGeneracion", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        parametros.put("filtroNombre", (nombreFiltro != null && !nombreFiltro.isBlank()) ? nombreFiltro : "Todos");
        parametros.put("filtroMinimoVisitas", minimoVisitas != null ? minimoVisitas : 0);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

        return JasperFillManager.fillReport(report, parametros, dataSource);
    }

    /**
     * Genera y exporta el reporte de clientes frecuentes en formato PDF.
     * 
     * @param rutaSalidaPDF ruta donde se guardará el archivo PDF
     * @param nombreFiltro filtro por nombre del cliente
     * @param minimoVisitas filtro por número mínimo de visitas
     * @throws Exception si ocurre un error durante la generación o exportación
     */
    public void generarReportePDF(String rutaSalidaPDF, String nombreFiltro, Integer minimoVisitas) throws Exception {
        if (rutaSalidaPDF == null || rutaSalidaPDF.trim().isEmpty()) {
            throw new NegocioException("La ruta de salida del PDF es obligatoria.");
        }

        JasperPrint jasperPrint = generarJasperClientesFrecuentes(nombreFiltro, minimoVisitas);
        asegurarCarpetaDestino(rutaSalidaPDF);
        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaSalidaPDF);
    }

    /**
     * Construye el nombre completo de un cliente a partir de sus atributos.
     * 
     * @param cliente cliente frecuente
     * @return nombre completo formateado
     */
    private String construirNombreCompleto(ClienteFrecuenteDTO cliente) {
        StringBuilder sb = new StringBuilder();

        if (cliente.getNombre() != null && !cliente.getNombre().isBlank()) {
            sb.append(cliente.getNombre().trim());
        }
        if (cliente.getApellidoPaterno() != null && !cliente.getApellidoPaterno().isBlank()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(cliente.getApellidoPaterno().trim());
        }
        if (cliente.getApellidoMaterno() != null && !cliente.getApellidoMaterno().isBlank()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(cliente.getApellidoMaterno().trim());
        }

        return sb.length() > 0 ? sb.toString() : "-";
    }

    /**
     * Verifica que la carpeta destino exista, y si no, la crea.
     * 
     * @param rutaSalidaPDF ruta del archivo PDF
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