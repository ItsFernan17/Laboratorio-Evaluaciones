/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Pojos.Pregunta;
import Pojos.Respuesta;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author ferna
 */
@ManagedBean
@ViewScoped

public class beanPreguntaRespuesta {

    private String enunciado;
    private BigDecimal punteo;
    private String respuesta;
    private String respuesta2;
    private String respuesta3;
    private Integer codigoPregunta;
    private Integer codigoRespuesta;
    private Boolean esCorrecta;
    private Boolean esCorrecta2;
    private Boolean esCorrecta3;
    private List<Object[]> listaRespuestas;
    private List listaPreguntas = null;

    @PostConstruct
    public void init() {
        comboPregunta();
       mostrar();
    }

    public void insertarPregunta() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (enunciado == null || enunciado.isEmpty() || punteo == null) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {
            if (CRUDs.CRUDPregunta.insert(enunciado, punteo, "1724271260706")) {
                limpiar();
                recargarPreguntas();
                context.addMessage(null, new FacesMessage("Exito", "¡Pregunta registrada!"));
            } else {
                context.addMessage(null, new FacesMessage("Error", "Registro No ingresado"));
            }
        }
    }

    public void insertarRespuesta() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean exito = true;

        if (CRUDs.CRUDRespuesta.preguntaTieneRespuestas(codigoPregunta)) {
            context.addMessage(null, new FacesMessage("Error", "Pregunta con respuestas ya asignadas"));
            return;
        }

        for (int i = 1; i <= 3; i++) {
            String currentRespuesta = null;
            Boolean currentEsCorrecta = null;

            if (i == 1) {
                currentRespuesta = respuesta;
                currentEsCorrecta = esCorrecta;
            } else if (i == 2) {
                currentRespuesta = respuesta2;
                currentEsCorrecta = esCorrecta2;
            } else if (i == 3) {
                currentRespuesta = respuesta3;
                currentEsCorrecta = esCorrecta3;
            }

            if (currentRespuesta == null || currentRespuesta.isEmpty() || currentEsCorrecta == null || codigoPregunta == null) {
                context.addMessage(null, new FacesMessage("Error", "Campos vacíos en la respuesta " + i));
                exito = false;
                break;
            } else if (!CRUDs.CRUDRespuesta.insert(currentRespuesta, currentEsCorrecta, codigoPregunta, "1724271260706")) {
                context.addMessage(null, new FacesMessage("Error", "No se pudo registrar la respuesta " + i));
                exito = false;
                break;
            }
        }

        // Si todas las respuestas se insertaron correctamente
        if (exito) {
            limpiar();
            context.addMessage(null, new FacesMessage("Éxito", "¡Respuestas registradas!"));
        }
    }

    public void update() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (enunciado == null || enunciado.isEmpty() || punteo == null || codigoPregunta == null) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {

            if (CRUDs.CRUDPregunta.update(codigoPregunta, enunciado, punteo, "1724271260706")) {
                context.addMessage(null, new FacesMessage("Exito", "¡Pregunta Actualizado!"));
                mostrar();
            } else {
                context.addMessage(null, new FacesMessage("Error", "¡Registro No actualizado"));
            }
        }
    }

    public void anular() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (codigoPregunta == null) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {
            if (CRUDs.CRUDPregunta.anular(codigoPregunta)) {
                mostrar();
                limpiar();
                context.addMessage(null, new FacesMessage("Exito", "¡Pregunta Anulado!"));
            } else {
                context.addMessage(null, new FacesMessage("Error", "Registro No Anulado"));
            }
        }
    }
    
    private void mostrar(){
         listaRespuestas = CRUDs.CRUDRespuesta.universo();
    }

    public void seleccionarPregunta(Object[] row) {
        if (row != null) {
            try {
                Integer codigoPregunta = (Integer) row[0];
                String enunciado = (String) row[1];
                BigDecimal punteo = (BigDecimal) row[2];

                this.codigoPregunta = codigoPregunta;
                this.enunciado = enunciado;
                this.punteo = punteo;
            } catch (ClassCastException e) {
                System.out.println("Error al castear los valores de la fila: " + e.getMessage());
            }
        }
    }

    public void limpiar() {
        setEnunciado("");
        setPunteo(null);
        setRespuesta("");
        setRespuesta2("");
        setRespuesta3("");
        setEsCorrecta(null);
        setEsCorrecta2(null);
        setEsCorrecta3(null);
        setCodigoPregunta(0);
    }

    public List<SelectItem> comboPregunta() {
        listaPreguntas = new ArrayList<>();
        List<Pojos.Pregunta> lstPregunta = CRUDs.CRUDPregunta.universo();
        for (Pojos.Pregunta pregunta : lstPregunta) {
            SelectItem residenciaItem = new SelectItem(pregunta.getCodigoPregunta(), pregunta.getEnunciado());
            listaPreguntas.add(residenciaItem);
        }
        return listaPreguntas;
    }

    public void recargarPreguntas() {
        listaPreguntas = null;
        comboPregunta();
    }

    /**
     * @return the enunciado
     */
    public String getEnunciado() {
        return enunciado;
    }

    /**
     * @param enunciado the enunciado to set
     */
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    /**
     * @return the punteo
     */
    public BigDecimal getPunteo() {
        return punteo;
    }

    /**
     * @param punteo the punteo to set
     */
    public void setPunteo(BigDecimal punteo) {
        this.punteo = punteo;
    }

    /**
     * @return the respuesta
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * @param respuesta the respuesta to set
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * @return the codigoPregunta
     */
    public Integer getCodigoPregunta() {
        return codigoPregunta;
    }

    /**
     * @param codigoPregunta the codigoPregunta to set
     */
    public void setCodigoPregunta(Integer codigoPregunta) {
        this.codigoPregunta = codigoPregunta;
    }

    /**
     * @return the codigoRespuesta
     */
    public Integer getCodigoRespuesta() {
        return codigoRespuesta;
    }

    /**
     * @param codigoRespuesta the codigoRespuesta to set
     */
    public void setCodigoRespuesta(Integer codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    /**
     * @return the esCorrecta
     */
    public Boolean getEsCorrecta() {
        return esCorrecta;
    }

    /**
     * @param esCorrecta the esCorrecta to set
     */
    public void setEsCorrecta(Boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    /**
     * @return the respuesta2
     */
    public String getRespuesta2() {
        return respuesta2;
    }

    /**
     * @param respuesta2 the respuesta2 to set
     */
    public void setRespuesta2(String respuesta2) {
        this.respuesta2 = respuesta2;
    }

    /**
     * @return the respuesta3
     */
    public String getRespuesta3() {
        return respuesta3;
    }

    /**
     * @param respuesta3 the respuesta3 to set
     */
    public void setRespuesta3(String respuesta3) {
        this.respuesta3 = respuesta3;
    }

    /**
     * @return the esCorrecta2
     */
    public Boolean getEsCorrecta2() {
        return esCorrecta2;
    }

    /**
     * @param esCorrecta2 the esCorrecta2 to set
     */
    public void setEsCorrecta2(Boolean esCorrecta2) {
        this.esCorrecta2 = esCorrecta2;
    }

    /**
     * @return the esCorrecta3
     */
    public Boolean getEsCorrecta3() {
        return esCorrecta3;
    }

    /**
     * @param esCorrecta3 the esCorrecta3 to set
     */
    public void setEsCorrecta3(Boolean esCorrecta3) {
        this.esCorrecta3 = esCorrecta3;
    }

    /**
     * @return the listaPreguntas
     */
    public List getListaPreguntas() {
        return listaPreguntas;
    }

    /**
     * @param listaPreguntas the listaPreguntas to set
     */
    public void setListaPreguntas(List listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    /**
     * @return the listaRespuestas
     */
    public List<Object[]> getListaRespuestas() {
        return listaRespuestas;
    }

    /**
     * @param listaRespuestas the listaRespuestas to set
     */
    public void setListaRespuestas(List<Object[]> listaRespuestas) {
        this.listaRespuestas = listaRespuestas;
    }

}
