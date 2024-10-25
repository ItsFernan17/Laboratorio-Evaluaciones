/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportesmindef;

import Reportes.ReporteCertificacion;
import Reportes.ReporteExamen;
import Reportes.ReporteUsuarios;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ferna
 */
public class factory {
    
    
    private static ArrayList<ReporteUsuarios> reporteUsuarios = new ArrayList<ReporteUsuarios>();
    
    private static ArrayList<ReporteCertificacion> reporteCertificacion = new ArrayList<ReporteCertificacion>();
    
    private static ArrayList<ReporteExamen> reporteExamen = new ArrayList<ReporteExamen>();
    
    
    public static List reporteUsuarios(){
        return getReporteUsuarios();
    }
    
    public static List reporteCertificacion(){
        return getReporteCertificacion();
    }

    public static List reporteExamen(){
        return getReporteExamen();
    }

    /**
     * @return the reporteUsuarios
     */
    public static ArrayList<ReporteUsuarios> getReporteUsuarios() {
        return reporteUsuarios;
    }

    /**
     * @param aReporteUsuarios the reporteUsuarios to set
     */
    public static void setReporteUsuarios(ArrayList<ReporteUsuarios> aReporteUsuarios) {
        reporteUsuarios = aReporteUsuarios;
    }

    /**
     * @return the reporteCertificacion
     */
    public static ArrayList<ReporteCertificacion> getReporteCertificacion() {
        return reporteCertificacion;
    }

    /**
     * @param aReporteCertificacion the reporteCertificacion to set
     */
    public static void setReporteCertificacion(ArrayList<ReporteCertificacion> aReporteCertificacion) {
        reporteCertificacion = aReporteCertificacion;
    }

    /**
     * @return the reporteExamen
     */
    public static ArrayList<ReporteExamen> getReporteExamen() {
        return reporteExamen;
    }

    /**
     * @param aReporteExamen the reporteExamen to set
     */
    public static void setReporteExamen(ArrayList<ReporteExamen> aReporteExamen) {
        reporteExamen = aReporteExamen;
    }
    
}
