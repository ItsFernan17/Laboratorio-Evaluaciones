/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Pojos.DepartamentoResidencia;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@ViewScoped

public class beanComboBox {


    private List listDepartamentos;
    private List listComandos;
    private List listPoblaciones;
    private List listGrados;
    private List listMotivos;

    @PostConstruct
    public void init() {
       comboDepartamentos();
       comboComandos();
       comboGrados();
       comboPoblacion();
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

    private List<SelectItem> comboMotivos() {
        setListMotivos(new ArrayList<SelectItem>());
        List<Pojos.Motivo> lstMotivo = CRUDs.CRUDMotivo.universo();
        for (Pojos.Motivo motivo : lstMotivo) {
            SelectItem motivoItem = new SelectItem(motivo.getCodigoMotivo(), motivo.getNombreMotivo());
            getListMotivos().add(motivoItem);
        }
        return getListMotivos();
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
     * @return the listMotivos
     */
    public List getListMotivos() {
        return listMotivos;
    }

    /**
     * @param listMotivos the listMotivos to set
     */
    public void setListMotivos(List listMotivos) {
        this.listMotivos = listMotivos;
    }
    

}
