package Bean;

import CRUDs.CRUDUsuario;
import Pojos.Usuario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class BeanUsuarios {

    private Integer codigoUsuario;
    private Boolean estado;
    private String nombreUsuario;
    private String rol;
    private String contrasenia;
    private List listUsuarios;

    @PostConstruct // se ejecuta este metodo, al aperturarse la ventana
    public void mostrar() {
        setListUsuarios(CRUDs.CRUDUsuario.universo()); // llamada del metodo universon del Crud
    }

    public void insertar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (nombreUsuario.equals("") || "Selecciona un rol".equals(rol) || contrasenia.equals("")) {
            context.addMessage(null, new FacesMessage("Datos", "Debe llenar todos los campos"));
        } else {
            if (CRUDUsuario.insert(getNombreUsuario(), getRol(), getContrasenia())) { // llamada del metodo insert del crud
                this.limpiar();
                context.addMessage(null, new FacesMessage("Exito", "Registro Ingresado"));
                // Actualizar la lista de centros después de la inserción
                setListUsuarios(CRUDs.CRUDUsuario.universo());
            } else {
                context.addMessage(null, new FacesMessage("Error", "Informacion no se guardo"));
            }
        }
    }

    public void limpiar() {
        setNombreUsuario("");
        setRol("");
        setContrasenia("");
    }

    public void seleccionar(Pojos.Usuario usuario) { // el pojo usuarios ya tiene el universo que devuelve una lista
        setCodigoUsuario(usuario.getCodigoUsuario());
        setNombreUsuario(usuario.getNombreUsuario());
        setRol(usuario.getRol());
        setContrasenia(usuario.getContrasenia());
    }
    
       public void update() { // similar al insertar, contructor se agrega ID, quitar estado y llamar crud update
        FacesContext context = FacesContext.getCurrentInstance();

        if (nombreUsuario == null || rol == null || contrasenia == null) {
            context.addMessage(null, new FacesMessage("Error", "Debe ingresar los datos"));
        } else {
            if (CRUDUsuario.update(getCodigoUsuario(), getNombreUsuario(), getRol(), getContrasenia())) { // llamada del metodo insert del crud
                limpiar();
                context.addMessage(null, new FacesMessage("Exito", "Registro actualizado"));
                // Actualizar la lista de centros después de la inserción
                setListUsuarios(CRUDs.CRUDUsuario.universo());
            } else {
                context.addMessage(null, new FacesMessage("Error", "Informacion no actualizado"));
            }
        }
    }
    
        public void anular() { // similar al insertar, contructor se agrega ID, quitar estado y llamar crud update
        FacesContext context = FacesContext.getCurrentInstance();

        if (codigoUsuario == null) {
            context.addMessage(null, new FacesMessage("Error", "Debe ingresar los datos"));
        } else {
            if (CRUDs.CRUDUsuario.anular(codigoUsuario)){ // llamada del metodo anular del crud
                limpiar();
                context.addMessage(null, new FacesMessage("Exito", "Registro eliminado"));
                // Actualizar la lista de centros después de la inserción
                setListUsuarios(CRUDUsuario.universo());
            } else {
                context.addMessage(null, new FacesMessage("Error", "Registro no eliminado"));
            }
        }
    }

    /**
     * @return the codigoUsuario
     */
    public Integer getCodigoUsuario() {
        return codigoUsuario;
    }

    /**
     * @param codigoUsuario the codigoUsuario to set
     */
    public void setCodigoUsuario(Integer codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    /**
     * @return the estado
     */
    public Boolean getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the listUsuarios
     */
    public List getListUsuarios() {
        return listUsuarios;
    }

    /**
     * @param listUsuarios the listUsuarios to set
     */
    public void setListUsuarios(List listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

}
