/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Scope.sessionBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author ferna
 */
@ManagedBean
@ViewScoped

public class beanExamen {

    private Date fechaEvaluacion;
    private Integer codigoMotivo;
    private String ceom;
    private Integer punteoMaximo;
    private List comboExamen;
    private List listExamen = null;
    private List listMotivo = null;
    private List listEmpleo = null;
    private List listPreguntas = null;
    private List listExamenes = null;

    private Integer codigoExamen;
    private String serie1 = "Serie I";
    private String serie2 = "Serie II";
    private String serie3 = "Serie III";
    private String instruccion1;
    private String instruccion2;
    private String instruccion3;
    private Integer codigoPregunta1;
    private Integer codigoPregunta2;
    private Integer codigoPregunta3;
    private Integer codigoPregunta4;
    private Integer codigoPregunta5;
    private Integer codigoPregunta6;
    private Integer codigoPregunta7;
    private Integer codigoPregunta8;
    private Integer codigoPregunta9;
    private Integer codigoPregunta10;
    private Integer codigoPregunta11;
    private Integer codigoPregunta12;
    
    @ManagedProperty(value = "#{sessionBean}")
    private sessionBean sessionBean = null;

    public sessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(sessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }    
    

    @PostConstruct
    public void init() {
        comboMotivos();
        comboEmpleos();
        comboExamen();
        comboPregunta();
        mostrar();
    }

    public void insertarExamen() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (fechaEvaluacion == null || punteoMaximo == null || codigoMotivo == null || ceom == null) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {
            if (CRUDs.CRUDExamen.insert(fechaEvaluacion, codigoMotivo, ceom, punteoMaximo, getSessionBean().getUsuario().getDpi())) {
                limpiar();
                recargarExamenes();
                context.addMessage(null, new FacesMessage("Exito", "¡Examen registrado!"));
            } else {
                context.addMessage(null, new FacesMessage("Error", "Registro No ingresado"));
            }
        }
    }

    public void mostrar() {
        setListExamenes(CRUDs.CRUDExamen.universo());
    }

    public void update() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (codigoExamen == null || fechaEvaluacion == null || punteoMaximo == null || codigoMotivo == null || ceom == null) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {

            if (CRUDs.CRUDExamen.update(codigoExamen, fechaEvaluacion, codigoMotivo, ceom, punteoMaximo, getSessionBean().getUsuario().getDpi())) {
                context.addMessage(null, new FacesMessage("Exito", "¡Examen Actualizado!"));
                mostrar();
            } else {
                context.addMessage(null, new FacesMessage("Error", "Registro No actualizado"));
            }
        }
    }

        public void anular() {
            FacesContext context = FacesContext.getCurrentInstance();
            
            if (codigoExamen == null) {
                context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
            } else {
                boolean examenAnulado = CRUDs.CRUDExamen.anular(codigoExamen);

                boolean detallesAnulados = CRUDs.CRUDExamenDetalle.anularDetallesPorExamen(codigoExamen);

                if (examenAnulado && detallesAnulados) {
                    mostrar();
                    limpiar();
                    context.addMessage(null, new FacesMessage("Éxito", "¡Examen anulado completamente!"));
                } else {
                    context.addMessage(null, new FacesMessage("Error", "No se pudo anular el examen o sus detalles"));
                }
            }
        }

    public void seleccionar(Pojos.Examen examen) {
        setFechaEvaluacion(examen.getFechaEvaluacion());
        setCodigoMotivo(examen.getMotivo().getCodigoMotivo());
        setCeom(examen.getEmpleo().getCeom());
        setPunteoMaximo(examen.getPunteoMaximo());
        setCodigoExamen(examen.getCodigoExamen());
    }

    public void insertarDetalleExamen() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean exito = true;

        if (codigoExamen == null || codigoPregunta1 == null || codigoPregunta2 == null || codigoPregunta3 == null || codigoPregunta4 == null
                || codigoPregunta5 == null || codigoPregunta6 == null || codigoPregunta7 == null || codigoPregunta8 == null || codigoPregunta9 == null
                || codigoPregunta10 == null || codigoPregunta11 == null || codigoPregunta12 == null) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));

        } else {
            for (int i = 1; i <= 3; i++) {
                String currentSerie = null;
                String currentInstruccion = null;
                Integer[] currentPreguntas = new Integer[4];

                if (i == 1) {
                    currentSerie = serie1;
                    currentInstruccion = instruccion1;
                    currentPreguntas[0] = codigoPregunta1;
                    currentPreguntas[1] = codigoPregunta2;
                    currentPreguntas[2] = codigoPregunta3;
                    currentPreguntas[3] = codigoPregunta4;
                } else if (i == 2) {
                    currentSerie = serie2;
                    currentInstruccion = instruccion2;
                    currentPreguntas[0] = codigoPregunta5;
                    currentPreguntas[1] = codigoPregunta6;
                    currentPreguntas[2] = codigoPregunta7;
                    currentPreguntas[3] = codigoPregunta8;
                } else if (i == 3) {
                    currentSerie = serie3;
                    currentInstruccion = instruccion3;
                    currentPreguntas[0] = codigoPregunta9;
                    currentPreguntas[1] = codigoPregunta10;
                    currentPreguntas[2] = codigoPregunta11;
                    currentPreguntas[3] = codigoPregunta12;
                }

                if (currentInstruccion == null || currentInstruccion.isEmpty()) {
                    context.addMessage(null, new FacesMessage("Error", "Instrucción vacía en la serie " + currentSerie));
                    exito = false;
                    break;
                }

                for (int j = 0; j < currentPreguntas.length; j++) {
                    Integer currentPregunta = currentPreguntas[j];

                    if (currentPregunta == null) {
                        context.addMessage(null, new FacesMessage("Error", "Pregunta vacía en la serie " + currentSerie + " para la pregunta " + (j + 1)));
                        exito = false;
                        break;
                    } else if (!CRUDs.CRUDExamenDetalle.insert(codigoExamen, currentSerie, currentInstruccion, currentPregunta, getSessionBean().getUsuario().getDpi())) {
                        context.addMessage(null, new FacesMessage("Error", "No se pudo registrar el detalle de la serie " + currentSerie + " para la pregunta " + (j + 1)));
                        exito = false;
                        break;
                    }
                }

                if (!exito) {
                    break;
                }
            }

            if (exito) {
                limpiar();
                context.addMessage(null, new FacesMessage("Éxito", "¡Detalles del examen registrados!"));
            }
        }
    }

    public void limpiar() {
        setFechaEvaluacion(null);
        setCodigoMotivo(0);
        setCeom("0");
        setPunteoMaximo(0);
        setCodigoExamen(0);
        setInstruccion1("");
        setInstruccion2("");
        setInstruccion3("");
        setCodigoPregunta1(0);
        setCodigoPregunta2(0);
        setCodigoPregunta3(0);
        setCodigoPregunta4(0);
        setCodigoPregunta5(0);
        setCodigoPregunta6(0);
        setCodigoPregunta7(0);
        setCodigoPregunta8(0);
        setCodigoPregunta9(0);
        setCodigoPregunta10(0);
        setCodigoPregunta11(0);
        setCodigoPregunta12(0);
    }

    private List<SelectItem> comboMotivos() {
        setListMotivo(new ArrayList<SelectItem>());
        List<Pojos.Motivo> lstMotivo = CRUDs.CRUDMotivo.universo();

        for (Pojos.Motivo motivo : lstMotivo) {
            if (motivo.getCodigoMotivo() < 200) {
                SelectItem motivoItem = new SelectItem(motivo.getCodigoMotivo(), motivo.getNombreMotivo());
                getListMotivo().add(motivoItem);
            }
        }

        return getListMotivo();
    }

    public List<SelectItem> comboPregunta() {
        listPreguntas = new ArrayList<>();
        List<Pojos.Pregunta> lstPregunta = CRUDs.CRUDPregunta.universo();
        for (Pojos.Pregunta pregunta : lstPregunta) {
            SelectItem residenciaItem = new SelectItem(pregunta.getCodigoPregunta(), pregunta.getEnunciado());
            listPreguntas.add(residenciaItem);
        }
        return listPreguntas;
    }

    public List<SelectItem> comboEmpleos() {
        if (listEmpleo == null) {
            listEmpleo = new ArrayList<>();
            List<Pojos.Empleo> lstEmpleos = CRUDs.CRUDEmpleo.universo();
            for (Pojos.Empleo empleo : lstEmpleos) {
                SelectItem empeloItem = new SelectItem(empleo.getCeom(), empleo.getDescripcion());
                listEmpleo.add(empeloItem);
            }
        }
        return listEmpleo;
    }

    public List<SelectItem> comboExamen() {
        if (listExamen == null) {
            listExamen = new ArrayList<>();
            List<Pojos.Examen> lstExamen = CRUDs.CRUDExamen.universo();
            for (Pojos.Examen examen : lstExamen) {
                SelectItem examenItem = new SelectItem(examen.getCodigoExamen(), examen.getMotivo().getNombreMotivo() + " - " + examen.getEmpleo().getDescripcion());
                listExamen.add(examenItem);
            }
        }
        return listExamen;
    }

    public void recargarExamenes() {
        listExamen = null; // Forzar la recarga de la lista
        comboExamen();
    }

    /**
     * @return the serie1
     */
    public String getSerie1() {
        return serie1;
    }

    /**
     * @param serie1 the serie1 to set
     */
    public void setSerie1(String serie1) {
        this.serie1 = serie1;
    }

    /**
     * @return the serie2
     */
    public String getSerie2() {
        return serie2;
    }

    /**
     * @param serie2 the serie2 to set
     */
    public void setSerie2(String serie2) {
        this.serie2 = serie2;
    }

    /**
     * @return the serie3
     */
    public String getSerie3() {
        return serie3;
    }

    /**
     * @param serie3 the serie3 to set
     */
    public void setSerie3(String serie3) {
        this.serie3 = serie3;
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
     * @return the codigoMotivo
     */
    public Integer getCodigoMotivo() {
        return codigoMotivo;
    }

    /**
     * @param codigoMotivo the codigoMotivo to set
     */
    public void setCodigoMotivo(Integer codigoMotivo) {
        this.codigoMotivo = codigoMotivo;
    }

    /**
     * @return the ceom
     */
    public String getCeom() {
        return ceom;
    }

    /**
     * @param ceom the ceom to set
     */
    public void setCeom(String ceom) {
        this.ceom = ceom;
    }

    /**
     * @return the punteoMaximo
     */
    public Integer getPunteoMaximo() {
        return punteoMaximo;
    }

    /**
     * @param punteoMaximo the punteoMaximo to set
     */
    public void setPunteoMaximo(Integer punteoMaximo) {
        this.punteoMaximo = punteoMaximo;
    }

    /**
     * @return the codigoExamen
     */
    public Integer getCodigoExamen() {
        return codigoExamen;
    }

    /**
     * @param codigoExamen the codigoExamen to set
     */
    public void setCodigoExamen(Integer codigoExamen) {
        this.codigoExamen = codigoExamen;
    }

    /**
     * @return the instruccion1
     */
    public String getInstruccion1() {
        return instruccion1;
    }

    /**
     * @param instruccion1 the instruccion1 to set
     */
    public void setInstruccion1(String instruccion1) {
        this.instruccion1 = instruccion1;
    }

    /**
     * @return the instruccion2
     */
    public String getInstruccion2() {
        return instruccion2;
    }

    /**
     * @param instruccion2 the instruccion2 to set
     */
    public void setInstruccion2(String instruccion2) {
        this.instruccion2 = instruccion2;
    }

    /**
     * @return the instruccion3
     */
    public String getInstruccion3() {
        return instruccion3;
    }

    /**
     * @param instruccion3 the instruccion3 to set
     */
    public void setInstruccion3(String instruccion3) {
        this.instruccion3 = instruccion3;
    }

    /**
     * @return the codigoPregunta1
     */
    public Integer getCodigoPregunta1() {
        return codigoPregunta1;
    }

    /**
     * @param codigoPregunta1 the codigoPregunta1 to set
     */
    public void setCodigoPregunta1(Integer codigoPregunta1) {
        this.codigoPregunta1 = codigoPregunta1;
    }

    /**
     * @return the codigoPregunta2
     */
    public Integer getCodigoPregunta2() {
        return codigoPregunta2;
    }

    /**
     * @param codigoPregunta2 the codigoPregunta2 to set
     */
    public void setCodigoPregunta2(Integer codigoPregunta2) {
        this.codigoPregunta2 = codigoPregunta2;
    }

    /**
     * @return the codigoPregunta3
     */
    public Integer getCodigoPregunta3() {
        return codigoPregunta3;
    }

    /**
     * @param codigoPregunta3 the codigoPregunta3 to set
     */
    public void setCodigoPregunta3(Integer codigoPregunta3) {
        this.codigoPregunta3 = codigoPregunta3;
    }

    /**
     * @return the codigoPregunta4
     */
    public Integer getCodigoPregunta4() {
        return codigoPregunta4;
    }

    /**
     * @param codigoPregunta4 the codigoPregunta4 to set
     */
    public void setCodigoPregunta4(Integer codigoPregunta4) {
        this.codigoPregunta4 = codigoPregunta4;
    }

    /**
     * @return the codigoPregunta5
     */
    public Integer getCodigoPregunta5() {
        return codigoPregunta5;
    }

    /**
     * @param codigoPregunta5 the codigoPregunta5 to set
     */
    public void setCodigoPregunta5(Integer codigoPregunta5) {
        this.codigoPregunta5 = codigoPregunta5;
    }

    /**
     * @return the codigoPregunta6
     */
    public Integer getCodigoPregunta6() {
        return codigoPregunta6;
    }

    /**
     * @param codigoPregunta6 the codigoPregunta6 to set
     */
    public void setCodigoPregunta6(Integer codigoPregunta6) {
        this.codigoPregunta6 = codigoPregunta6;
    }

    /**
     * @return the codigoPregunta7
     */
    public Integer getCodigoPregunta7() {
        return codigoPregunta7;
    }

    /**
     * @param codigoPregunta7 the codigoPregunta7 to set
     */
    public void setCodigoPregunta7(Integer codigoPregunta7) {
        this.codigoPregunta7 = codigoPregunta7;
    }

    /**
     * @return the codigoPregunta8
     */
    public Integer getCodigoPregunta8() {
        return codigoPregunta8;
    }

    /**
     * @param codigoPregunta8 the codigoPregunta8 to set
     */
    public void setCodigoPregunta8(Integer codigoPregunta8) {
        this.codigoPregunta8 = codigoPregunta8;
    }

    /**
     * @return the codigoPregunta9
     */
    public Integer getCodigoPregunta9() {
        return codigoPregunta9;
    }

    /**
     * @param codigoPregunta9 the codigoPregunta9 to set
     */
    public void setCodigoPregunta9(Integer codigoPregunta9) {
        this.codigoPregunta9 = codigoPregunta9;
    }

    /**
     * @return the codigoPregunta10
     */
    public Integer getCodigoPregunta10() {
        return codigoPregunta10;
    }

    /**
     * @param codigoPregunta10 the codigoPregunta10 to set
     */
    public void setCodigoPregunta10(Integer codigoPregunta10) {
        this.codigoPregunta10 = codigoPregunta10;
    }

    /**
     * @return the codigoPregunta11
     */
    public Integer getCodigoPregunta11() {
        return codigoPregunta11;
    }

    /**
     * @param codigoPregunta11 the codigoPregunta11 to set
     */
    public void setCodigoPregunta11(Integer codigoPregunta11) {
        this.codigoPregunta11 = codigoPregunta11;
    }

    /**
     * @return the codigoPregunta12
     */
    public Integer getCodigoPregunta12() {
        return codigoPregunta12;
    }

    /**
     * @param codigoPregunta12 the codigoPregunta12 to set
     */
    public void setCodigoPregunta12(Integer codigoPregunta12) {
        this.codigoPregunta12 = codigoPregunta12;
    }

    /**
     * @return the comboExamen
     */
    public List getComboExamen() {
        return comboExamen;
    }

    /**
     * @param comboExamen the comboExamen to set
     */
    public void setComboExamen(List comboExamen) {
        this.comboExamen = comboExamen;
    }

    /**
     * @return the listExamen
     */
    public List getListExamen() {
        return listExamen;
    }

    /**
     * @param listExamen the listExamen to set
     */
    public void setListExamen(List listExamen) {
        this.listExamen = listExamen;
    }

    /**
     * @return the listMotivo
     */
    public List getListMotivo() {
        return listMotivo;
    }

    /**
     * @param listMotivo the listMotivo to set
     */
    public void setListMotivo(List listMotivo) {
        this.listMotivo = listMotivo;
    }

    /**
     * @return the listEmpleo
     */
    public List getListEmpleo() {
        return listEmpleo;
    }

    /**
     * @param listEmpleo the listEmpleo to set
     */
    public void setListEmpleo(List listEmpleo) {
        this.listEmpleo = listEmpleo;
    }

    /**
     * @return the listPreguntas
     */
    public List getListPreguntas() {
        return listPreguntas;
    }

    /**
     * @param listPreguntas the listPreguntas to set
     */
    public void setListPreguntas(List listPreguntas) {
        this.listPreguntas = listPreguntas;
    }

    /**
     * @return the listExamenes
     */
    public List getListExamenes() {
        return listExamenes;
    }

    /**
     * @param listExamenes the listExamenes to set
     */
    public void setListExamenes(List listExamenes) {
        this.listExamenes = listExamenes;
    }

}
