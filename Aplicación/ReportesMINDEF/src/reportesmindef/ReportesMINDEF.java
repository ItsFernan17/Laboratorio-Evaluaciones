/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportesmindef;

import Reportes.ReporteCertificacion;
import Reportes.ReporteUsuarios;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ferna
 */
public class ReportesMINDEF {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    }

    public static List reporteUsuarios() {
        List<ReporteUsuarios> list = new ArrayList<ReporteUsuarios>();
        for (Iterator it = CRUDs.CRUDUsuario.reporteUsuarios().iterator(); it.hasNext();) {
            Object[] item = (Object[]) it.next();
            list.add(new ReporteUsuarios((String) item[0], (String) item[1], (String) item[2], (String) item[3], (String) item[4], (String) item[5], (String) item[6], (String) item[7]));
            factory reporte = new factory();
            reporte.setReporteUsuarios((ArrayList<ReporteUsuarios>) list);
        }
        return list;
    }

     public static List reporteCertificación(String evaluado, Integer examen) {
        List<ReporteCertificacion> list = new ArrayList<ReporteCertificacion>();
        try {
            for (Iterator it = CRUDs.CRUDAsignacion.reporteCertificacion(examen, evaluado).iterator(); it.hasNext();) {
                Object[] item = (Object[]) it.next();
                list.add(new ReporteCertificacion((String) item[0], (String) item[1], (String) item[2], (String) item[3], (String) item[4], (String) item[5], (String) item[6], (Date) item[7], (String) item[8], (String) item[9]));
                factory reporte = new factory();
                reporte.setReporteCertificacion((ArrayList<ReporteCertificacion>) list);
            }
        } catch (ParseException ex) {
            Logger.getLogger(ReportesMINDEF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    

}
