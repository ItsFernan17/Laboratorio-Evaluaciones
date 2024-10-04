/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import Pojos.Empleo;
import Pojos.Examen;
import Pojos.Usuario;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ferna
 */
public class CRUDExamen {

    public static boolean insert(Date fechaEvaluacion, String usuarioEvaluado, String empelo, Integer punteoTotal, String usuarioIngreso) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Examen.class);
        criteria.add(Restrictions.eq("estado", true));
        Examen insert = (Examen) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Examen();
                insert.setEstado(true);
                insert.setFechaEvaluacion(fechaEvaluacion);
                Usuario user = new Usuario();
                user.setDpi(usuarioEvaluado);
                insert.setUsuarioByUsuario(user);
                Empleo empleo = new Empleo();
                empleo.setCeom(empelo);
                insert.setPunteoTotal(punteoTotal);
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

    public static boolean update(Integer codigoExamen, Date fechaEvaluacion, String usuarioEvaluado, String empelo, Integer punteoTotal, String usuarioModifica) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Examen.class);
        criteria.add(Restrictions.eq("codigoExamen", codigoExamen));
        Examen update = (Examen) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setEstado(true);
                update.setFechaEvaluacion(fechaEvaluacion);
                Usuario user = new Usuario();
                user.setDpi(usuarioEvaluado);
                update.setUsuarioByUsuario(user);
                Empleo empleo = new Empleo();
                empleo.setCeom(empelo);
                update.setPunteoTotal(punteoTotal);
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

    public static List<Examen> universo() {
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Examen> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Examen.class);
            criteria.createAlias("empleo", "emp");
            criteria.createAlias("usuarioByUsuario", "usuario");
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.asc("codigoExamen"));
            lista = criteria.list();
        } catch (HibernateException e) {
            System.out.println("Error " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    public static boolean anular(Integer codigoExamen) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Examen.class);
        criteria.add(Restrictions.eq("codigoExamen", codigoExamen));
        Examen update = (Examen) criteria.uniqueResult();
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
