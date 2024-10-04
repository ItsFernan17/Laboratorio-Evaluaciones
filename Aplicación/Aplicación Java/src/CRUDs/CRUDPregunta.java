/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import Pojos.BancoRespuestas;
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
public class CRUDPregunta {

    public static boolean insert(String enunciado, Integer codigoRespuesta, String usuarioIngreso) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Pregunta.class);
        criteria.add(Restrictions.eq("enunciado", enunciado));
        criteria.add(Restrictions.eq("estado", true));
        Pregunta insert = (Pregunta) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Pregunta();
                insert.setEstado(true);
                insert.setEnunciado(enunciado);
                BancoRespuestas res = new BancoRespuestas();
                res.setCodigoRespuesta(codigoRespuesta);
                insert.setBancoRespuestas(res);
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

    public static boolean update(Integer codigoPregunta, String enunciado, Integer codigoRespuesta, String usuarioModifica) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Pregunta.class);
        criteria.add(Restrictions.eq("codigoPregunta", codigoPregunta));
        Pregunta update = (Pregunta) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setEstado(true);
                update.setEnunciado(enunciado);
                BancoRespuestas res = new BancoRespuestas();
                res.setCodigoRespuesta(codigoRespuesta);
                update.setBancoRespuestas(res);
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

    public static List<Pregunta> universo() {
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Pregunta> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Pregunta.class);
            criteria.createAlias("bancoRespuestas", "resp");
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.asc("codigoPregunta"));
            lista = criteria.list();
        } catch (HibernateException e) {
            System.out.println("Error " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    public static boolean anular(Integer codigoPregunta) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Pregunta.class);
        criteria.add(Restrictions.eq("codigoPregunta", codigoPregunta));
        Pregunta update = (Pregunta) criteria.uniqueResult();
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
