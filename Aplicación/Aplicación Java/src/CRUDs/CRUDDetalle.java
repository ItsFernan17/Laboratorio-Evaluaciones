/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import Pojos.DetalleExamen;
import Pojos.Examen;
import Pojos.Pregunta;
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
public class CRUDDetalle {

    public static boolean insert(Integer codigoExamen, Integer codigoPregunta, String respuestaDada, String usuarioIngreso) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DetalleExamen.class);
        criteria.add(Restrictions.eq("estado", true));
        DetalleExamen insert = (DetalleExamen) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new DetalleExamen();
                insert.setEstado(true);
                Examen examen = new Examen();
                examen.setCodigoExamen(codigoExamen);
                insert.setExamen(examen);
                Pregunta pregunta = new Pregunta();
                pregunta.setCodigoPregunta(codigoPregunta);
                insert.setRespuestaDada(respuestaDada);
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

    public static boolean update(Integer codigoDetalle, Integer codigoExamen, Integer codigoPregunta, String respuestaDada, String usuarioModifica) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DetalleExamen.class);
        criteria.add(Restrictions.eq("codigoDetalle", codigoDetalle));
        DetalleExamen update = (DetalleExamen) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setEstado(true);
                Examen examen = new Examen();
                examen.setCodigoExamen(codigoExamen);
                update.setExamen(examen);
                Pregunta pregunta = new Pregunta();
                pregunta.setCodigoPregunta(codigoPregunta);
                update.setRespuestaDada(respuestaDada);
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

    public static List<DetalleExamen> universo() {
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<DetalleExamen> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(DetalleExamen.class);
            criteria.createAlias("examen", "ex");
            criteria.createAlias("pregunta", "pre");
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.asc("codigoDetalle"));
            lista = criteria.list();
        } catch (HibernateException e) {
            System.out.println("Error " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    public static boolean anular(Integer codigoDetalle) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DetalleExamen.class);
        criteria.add(Restrictions.eq("codigoDetalle", codigoDetalle));
        DetalleExamen update = (DetalleExamen) criteria.uniqueResult();
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
