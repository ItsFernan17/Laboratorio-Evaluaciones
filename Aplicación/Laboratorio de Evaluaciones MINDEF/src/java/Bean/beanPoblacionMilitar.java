/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Pojos.Poblacion;
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

public class beanPoblacionMilitar {

    private String nombrePoblacion;
    private Integer codigoPoblacion;
    private List listaPoblacion;
    private ArrayList<String> poblacion;

    @PostConstruct
    public void init() {
        mostrarPoblacion();
        poblacionComboBox();
    }

    public void insert() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (nombrePoblacion == null || nombrePoblacion.isEmpty()) {
            context.addMessage(null, new FacesMessage("Error", "Campos Requeridos Vacios"));
        } else {
            if (CRUDs.CRUDPoblacion.insert(nombrePoblacion, 1)) {
                context.addMessage(null, new FacesMessage("Registo Cargado", "Población Militar Registrada"));
                mostrarPoblacion();
                limpiar();
            } else {
                context.addMessage(null, new FacesMessage("Error al Cargar", "Población Militar No Registrada"));
            }
        }
    }

    public void update() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (nombrePoblacion == null || nombrePoblacion.isEmpty()) {
            context.addMessage(null, new FacesMessage("Error", "Campos Requeridos Vacios"));
        } else {
            if (CRUDs.CRUDPoblacion.update(codigoPoblacion, nombrePoblacion, 1)) {
                context.addMessage(null, new FacesMessage("Registo Actualizado", "Población Militar Actualizada"));
                mostrarPoblacion();
                limpiar();
            } else {
                context.addMessage(null, new FacesMessage("Error al Actualizar", "Población Militar No Actualizada"));
            }
        }
    }

    public void anular() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (codigoPoblacion == null) {
            context.addMessage(null, new FacesMessage("Error", "Población Militar No Registrada en el Sistema"));
        } else {
            if (CRUDs.CRUDPoblacion.anular(codigoPoblacion)) {
                context.addMessage(null, new FacesMessage("Registo Anulado", "Población Militar Anulada del Sistema"));
                mostrarPoblacion();
                limpiar();
            } else {
                context.addMessage(null, new FacesMessage("Error al Anular", "Población Militar No Anulada"));
            }
        }
    }

    public void mostrarPoblacion() {
        setListaPoblacion(CRUDs.CRUDPoblacion.universo());
    }

    public void poblacionComboBox() {
        poblacion = new ArrayList<>();
        List<Poblacion> lista = CRUDs.CRUDPoblacion.universo();

        if (lista != null) {
            for (Poblacion pob : lista) {
                poblacion.add(pob.getNombrePoblacion());
            }
        }
    }

    public void seleccionarPoblacion(Pojos.Poblacion poblacion) {
        setCodigoPoblacion(poblacion.getCodigoPoblacion());
        setNombrePoblacion(poblacion.getNombrePoblacion());
    }

    public void limpiar() {
        setNombrePoblacion("");
    }

    /**
     * @return the nombrePoblacion
     */
    public String getNombrePoblacion() {
        return nombrePoblacion;
    }

    /**
     * @param nombrePoblacion the nombrePoblacion to set
     */
    public void setNombrePoblacion(String nombrePoblacion) {
        this.nombrePoblacion = nombrePoblacion;
    }

    /**
     * @return the codigoPoblacion
     */
    public Integer getCodigoPoblacion() {
        return codigoPoblacion;
    }

    /**
     * @param codigoPoblacion the codigoPoblacion to set
     */
    public void setCodigoPoblacion(Integer codigoPoblacion) {
        this.codigoPoblacion = codigoPoblacion;
    }

    /**
     * @return the listaPoblacion
     */
    public List getListaPoblacion() {
        return listaPoblacion;
    }

    /**
     * @param listaPoblacion the listaPoblacion to set
     */
    public void setListaPoblacion(List listaPoblacion) {
        this.listaPoblacion = listaPoblacion;
    }

    /**
     * @return the poblacion
     */
    public ArrayList<String> getPoblacion() {
        return poblacion;
    }

    /**
     * @param poblacion the poblacion to set
     */
    public void setPoblacion(ArrayList<String> poblacion) {
        this.poblacion = poblacion;
    }

}
