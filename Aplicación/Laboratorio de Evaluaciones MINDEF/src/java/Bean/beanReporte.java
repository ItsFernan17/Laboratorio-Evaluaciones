/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import reportesmindef.factory;

/**
 *
 * @author ferna
 */

@ManagedBean
@ViewScoped

public class beanReporte {
        private List listaReporte = null;
        
    
    public void reporteUsuarios() throws IOException, JRException, ParseException {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Integer estado = CRUDs.CRUDUsuario.reporteUsuarios().size();
            if (estado != 0) {
                reportesmindef.ReportesMINDEF.reporteUsuarios();
                setListaReporte(factory.reporteUsuarios());
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaReporte);
                File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Reportes/ReporteUsuarios.jasper"));
                byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), null, ds);
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=\"Reporte Usuarios.pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                FacesContext.getCurrentInstance().responseComplete();
            } else {
                context.addMessage(null, new FacesMessage("Error", "No existe información de Personas"));
            }
        } catch (JRException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "Error al cargar el reporte " + e));
        }
    }    
    
    public void reporteCertificacion(String evaluado, Integer examen) throws IOException, JRException, ParseException {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Integer estado = CRUDs.CRUDAsignacion.reporteCertificacion(examen, evaluado).size();
            if (estado != 0) {
                reportesmindef.ReportesMINDEF.reporteCertificación(evaluado, examen);
                setListaReporte(factory.reporteCertificacion());
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaReporte);
                File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Reportes/ReporteCertificacion.jasper"));
                byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), null, ds);
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=\"Certificacion.pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                FacesContext.getCurrentInstance().responseComplete();
            } else {
                context.addMessage(null, new FacesMessage("Error", "No existe información"));
            }
        } catch (JRException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "Error al cargar el reporte " + e));
        }
    }     

    public void reporteExamen(String evaluado, Integer examen) throws IOException, JRException, ParseException {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Integer estado = CRUDs.CRUDExamenDetalle.reporteExamen(examen, evaluado).size();
            if (estado != 0) {
                reportesmindef.ReportesMINDEF.reporteExamen(evaluado, examen);
                setListaReporte(factory.reporteExamen());
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaReporte);
                File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Reportes/ReporteExamen.jasper"));
                byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), null, ds);
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=\"Examen.pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                FacesContext.getCurrentInstance().responseComplete();
            } else {
                context.addMessage(null, new FacesMessage("Error", "No existe información"));
            }
        } catch (JRException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "Error al cargar el reporte " + e));
        }
    } 

    /**
     * @return the listaReporte
     */
    public List getListaReporte() {
        return listaReporte;
    }

    /**
     * @param listaReporte the listaReporte to set
     */
    public void setListaReporte(List listaReporte) {
        this.listaReporte = listaReporte;
    }
        
}
