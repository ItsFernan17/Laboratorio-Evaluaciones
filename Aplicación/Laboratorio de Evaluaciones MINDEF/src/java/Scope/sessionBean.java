package Scope;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ferna
 */
@ManagedBean
@SessionScoped

public class sessionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pojos.Usuario usuario = null;
    private String usuarioSession = "";
    private String mensaje = null;
    private String template = "/Disenio/sistema-admin.xhtml";

    // Getters y Setters

    public Pojos.Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Pojos.Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(String usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
