/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Pojos.BancoRespuestas;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ferna
 */
@ManagedBean
@ViewScoped
public class beanRespuestaExamen {

    private String respuesta;
    private Integer codigoRespuesta;
    private List listaRespuesta;
    private ArrayList<String> respuestas;

    @PostConstruct
    public void init() {
        mostrarRespuestas();
        respuestasComboBox();
    }

    public void insert() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (respuesta == null || respuesta.isEmpty()) {
            context.addMessage(null, new FacesMessage("Error", "Campos Requeridos Vacios"));
        } else {
            if (CRUDs.CURDRespuestas.insert(respuesta, 1)) {
                context.addMessage(null, new FacesMessage("Registo Cargado", "Respuesta Registrada"));
                mostrarRespuestas();
                limpiar();

            } else {
                context.addMessage(null, new FacesMessage("Error al Cargar", "Respuesta No Registrada"));
            }
        }
    }

    public void update() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (respuesta == null || respuesta.isEmpty()) {
            context.addMessage(null, new FacesMessage("Error", "Campos Requeridos Vacios"));
        } else {
            if (CRUDs.CURDRespuestas.update(codigoRespuesta, respuesta, 1)) {
                context.addMessage(null, new FacesMessage("Registo Actualizado", "Respuesta Actualizada"));
                mostrarRespuestas();
                limpiar();
            } else {
                context.addMessage(null, new FacesMessage("Error al Actualizar", "Respuesta No Actualizada"));
            }
        }
    }

    public void anular() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (codigoRespuesta == null) {
            context.addMessage(null, new FacesMessage("Error", "Respuesta No Registrada en el Sistema"));
        } else {
            if (CRUDs.CURDRespuestas.anular(codigoRespuesta)) {
                context.addMessage(null, new FacesMessage("Registo Anulado", "Respuesta Anulada del Sistema"));
                mostrarRespuestas();
                limpiar();
            } else {
                context.addMessage(null, new FacesMessage("Error al Anular", "Respuesta No Anulada"));
            }
        }
    }

    public void mostrarRespuestas() {
        setListaRespuesta(CRUDs.CURDRespuestas.universo());
    }

    public void respuestasComboBox() {
        respuestas = new ArrayList<>();
        List<BancoRespuestas> lista = CRUDs.CURDRespuestas.universo();

        if (lista != null) {
            for (BancoRespuestas res : lista) {
                respuestas.add(res.getRespuesta());
            }
        }
    }

    public void seleccionarRespuesta(Pojos.BancoRespuestas respuestas) {
        setCodigoRespuesta(respuestas.getCodigoRespuesta());
        setRespuesta(respuestas.getRespuesta());
    }

    public void limpiar() {
        setRespuesta("");
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
     * @return the listaRespuesta
     */
    public List getListaRespuesta() {
        return listaRespuesta;
    }

    /**
     * @param listaRespuesta the listaRespuesta to set
     */
    public void setListaRespuesta(List listaRespuesta) {
        this.listaRespuesta = listaRespuesta;
    }

    /**
     * @return the respuestas
     */
    public ArrayList<String> getRespuestas() {
        return respuestas;
    }

    /**
     * @param respuestas the respuestas to set
     */
    public void setRespuestas(ArrayList<String> respuestas) {
        this.respuestas = respuestas;
    }
}
