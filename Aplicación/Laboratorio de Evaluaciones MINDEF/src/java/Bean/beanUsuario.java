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

@ManagedBean
@ViewScoped

public class beanUsuario {

    private String dpi;
    private String nombreApellido;
    private String telefono;
    private String password;
    private String nombreUsuario;
    private String rol;
    private Integer codigo_residencia;
    private Integer codigo_comando;
    private Integer codigo_poblacion;
    private Integer codigo_grado;
    private List listDepartamentos = null;
    private List listComandos = null;
    private List listPoblaciones = null;
    private List listGrados = null;
    private List listUsuarios = null;

    @ManagedProperty(value = "#{sessionBean}")
    private sessionBean sessionBean = null;

    public sessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(sessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public static String generarNombreUsuario(String nombreApellido) {
        String[] partes = nombreApellido.split("\\s+");

        if (partes.length < 2) {
            throw new IllegalArgumentException("Debe proporcionar al menos un nombre y un apellido.");
        }
        String primeraLetraNombre = partes[0].substring(0, 1).toLowerCase();

        String primerApellido;
        if (partes.length == 2) {
            primerApellido = partes[1].toLowerCase();
        } else {
            primerApellido = partes[partes.length - 2].toLowerCase();
        }
        String nombreUsuario = primeraLetraNombre + primerApellido;

        return nombreUsuario;
    }

    @PostConstruct
    public void init() {
        comboDepartamentos();
        comboComandos();
        comboGrados();
        comboPoblacion();
        mostrar();
        comboRoles();
    }

    public void insertar() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (dpi == null || dpi.isEmpty() || nombreApellido == null || nombreApellido.isEmpty() || password == null || password.isEmpty() || telefono == null || telefono.isEmpty() || codigo_grado == null || codigo_poblacion == null || codigo_residencia == null || codigo_comando == null) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {
            boolean dpiExiste = CRUDs.CRUDUsuario.dpiExiste(dpi);
            if (dpiExiste) {
                context.addMessage(null, new FacesMessage("Error", "El DPI ya está registrado."));
                return;
            }

            try {
                nombreUsuario = generarNombreUsuario(nombreApellido);
            } catch (IllegalArgumentException e) {
                context.addMessage(null, new FacesMessage("Error", "Debe proporcionar al menos un nombre y un apellido."));
                return;
            }
            String encryptedPassword = CRUDs.CRUDLogin.encryptPassword(password);
            if (encryptedPassword == null) {
                context.addMessage(null, new FacesMessage("Error", "Error al encriptar la contraseña."));
                return;
            }

            if (CRUDs.CRUDUsuario.insert(dpi, nombreApellido, telefono, nombreUsuario, rol, encryptedPassword, codigo_grado, codigo_poblacion, codigo_residencia, codigo_comando)) {
                context.addMessage(null, new FacesMessage("Exito", "¡Usuario registrado!"));
                limpiar();
            } else {
                context.addMessage(null, new FacesMessage("Error", "¡Registro No ingresado"));
            }
        }
    }

    public void update() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (dpi == null || dpi.isEmpty() || nombreApellido == null || nombreApellido.isEmpty() || password == null || password.isEmpty() || telefono == null || telefono.isEmpty() || getCodigo_grado() == null || getCodigo_poblacion() == null || getCodigo_residencia() == null || getCodigo_comando() == null) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {
            String encryptedPassword = CRUDs.CRUDLogin.encryptPassword(password);
            if (encryptedPassword == null) {
                context.addMessage(null, new FacesMessage("Error", "Error al encriptar la contraseña."));
                return;
            }

            if (CRUDs.CRUDUsuario.update(dpi, nombreApellido, telefono, rol, encryptedPassword, codigo_grado, codigo_poblacion, getCodigo_residencia(), codigo_comando)) {
                context.addMessage(null, new FacesMessage("Exito", "¡Usuario Actualizado!"));
                mostrar();
            } else {
                context.addMessage(null, new FacesMessage("Error", "¡Registro No ingresado"));
            }
        }
    }
    
    public void anular() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (dpi == null || dpi.isEmpty()) {
            context.addMessage(null, new FacesMessage("Error", "Campos Vacios"));
        } else {
            if (CRUDs.CRUDUsuario.anular(dpi)) {
                mostrar();
                limpiar();
                context.addMessage(null, new FacesMessage("Exito", "¡Usuario Anulado!"));
            } else {
                context.addMessage(null, new FacesMessage("Error", "Registro No Anulado"));
            }
        }
    }

    public void mostrar() {
        setListUsuarios(CRUDs.CRUDUsuario.universo());
    }

    public void seleccionar(Pojos.Usuario usuario) {
        setDpi(usuario.getDpi());
        setNombreApellido(usuario.getNombreCompleto());
        setTelefono(usuario.getTelefono());
        setPassword(usuario.getContrasenia());
        setRol(usuario.getRol());
        setCodigo_residencia(usuario.getDepartamentoResidencia().getCodigoDepartamento());
        setCodigo_comando(usuario.getComando().getCodigoComando());
        setCodigo_grado(usuario.getGrado().getCodigoGrado());
        setCodigo_poblacion(usuario.getPoblacion().getCodigoPoblacion());
        setNombreUsuario(usuario.getNombreUsuario());
    }

    public void limpiar() {
        setNombreApellido("");
        setDpi("");
        setTelefono("");
        setPassword("");
        setRol("inicio");
        setCodigo_comando(0);
        setCodigo_grado(0);
        setCodigo_residencia(0);
        setCodigo_poblacion(0);

    }

    public List<SelectItem> comboDepartamentos() {
        if (listDepartamentos == null) {
            listDepartamentos = new ArrayList<>();
            List<Pojos.DepartamentoResidencia> lstResidencia = CRUDs.CRUDDepartamento.universo();
            for (Pojos.DepartamentoResidencia residencia : lstResidencia) {
                SelectItem residenciaItem = new SelectItem(residencia.getCodigoDepartamento(), residencia.getNombreDepartamento());
                listDepartamentos.add(residenciaItem);
            }
        }
        return listDepartamentos;
    }

    public List<SelectItem> comboRoles() {
        List<SelectItem> listRoles = new ArrayList<>();
        String rolUsuario = getSessionBean().getUsuario().getRol();
        
        if (rolUsuario.equalsIgnoreCase("admin")) {
            listRoles.add(new SelectItem("admin", "Administrador"));
        }
        listRoles.add(new SelectItem("evaluador", "Evaluador"));
        listRoles.add(new SelectItem("auxiliar", "Auxiliar"));
        listRoles.add(new SelectItem("evaluado", "Evaluado"));

        return listRoles;
    }    
    
    
    
    private List<SelectItem> comboComandos() {
        setListComandos(new ArrayList<SelectItem>());
        List<Pojos.Comando> lstComando = CRUDs.CRUDComando.universo();
        for (Pojos.Comando comando : lstComando) {
            SelectItem comandoItem = new SelectItem(comando.getCodigoComando(), comando.getNombreComando());
            getListComandos().add(comandoItem);
        }
        return getListComandos();
    }

    private List<SelectItem> comboPoblacion() {
        setListPoblaciones(new ArrayList<SelectItem>());
        List<Pojos.Poblacion> lstPoblacion = CRUDs.CRUDPoblacion.universo();
        for (Pojos.Poblacion poblacion : lstPoblacion) {
            SelectItem poblacionItem = new SelectItem(poblacion.getCodigoPoblacion(), poblacion.getNombrePoblacion());
            getListPoblaciones().add(poblacionItem);
        }
        return getListPoblaciones();
    }

    private List<SelectItem> comboGrados() {
        setListGrados(new ArrayList<SelectItem>());
        List<Pojos.Grado> lstGrado = CRUDs.CRUDGrado.universo();
        for (Pojos.Grado grado : lstGrado) {
            SelectItem gradoItem = new SelectItem(grado.getCodigoGrado(), grado.getNombreGrado());
            getListGrados().add(gradoItem);
        }
        return getListGrados();
    }

    /**
     * @return the dpi
     */
    public String getDpi() {
        return dpi;
    }

    /**
     * @param dpi the dpi to set
     */
    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    /**
     * @return the nombreApellido
     */
    public String getNombreApellido() {
        return nombreApellido;
    }

    /**
     * @param nombreApellido the nombreApellido to set
     */
    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
     * @return the listDepartamentos
     */
    public List getListDepartamentos() {
        return listDepartamentos;
    }

    /**
     * @param listDepartamentos the listDepartamentos to set
     */
    public void setListDepartamentos(List listDepartamentos) {
        this.listDepartamentos = listDepartamentos;
    }

    /**
     * @return the listComandos
     */
    public List getListComandos() {
        return listComandos;
    }

    /**
     * @param listComandos the listComandos to set
     */
    public void setListComandos(List listComandos) {
        this.listComandos = listComandos;
    }

    /**
     * @return the listPoblaciones
     */
    public List getListPoblaciones() {
        return listPoblaciones;
    }

    /**
     * @param listPoblaciones the listPoblaciones to set
     */
    public void setListPoblaciones(List listPoblaciones) {
        this.listPoblaciones = listPoblaciones;
    }

    /**
     * @return the listGrados
     */
    public List getListGrados() {
        return listGrados;
    }

    /**
     * @param listGrados the listGrados to set
     */
    public void setListGrados(List listGrados) {
        this.listGrados = listGrados;
    }

    /**
     * @return the codigo_residencia
     */
    public Integer getCodigo_residencia() {
        return codigo_residencia;
    }

    /**
     * @param codigo_residencia the codigo_residencia to set
     */
    public void setCodigo_residencia(Integer codigo_residencia) {
        this.codigo_residencia = codigo_residencia;
    }

    /**
     * @return the codigo_comando
     */
    public Integer getCodigo_comando() {
        return codigo_comando;
    }

    /**
     * @param codigo_comando the codigo_comando to set
     */
    public void setCodigo_comando(Integer codigo_comando) {
        this.codigo_comando = codigo_comando;
    }

    /**
     * @return the codigo_poblacion
     */
    public Integer getCodigo_poblacion() {
        return codigo_poblacion;
    }

    /**
     * @param codigo_poblacion the codigo_poblacion to set
     */
    public void setCodigo_poblacion(Integer codigo_poblacion) {
        this.codigo_poblacion = codigo_poblacion;
    }

    /**
     * @return the codigo_grado
     */
    public Integer getCodigo_grado() {
        return codigo_grado;
    }

    /**
     * @param codigo_grado the codigo_grado to set
     */
    public void setCodigo_grado(Integer codigo_grado) {
        this.codigo_grado = codigo_grado;
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
