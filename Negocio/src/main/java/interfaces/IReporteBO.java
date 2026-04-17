/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import excepciones.NegocioException;
import java.time.LocalDate;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Interfaz que define las operaciones de negocio para la generación de reportes.
 * 
 * Proporciona métodos para crear reportes relacionados con comandas y clientes
 * frecuentes utilizando la herramienta JasperReports.
 * 
 * Permite generar reportes en formato visual (JasperPrint) y exportarlos a PDF.
 * 
 * Esta interfaz forma parte de la capa de negocio y actúa como contrato para
 * las clases que implementan la lógica de generación de reportes.
 * 
 * @author Mariana, Regina, Isaac
 */
public interface IReporteBO {

    /**
     * Genera un reporte de comandas en formato JasperPrint dentro de un rango de fechas.
     * 
     * @param inicio fecha inicial del rango.
     * @param fin fecha final del rango.
     * @return objeto JasperPrint que contiene el reporte generado.
     * @throws NegocioException si ocurre un error durante la generación del reporte.
     */
    public JasperPrint generarJasperReporteComandas(LocalDate inicio, LocalDate fin) throws NegocioException;

    /**
     * Genera un reporte de clientes frecuentes en formato JasperPrint.
     * 
     * Permite filtrar por nombre y por un número mínimo de visitas.
     * 
     * @param nombreFiltro nombre o parte del nombre del cliente.
     * @param minimoVisitas número mínimo de visitas requeridas.
     * @return objeto JasperPrint con la información del reporte.
     * @throws Exception si ocurre un error durante la generación del reporte.
     */
    public JasperPrint generarJasperClientesFrecuentes(String nombreFiltro, Integer minimoVisitas) throws Exception;

    /**
     * Genera un reporte de clientes frecuentes en formato PDF.
     * 
     * Utiliza los filtros proporcionados y guarda el archivo en la ruta especificada.
     * 
     * @param rutaSalidaPDF ruta donde se guardará el archivo PDF.
     * @param nombreFiltro nombre o parte del nombre del cliente.
     * @param minimoVisitas número mínimo de visitas requeridas.
     * @throws Exception si ocurre un error durante la generación o exportación del PDF.
     */
    public void generarReportePDF(String rutaSalidaPDF, String nombreFiltro, Integer minimoVisitas) throws Exception;
    
    public void generarReporteComandasPDF(String rutaSalidaPDF, LocalDate inicio, LocalDate fin) throws Exception;
}