/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import excepciones.NegocioException;
import java.time.LocalDate;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Mariana, Regina, Isaac
 */
public interface IReporteBO {
    public JasperPrint generarJasperReporteComandas(LocalDate inicio, LocalDate fin) throws NegocioException;
    public JasperPrint generarJasperClientesFrecuentes(String nombreFiltro, Integer minimoVisitas) throws Exception ;
    public void generarReportePDF(String rutaSalidaPDF, String nombreFiltro, Integer minimoVisitas) throws Exception ;
}
