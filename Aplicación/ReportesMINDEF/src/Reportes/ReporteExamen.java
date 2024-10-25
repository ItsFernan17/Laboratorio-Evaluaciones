/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ferna
 */
public class ReporteExamen {
    
    private String evaluado;
    private Date fechaEvaluacion;
    private String empleo;
    private String serie;
    private String instruccion;
    private String pregunta;
    private BigDecimal punteoPregunta;
    private String opcion1;
    private String opcion2;
    private String opcion3;    

    public ReporteExamen(String evaluado, Date fechaEvaluacion, String empleo, String serie, String instruccion, String pregunta, BigDecimal punteoPregunta, String opcion1, String opcion2, String opcion3) {
        this.evaluado = evaluado;
        this.fechaEvaluacion = fechaEvaluacion;
        this.empleo = empleo;
        this.serie = serie;
        this.instruccion = instruccion;
        this.pregunta = pregunta;
        this.punteoPregunta = punteoPregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
    }
    
    
    

    /**
     * @return the evaluado
     */
    public String getEvaluado() {
        return evaluado;
    }

    /**
     * @param evaluado the evaluado to set
     */
    public void setEvaluado(String evaluado) {
        this.evaluado = evaluado;
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

    /**
     * @return the serie
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return the instruccion
     */
    public String getInstruccion() {
        return instruccion;
    }

    /**
     * @param instruccion the instruccion to set
     */
    public void setInstruccion(String instruccion) {
        this.instruccion = instruccion;
    }

    /**
     * @return the pregunta
     */
    public String getPregunta() {
        return pregunta;
    }

    /**
     * @param pregunta the pregunta to set
     */
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    /**
     * @return the punteoPregunta
     */
    public BigDecimal getPunteoPregunta() {
        return punteoPregunta;
    }

    /**
     * @param punteoPregunta the punteoPregunta to set
     */
    public void setPunteoPregunta(BigDecimal punteoPregunta) {
        this.punteoPregunta = punteoPregunta;
    }

    /**
     * @return the opcion1
     */
    public String getOpcion1() {
        return opcion1;
    }

    /**
     * @param opcion1 the opcion1 to set
     */
    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    /**
     * @return the opcion2
     */
    public String getOpcion2() {
        return opcion2;
    }

    /**
     * @param opcion2 the opcion2 to set
     */
    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    /**
     * @return the opcion3
     */
    public String getOpcion3() {
        return opcion3;
    }

    /**
     * @param opcion3 the opcion3 to set
     */
    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }
    
}
