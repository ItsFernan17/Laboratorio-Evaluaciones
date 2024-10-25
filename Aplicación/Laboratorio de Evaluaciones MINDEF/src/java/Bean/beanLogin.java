/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Scope.sessionBean;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author erick
 */
@ManagedBean
@ViewScoped

public class beanLogin {

    @ManagedProperty(value = "#{sessionBean}")
    private sessionBean sessionBean = null;
    private String password = null;

    @PostConstruct
    public void init() {
        getSessionBean().setUsuario(null);
        getSessionBean().setUsuarioSession("");
        getSessionBean().setMensaje("");
    }

public String irPrincipal() throws IOException {
    FacesContext context = FacesContext.getCurrentInstance();
    
    // Verificación de campos vacíos
    if (password.equals("") || getSessionBean().getUsuarioSession().equals("")) {
        getSessionBean().setMensaje("");
        return null;
    } else {
        Pojos.Usuario usuarioSelect = CRUDs.CRUDUsuario.select(getSessionBean().getUsuarioSession());
        getSessionBean().setUsuario(usuarioSelect);
        String pass = CRUDs.CRUDLogin.sha1(password);
        if (usuarioSelect == null) {
            getSessionBean().setUsuarioSession("");
            setPassword("");
            context.addMessage(null, new FacesMessage("Error", "El Usuario no Existe"));
            return "index";
        } else if (usuarioSelect.getContrasenia().equals(pass)) {
            getSessionBean().setUsuarioSession("");           
            String rol = usuarioSelect.getRol();    
            if (rol.equalsIgnoreCase("admin")) {
                sessionBean.setTemplate("/Disenio/sistema-admin.xhtml");
                context.getExternalContext().redirect("./menu.xhtml");
            } else if (rol.equalsIgnoreCase("auxiliar")) {
                sessionBean.setTemplate("/Disenio/sistema-auxiliar.xhtml");
                context.getExternalContext().redirect("./menu.xhtml");
            } else if (rol.equalsIgnoreCase("evaluador")) {
                sessionBean.setTemplate("/Disenio/sistema-evaluador.xhtml");
                context.getExternalContext().redirect("./menu.xhtml");
            } else if (rol.equalsIgnoreCase("evaluado")) {
                sessionBean.setTemplate("/Disenio/sistema-evaluado.xhtml");
                context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/asignacion/mis-asignaciones.xhtml");
                return null;
            } else {
                context.addMessage(null, new FacesMessage("Error", "Rol de usuario no reconocido"));
                return "index";
            }
            context.addMessage(null, new FacesMessage("Éxito", "Bienvenido al Sistema"));
            return null;
        } else {
            setPassword("");
            getSessionBean().setMensaje("¡Contraseña Incorrecta!");
            context.addMessage(null, new FacesMessage("Error", "Contraseña Incorrecta"));
            return "index";
        }
    }
}


    public void cancelarLogin() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/");
        setSessionBean(null);
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

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
