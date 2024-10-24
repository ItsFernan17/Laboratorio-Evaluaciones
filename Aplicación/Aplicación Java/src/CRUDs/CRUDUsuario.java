/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import HibernetUtil.HibernateUtil;
import Pojos.Comando;
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

    public static boolean insert(String dpi, String nombreCompleto, String telefono, String nombreUsuario, String rol, String contrasenia, Integer grado, Integer poblacion, Integer departamento, Integer comando) {
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
                insert.setDpi(dpi);
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
                Comando com = new Comando();
                com.setCodigoComando(comando);
                insert.setComando(com);
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

    public static boolean update(String dpi, String nombreCompleto, String telefono, String rol, String contrasenia, Integer grado, Integer poblacion, Integer departamento, Integer comando) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("dpi", dpi));
        Usuario update = (Usuario) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setNombreCompleto(nombreCompleto);
                update.setTelefono(telefono);
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
                Comando com = new Comando();
                com.setCodigoComando(comando);
                update.setComando(com);
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
            criteria.createAlias("comando", "com");
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

    public static Usuario select(String nombreUsuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Usuario select = null;
        String nombre = null;

        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));
            criteria.add(Restrictions.eq("estado", true));
            select = (Usuario) criteria.uniqueResult();

            if (select != null) {
                nombre = select.getNombreUsuario();
            }

        } catch (HibernateException e) {
            System.out.println("Error" + e);
        } finally {
            session.close();
        }

        return select;
    }
    
        public static boolean dpiExiste(String dpi) {
        boolean flag = false;
        Session session = null;

        try {
            session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("dpi", dpi));

            Usuario usuario = (Usuario) criteria.uniqueResult();

            if (usuario != null) {
                flag = true;
            }

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return flag;
    }

}
