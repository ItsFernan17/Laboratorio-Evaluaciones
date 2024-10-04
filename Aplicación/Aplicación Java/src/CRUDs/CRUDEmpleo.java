/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import Pojos.Empleo;
import Pojos.Usuario;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CRUDEmpleo {

    public static boolean insert(String ceom, String descripcion, String usuarioIngreso) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Empleo.class);
        criteria.add(Restrictions.eq("descripcion", descripcion));
        criteria.add(Restrictions.eq("estado", true));
        Empleo insert = (Empleo) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Empleo();
                insert.setCeom(ceom);
                insert.setEstado(true);
                insert.setDescripcion(descripcion);
                Usuario usuario = new Usuario();
                usuario.setDpi(usuarioIngreso);
                insert.setUsuarioByUsuarioIngreso(usuario);
                insert.setFechaIngreso(fecha);
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

    public static boolean update(String ceom, String descripcion, String usuarioModifica) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Empleo.class);
        criteria.add(Restrictions.eq("ceom", ceom));
        Empleo update = (Empleo) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setEstado(true);
                update.setCeom(ceom);
                update.setEstado(true);
                update.setDescripcion(descripcion);
                Usuario usuario = new Usuario();
                usuario.setDpi(usuarioModifica);
                update.setUsuarioByUsuarioModifica(usuario);
                update.setFechaIngreso(fecha);
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

    public static List<Empleo> universo() {
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Empleo> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Empleo.class);
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.asc("ceom"));
            lista = criteria.list();
        } catch (HibernateException e) {
            System.out.println("Error " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    public static boolean anular(String ceom) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Empleo.class);
        criteria.add(Restrictions.eq("ceom", ceom));
        Empleo update = (Empleo) criteria.uniqueResult();
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
