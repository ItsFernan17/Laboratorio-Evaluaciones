/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Scope.sessionBean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped

public class beanEmpleo {

    private String ceom;
    private String descripcion;
    private List listaEmpleos;   
    
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
        mostrar();
    }

    public void insertar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (ceom == null || ceom.isEmpty() || descripcion == null || descripcion.isEmpty()) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {
            if (CRUDs.CRUDEmpleo.insert(ceom, descripcion, getSessionBean().getUsuario().getDpi())) {
                limpiar();
                context.addMessage(null, new FacesMessage("Exito", "¡Empleo registrado!"));
            } else {
                context.addMessage(null, new FacesMessage("Error", "Registro No ingresado"));
            }
        }
    }

    public void update() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (ceom == null || ceom.isEmpty() || descripcion == null || descripcion.isEmpty()) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {

            if (CRUDs.CRUDEmpleo.update(ceom, descripcion, getSessionBean().getUsuario().getDpi())) {
                context.addMessage(null, new FacesMessage("Exito", "¡Empleo Actualizado!"));
                mostrar();
            } else {
                context.addMessage(null, new FacesMessage("Error", "¡Registro No actualizado"));
            }
        }
    }

    public void anular() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (ceom == null || ceom.isEmpty()) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {
            if (CRUDs.CRUDEmpleo.anular(ceom)) {
                mostrar();
                limpiar();
                context.addMessage(null, new FacesMessage("Exito", "¡Empleo Anulado!"));
            } else {
                context.addMessage(null, new FacesMessage("Error", "Registro No Anulado"));
            }
        }
    }

    public void mostrar() {
        setListaEmpleos(CRUDs.CRUDEmpleo.universo());
    }

    public void seleccionar(Pojos.Empleo empleo) {
        setCeom(empleo.getCeom());
        setDescripcion(empleo.getDescripcion());

    }

    public void limpiar() {
        setCeom("");
        setDescripcion("");
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the listaEmpleos
     */
    public List getListaEmpleos() {
        return listaEmpleos;
    }

    /**
     * @param listaEmpleos the listaEmpleos to set
     */
    public void setListaEmpleos(List listaEmpleos) {
        this.listaEmpleos = listaEmpleos;
    }

}
