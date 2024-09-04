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

@ManagedBean
@ViewScoped

public class beanDepartamento {

    private ArrayList<String> departamentos;

    @PostConstruct
    public void init() {
        mostrarDepartamentos();
    }

    public void mostrarDepartamentos() {
        departamentos = new ArrayList<>();
        List<DepartamentoResidencia> lista = CRUDs.CRUDDepartamento.universo();

        if (lista != null) {
            for (DepartamentoResidencia dep : lista) {
                departamentos.add(dep.getNombreDepartamento());
            }
        }
    }

    /**
     * @return the departamentos
     */
    public ArrayList<String> getDepartamentos() {
        return departamentos;
    }

    /**
     * @param departamentos the departamentos to set
     */
    public void setDepartamentos(ArrayList<String> departamentos) {
        this.departamentos = departamentos;
    }

}
