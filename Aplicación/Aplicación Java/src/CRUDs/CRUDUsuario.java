/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import Pojos.Usuario;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CRUDUsuario {

    public static boolean insert(Boolean estado, String nombreUsuario, String rol, String contrasenia) {
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
                insert.setEstado(estado);
                insert.setNombreUsuario(nombreUsuario);
                insert.setRol(rol);
                insert.setContrasenia(contrasenia);
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

    public static boolean update(Integer codigoUsuario, Boolean estado, String nombreUsuario, String rol, String contrasenia) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("codigoUsuario", codigoUsuario));
        Usuario update = (Usuario) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setEstado(estado);
                update.setNombreUsuario(nombreUsuario);
                update.setRol(rol);
                update.setContrasenia(contrasenia);
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
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.asc("codigoUsuario"));
            lista = criteria.list();
        } catch (HibernateException e) {
            System.out.println("Error " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    public static boolean anular(Integer codigoUsuario) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("codigoUsuario", codigoUsuario));
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
