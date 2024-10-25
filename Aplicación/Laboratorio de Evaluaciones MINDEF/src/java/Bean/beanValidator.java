/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Pojos.Usuario;
import Scope.sessionBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped

public class beanValidator {

    @ManagedProperty(value = "#{sessionBean}")

    private sessionBean sessionBean = null;

    public void verificarSesion() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Usuario user = getSessionBean().getUsuario();
            if ((user == null) || (getSessionBean() == null)) {
                context.getExternalContext().redirect("/Laboratorio_de_Evaluaciones_MINDEF");
            }
        } catch (Exception e) {

        }
    }

    /**
     * @return the sessionBean
     */
    public sessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(sessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

}
