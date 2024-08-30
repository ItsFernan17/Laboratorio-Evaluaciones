/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laboratorio.evaluaciones;

public class LaboratorioEvaluaciones {

    public static void main(String[] args) {

        //Inserción   
        CRUDs.CRUDUsuario.insert(true, "UMG Sistemas", "Administrador", "admin123");

        //Actualizar
        CRUDs.CRUDUsuario.update(1, true, "GT", "Administrador", "gt123");

        //Anula
        CRUDs.CRUDUsuario.anular(1);

        //Universo
        for (int i = 0; i < CRUDs.CRUDUsuario.universo().size(); i++) {
            System.out.println("CODIGO USUARIO: " + CRUDs.CRUDUsuario.universo().get(i).getCodigoUsuario());
            System.out.println("USUARIO: " + CRUDs.CRUDUsuario.universo().get(i).getNombreUsuario());
            System.out.println("ROL: " + CRUDs.CRUDUsuario.universo().get(i).getRol());
            System.out.println("CONSTRASEÑA: " + CRUDs.CRUDUsuario.universo().get(i).getContrasenia());
        }
    }

}
