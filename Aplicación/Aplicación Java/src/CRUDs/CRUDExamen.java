/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import Pojos.Empleo;
import Pojos.Examen;
import Pojos.Motivo;
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
 * @author erick
 */
public class CRUDExamen {

    public static boolean insert(Date fechaEvaluacion, Integer codigoMotivo, String ceom, Integer punteoMaximo, String usuarioIngreso) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Examen nuevoExamen = new Examen();
            nuevoExamen.setEstado(true);
            nuevoExamen.setFechaEvaluacion(fechaEvaluacion);
            Motivo motivo = new Motivo();
            motivo.setCodigoMotivo(codigoMotivo);
            nuevoExamen.setMotivo(motivo);
            Empleo empleo = new Empleo();
            empleo.setCeom(ceom);
            nuevoExamen.setEmpleo(empleo);
            nuevoExamen.setPunteoMaximo(punteoMaximo);
            Usuario usuario = new Usuario();
            usuario.setDpi(usuarioIngreso);
            nuevoExamen.setUsuarioByUsuarioIngreso(usuario);
            nuevoExamen.setFechaIngreso(fecha);
            session.save(nuevoExamen);
            transaction.commit();
            flag = true;

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error: " + e);
        } finally {
            session.close();
        }

        return flag;
    }

    public static boolean update(Integer codigoExamen, Date fechaEvaluacion, Integer codigoMotivo, String ceom, Integer punteoMaximo, String usuarioModifica) {
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
                Motivo motivo = new Motivo();
                motivo.setCodigoMotivo(codigoMotivo);
                update.setMotivo(motivo);
                Empleo empleo = new Empleo();
                empleo.setCeom(ceom);
                update.setEmpleo(empleo);
                update.setPunteoMaximo(punteoMaximo);
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
            criteria.createAlias("motivo", "mot");
            criteria.createAlias("empleo", "emp");            
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
