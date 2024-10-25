package Pojos;
// Generated Oct 24, 2024 12:41:15 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Grado generated by hbm2java
 */
public class Grado  implements java.io.Serializable {


     private Integer codigoGrado;
     private Boolean estado;
     private String nombreGrado;
     private Set usuarios = new HashSet(0);

    public Grado() {
    }

    public Grado(Boolean estado, String nombreGrado, Set usuarios) {
       this.estado = estado;
       this.nombreGrado = nombreGrado;
       this.usuarios = usuarios;
    }
   
    public Integer getCodigoGrado() {
        return this.codigoGrado;
    }
    
    public void setCodigoGrado(Integer codigoGrado) {
        this.codigoGrado = codigoGrado;
    }
    public Boolean getEstado() {
        return this.estado;
    }
    
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    public String getNombreGrado() {
        return this.nombreGrado;
    }
    
    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }
    public Set getUsuarios() {
        return this.usuarios;
    }
    
    public void setUsuarios(Set usuarios) {
        this.usuarios = usuarios;
    }




}


