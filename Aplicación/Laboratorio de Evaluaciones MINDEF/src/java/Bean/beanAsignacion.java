/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Scope.sessionBean;
import java.util.ArrayList;
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

public class beanAsignacion {

    private String evaluado;
    private Integer codigoExamen;
    private Integer codigoAsignacion;
    private List listEvaluado = null;
    private List listExamen = null;
    private List listAsignaciones = null;

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
        comboEvaluados();
        comboExamen();
        mostrar();
    }

    public void insertar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (evaluado == null || codigoExamen == null) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacíos"));
        } else {
            if (CRUDs.CRUDAsignacion.verificarAsignacionExistente(evaluado, codigoExamen)) {
                context.addMessage(null, new FacesMessage("Error", "Examen ya asignado al usuario"));
            } else {
                if (CRUDs.CRUDAsignacion.insert(evaluado, codigoExamen, getSessionBean().getUsuario().getDpi())) {
                    limpiar();
                    recargarExamenes();
                    context.addMessage(null, new FacesMessage("Éxito", "¡Asignación registrada!"));
                } else {
                    context.addMessage(null, new FacesMessage("Error", "Registro no ingresado"));
                }
            }
        }
    }

    public void update() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (codigoAsignacion == null || evaluado == null || codigoExamen == null) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {
            if (CRUDs.CRUDAsignacion.verificarAsignacionExistente(evaluado, codigoExamen)) {
                context.addMessage(null, new FacesMessage("Error", "Examen ya asignado al usuario"));
            } else {

                if (CRUDs.CRUDAsignacion.update(codigoAsignacion, evaluado, codigoExamen, getSessionBean().getUsuario().getDpi())) {
                    context.addMessage(null, new FacesMessage("Exito", "¡Asignación Actualizada!"));
                    mostrar();
                } else {
                    context.addMessage(null, new FacesMessage("Error", "¡Registro No actualizado"));
                }
            }

        }
    }

    public void anular() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (codigoAsignacion == null) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {
            if (CRUDs.CRUDAsignacion.anular(codigoAsignacion)) {
                mostrar();
                limpiar();
                context.addMessage(null, new FacesMessage("Exito", "¡Asignación Anulada!"));
            } else {
                context.addMessage(null, new FacesMessage("Error", "Registro No Anulado"));
            }
        }
    }

    public void mostrar() {
        String rol = getSessionBean().getUsuario().getRol();

        if (rol != null && rol.equalsIgnoreCase("evaluado")) {
            setListAsignaciones(CRUDs.CRUDAsignacion.asignacionPorEvaluado(getSessionBean().getUsuario().getDpi()));
        } else {
            setListAsignaciones(CRUDs.CRUDAsignacion.universo());
        }
    }

    public void seleccionar(Pojos.Asignacion asignacion) {
        setCodigoExamen(asignacion.getExamen().getCodigoExamen());
        setEvaluado(asignacion.getUsuarioByEvaluado().getDpi());
        setCodigoAsignacion(asignacion.getCodigoAsignacion());
    }

    public List<SelectItem> comboEvaluados() {
        setListEvaluado(new ArrayList<SelectItem>());
        List<Pojos.Usuario> lstUsuario = CRUDs.CRUDUsuario.universo();

        for (Pojos.Usuario usuario : lstUsuario) {
            if (usuario.getRol().equals("evaluado")) {
                SelectItem evaluadoItem = new SelectItem(usuario.getDpi(), usuario.getNombreCompleto());
                getListEvaluado().add(evaluadoItem);
            }
        }

        return getListEvaluado();
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
        listExamen = null;
        listEvaluado = null;
        comboExamen();
        comboEvaluados();
    }

    public void limpiar() {
        setCodigoExamen(0);
        setEvaluado("0");
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
     * @return the listEvaluado
     */
    public List getListEvaluado() {
        return listEvaluado;
    }

    /**
     * @param listEvaluado the listEvaluado to set
     */
    public void setListEvaluado(List listEvaluado) {
        this.listEvaluado = listEvaluado;
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
     * @return the listAsignaciones
     */
    public List getListAsignaciones() {
        return listAsignaciones;
    }

    /**
     * @param listAsignaciones the listAsignaciones to set
     */
    public void setListAsignaciones(List listAsignaciones) {
        this.listAsignaciones = listAsignaciones;
    }

    /**
     * @return the codigoAsignacion
     */
    public Integer getCodigoAsignacion() {
        return codigoAsignacion;
    }

    /**
     * @param codigoAsignacion the codigoAsignacion to set
     */
    public void setCodigoAsignacion(Integer codigoAsignacion) {
        this.codigoAsignacion = codigoAsignacion;
    }

}
