/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import java.util.Date;

/**
 *
 * @author ferna
 */
public class ReporteCertificacion {
    
    private String dpi;
    private String nombreCompleto;
    private String telefono;
    private String grado;
    private String departamento;
    private String comando;
    private String poblacion;
    private Date fechaEvaluacion;
    private String motivo;
    private String empleo;

    public ReporteCertificacion(String dpi, String nombreCompleto, String telefono, String grado, String departamento, String comando, String poblacion, Date fechaEvaluacion, String motivo, String empleo) {
        this.dpi = dpi;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.grado = grado;
        this.departamento = departamento;
        this.comando = comando;
        this.poblacion = poblacion;
        this.fechaEvaluacion = fechaEvaluacion;
        this.motivo = motivo;
        this.empleo = empleo;
    }
    
    
    

    /**
     * @return the dpi
     */
    public String getDpi() {
        return dpi;
    }

    /**
     * @param dpi the dpi to set
     */
    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the grado
     */
    public String getGrado() {
        return grado;
    }

    /**
     * @param grado the grado to set
     */
    public void setGrado(String grado) {
        this.grado = grado;
    }

    /**
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the comando
     */
    public String getComando() {
        return comando;
    }

    /**
     * @param comando the comando to set
     */
    public void setComando(String comando) {
        this.comando = comando;
    }

    /**
     * @return the poblacion
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     * @param poblacion the poblacion to set
     */
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * @return the fechaEvaluacion
     */
    public Date getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    /**
     * @param fechaEvaluacion the fechaEvaluacion to set
     */
    public void setFechaEvaluacion(Date fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * @return the empleo
     */
    public String getEmpleo() {
        return empleo;
    }

    /**
     * @param empleo the empleo to set
     */
    public void setEmpleo(String empleo) {
        this.empleo = empleo;
    }

    
}
