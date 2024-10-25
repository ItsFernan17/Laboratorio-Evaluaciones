package Bean;

import Scope.sessionBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ferna
 */
@ManagedBean
@ViewScoped
public class beanSaludos {

    @ManagedProperty(value = "#{sessionBean}")
    private sessionBean sessionBean;

    public sessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(sessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    // Método para mostrar "¡Atención {usuario}!" o "¡Atención usuario!"
    public String getSaludoAtencion() {
        if (sessionBean != null && sessionBean.getUsuario() != null && sessionBean.getUsuario().getDpi() != null) {
            return "¡Atención " + sessionBean.getUsuario().getNombreUsuario() + "!";
        } else {
            return "¡Atención usuario!";
        }
    }

    // Método para mostrar "¡Bienvenido de vuelta {usuario}!" o "¡Bienvenido de vuelta usuario!"
    public String getSaludoBienvenida() {
        if (sessionBean != null && sessionBean.getUsuario() != null && sessionBean.getUsuario().getDpi() != null) {
            return "¡Bienvenido de vuelta " + sessionBean.getUsuario().getNombreUsuario() + "!";
        } else {
            return "¡Bienvenido de vuelta usuario!";
        }
    }
}
