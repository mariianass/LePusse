package BOs;

import dtos.ClienteFrecuenteDTO;
import dtos.ComandaDTO;
import entidades.Comanda;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
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

    private static ReporteBO instanciaReporteClienteBO;
    private final ClienteFrecuenteBO clienteFrecuenteBO;
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
     * @return instancia única de ReporteBO
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
     * @return lista de clientes frecuentes
     * @throws NegocioException si ocurre un error en la obtención
     */
    public List<ClienteFrecuenteDTO> obtenerDatosReporte() throws NegocioException {
        return clienteFrecuenteBO.obtenerTodos();
    }

    /**
     * Genera un reporte JasperPrint de clientes frecuentes aplicando filtros.
     *
     * @param nombreFiltro filtro por nombre del cliente
     * @param minimoVisitas filtro por número mínimo de visitas
     * @return objeto JasperPrint listo para visualizar
     * @throws Exception si ocurre un error en la generación del reporte
     */
    @Override
    public JasperPrint generarJasperClientesFrecuentes(String nombreFiltro, Integer minimoVisitas) throws Exception {
        try {
            List<ClienteFrecuenteDTO> lista;

            String nombre = (nombreFiltro == null) ? "" : nombreFiltro.trim();
            int visitas = (minimoVisitas == null) ? 0 : minimoVisitas;

            if (!nombre.isEmpty()) {
                //Metodo para generar reporte por numVisitas
                lista = clienteFrecuenteBO.buscarPorFiltros(nombre);
            } else if (visitas > 0) {
                //Metodo para generar reporte por numVisitas
                //por mientras
                lista = clienteFrecuenteBO.obtenerTodos();
                lista.removeIf(c -> (c.getNumeroVisitas() == null ? 0 : c.getNumeroVisitas()) < minimoVisitas);
            } else {
                lista = clienteFrecuenteBO.obtenerTodos();
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("nombreReporte", "Reporte de Clientes Frecuentes");
            parametros.put("filtroNombre", !nombre.isEmpty() ? nombre : (visitas > 0 ? "Min. Visitas: " + visitas : "Todos"));


            InputStream ruta = getClass().getResourceAsStream("/reportes/clientes.jrxml");
            
            if (ruta == null) {
                throw new NegocioException(" No se encontro el archivo (.jrxml). Revisa la ruta.");
            }
            
            JasperReport report = JasperCompileManager.compileReport(ruta);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);
            
            if (lista.isEmpty()) {
                throw new NegocioException("No se encontraron coincidencias con los filtros aplicados.");
            }
            return JasperFillManager.fillReport(report, parametros, dataSource);

        } catch (NegocioException | JRException ex) {
            throw new NegocioException("Error al procesar el reporte: " + ex.getMessage());
        }
    }
    
    @Override
    public JasperPrint generarJasperReporteComandas(LocalDate inicio, LocalDate fin) throws NegocioException {
        try {
            List<ComandaDTO> lista = comanda.buscarPorFiltros(null);

            DateTimeFormatter formatoSimple = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("fechaInicio", inicio.format(formatoSimple));
            parametros.put("fechaFin", fin.format(formatoSimple));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reportes/comandas.jrxml"));

            return JasperFillManager.fillReport(report, parametros, dataSource);

        } catch (NegocioException | JRException ex) {
            throw new NegocioException("Error al procesar el reporte: " + ex.getMessage());
        }
    }
    
    /**
     * Genera y exporta el reporte de clientes frecuentes en formato PDF.
     *
     * @param rutaSalidaPDF ruta donde se guardará el archivo PDF
     * @param nombreFiltro filtro por nombre del cliente
     * @param minimoVisitas filtro por número mínimo de visitas
     * @throws Exception si ocurre un error durante la generación o exportación
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
