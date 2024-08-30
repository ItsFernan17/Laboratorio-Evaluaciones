/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import Pojos.MotivoEvaluacion;
import Pojos.Usuario;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CRUDMotivoEvaluacion {

    public static boolean insert(Boolean estado, String nombreMotivo, Integer usuarioIngreso) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(MotivoEvaluacion.class);
        criteria.add(Restrictions.eq("nombreMotivo", nombreMotivo));
        criteria.add(Restrictions.eq("estado", true));
        MotivoEvaluacion insert = (MotivoEvaluacion) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new MotivoEvaluacion();
                insert.setEstado(estado);
                insert.setNombreMotivo(nombreMotivo);
                Usuario usuario = new Usuario();
                usuario.setCodigoUsuario(usuarioIngreso);
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

    public static boolean update(Integer codigoMotivo, Boolean estado, String nombreMotivo, Integer usuarioModifica) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(MotivoEvaluacion.class);
        criteria.add(Restrictions.eq("codigoMotivo", codigoMotivo));
        MotivoEvaluacion update = (MotivoEvaluacion) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setEstado(estado);
                update.setNombreMotivo(nombreMotivo);
                Usuario usuario = new Usuario();
                usuario.setCodigoUsuario(usuarioModifica);
                update.setUsuarioByUsuarioModifica(usuario);
                update.setFechaModifica(fecha);
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

    public static List<MotivoEvaluacion> universo() {
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<MotivoEvaluacion> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(MotivoEvaluacion.class);
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.asc("codigoMotivo"));
            lista = criteria.list();
        } catch (HibernateException e) {
            System.out.println("Error " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    public static boolean anular(Integer codigoMotivo) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(MotivoEvaluacion.class);
        criteria.add(Restrictions.eq("codigoMotivo", codigoMotivo));
        MotivoEvaluacion update = (MotivoEvaluacion) criteria.uniqueResult();
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
