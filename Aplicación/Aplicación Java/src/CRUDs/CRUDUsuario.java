/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import Pojos.DepartamentoResidencia;
import Pojos.Grado;
import Pojos.Poblacion;
import Pojos.Usuario;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CRUDUsuario {

    public static boolean insert(String dpi, String nombreCompleto, String telefono, String nombreUsuario, String rol, String contrasenia, Integer grado, Integer poblacion, Integer departamento) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));
        criteria.add(Restrictions.eq("estado", true));
        Usuario insert = (Usuario) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Usuario();
                insert.setEstado(true);
                insert.setNombreCompleto(nombreCompleto);
                insert.setTelefono(telefono);
                insert.setNombreUsuario(nombreUsuario);
                insert.setRol(rol);
                insert.setContrasenia(contrasenia);
                Grado grd = new Grado();
                grd.setCodigoGrado(grado);
                insert.setGrado(grd);
                Poblacion pob = new Poblacion();
                pob.setCodigoPoblacion(poblacion);
                insert.setPoblacion(pob);
                DepartamentoResidencia dep = new DepartamentoResidencia();
                dep.setCodigoDepartamento(departamento);
                insert.setDepartamentoResidencia(dep);
                session.save(insert);
                flag = true;
            }
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Error " + e);
        } finally {
            session.close();
        }

        return flag;
    }

    public static boolean update(String dpi, String nombreCompleto, String telefono, String nombreUsuario, String rol, String contrasenia, Integer grado, Integer poblacion, Integer departamento) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("dpi", dpi));
        Usuario update = (Usuario) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setEstado(true);
                update.setEstado(true);
                update.setNombreCompleto(nombreCompleto);
                update.setTelefono(telefono);
                update.setNombreUsuario(nombreUsuario);
                update.setRol(rol);
                update.setContrasenia(contrasenia);
                Grado grd = new Grado();
                grd.setCodigoGrado(grado);
                update.setGrado(grd);
                Poblacion pob = new Poblacion();
                pob.setCodigoPoblacion(poblacion);
                update.setPoblacion(pob);
                DepartamentoResidencia dep = new DepartamentoResidencia();
                dep.setCodigoDepartamento(departamento);
                update.setDepartamentoResidencia(dep);
                session.update(update);
                flag = true;
            }
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Error " + e);
        } finally {
            session.close();
        }

        return flag;
    }

    public static List<Usuario> universo() {
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Usuario> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.createAlias("departamentoResidencia", "dep");
            criteria.createAlias("grado", "grad");
            criteria.createAlias("poblacion", "pob");
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.asc("dpi"));
            lista = criteria.list();
        } catch (HibernateException e) {
            System.out.println("Error " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    public static boolean anular(String dpi) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("dpi", dpi));
        Usuario update = (Usuario) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setEstado(false);
                session.update(update);
                flag = true;
            }
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Error " + e);
        } finally {
            session.close();
        }

        return flag;
    }

}
