/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import Pojos.Certificacion;
import Pojos.Comando;
import Pojos.Examen;
import Pojos.Usuario;
import Pojos.MotivoEvaluacion;
import Pojos.MotivoNoApto;
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
public class CRUDCertificacion {

    public static boolean insert(String resultadoFinal, Integer codigoComando, Integer codigoMotivo, Integer codigoNoMotivo, Integer codigoExamen, String usuarioIngreso) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Certificacion.class);
        criteria.add(Restrictions.eq("estado", true));
        Certificacion insert = (Certificacion) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Certificacion();
                insert.setEstado(true);
                insert.setResultadoFinal(resultadoFinal);
                Comando comando = new Comando();
                comando.setCodigoComando(codigoComando);
                insert.setComando(comando);
                MotivoEvaluacion motivo = new MotivoEvaluacion();
                motivo.setCodigoMotivo(codigoMotivo);
                insert.setMotivoEvaluacion(motivo);
                MotivoNoApto motivoNo = new MotivoNoApto();
                motivoNo.setCodigoNoApto(codigoNoMotivo);
                insert.setMotivoNoApto(motivoNo);
                Examen examen = new Examen();
                examen.setCodigoExamen(codigoExamen);
                insert.setExamen(examen);
                Usuario usuario = new Usuario();
                usuario.setDpi(usuarioIngreso);
                insert.setUsuarioByUsarioIngresa(usuario);
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

    public static boolean update(Integer numeroCertificacion, String resultadoFinal, Integer codigoComando, Integer codigoMotivo, Integer codigoNoMotivo, Integer codigoExamen, String usuarioModifica) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Certificacion.class);
        criteria.add(Restrictions.eq("numeroCertificacion", numeroCertificacion));
        Certificacion update = (Certificacion) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setEstado(true);
                update.setEstado(true);
                update.setResultadoFinal(resultadoFinal);
                Comando comando = new Comando();
                comando.setCodigoComando(codigoComando);
                update.setComando(comando);
                MotivoEvaluacion motivo = new MotivoEvaluacion();
                motivo.setCodigoMotivo(codigoMotivo);
                update.setMotivoEvaluacion(motivo);
                MotivoNoApto motivoNo = new MotivoNoApto();
                motivoNo.setCodigoNoApto(codigoNoMotivo);
                update.setMotivoNoApto(motivoNo);
                Examen examen = new Examen();
                examen.setCodigoExamen(codigoExamen);
                update.setExamen(examen);
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

    public static List<Certificacion> universo() {
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Certificacion> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Certificacion.class);
            criteria.createAlias("comando", "co");
            criteria.createAlias("examen", "ex");
            criteria.createAlias("motivoEvaluacion", "mo");
            criteria.createAlias("motivoNoApto", "mon");
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.asc("numeroCertificacion"));
            lista = criteria.list();
        } catch (HibernateException e) {
            System.out.println("Error " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    public static boolean anular(Integer numeroCertificacion) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Certificacion.class);
        criteria.add(Restrictions.eq("numeroCertificacion", numeroCertificacion));
        Certificacion update = (Certificacion) criteria.uniqueResult();
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
