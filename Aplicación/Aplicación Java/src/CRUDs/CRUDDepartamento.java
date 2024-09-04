/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import Pojos.DepartamentoResidencia;
import Pojos.Usuario;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CRUDDepartamento {

    public static boolean insert(String nombreDepartamento, Integer usuarioIngreso) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DepartamentoResidencia.class);
        criteria.add(Restrictions.eq("nombreDepartamento", nombreDepartamento));
        criteria.add(Restrictions.eq("estado", true));
        DepartamentoResidencia insert = (DepartamentoResidencia) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new DepartamentoResidencia();
                insert.setEstado(true);
                insert.setNombreDepartamento(nombreDepartamento);
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

    public static boolean update(Integer codigoDepartamento, String nombreDepartamento, Integer usuarioModifica) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DepartamentoResidencia.class);
        criteria.add(Restrictions.eq("codigoDepartamento", codigoDepartamento));
        DepartamentoResidencia update = (DepartamentoResidencia) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setEstado(true);
                update.setNombreDepartamento(nombreDepartamento);
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

    public static List<DepartamentoResidencia> universo() {
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<DepartamentoResidencia> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(DepartamentoResidencia.class);
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.asc("codigoDepartamento"));
            lista = criteria.list();
        } catch (HibernateException e) {
            System.out.println("Error " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    public static boolean anular(Integer codigoDepartamento) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DepartamentoResidencia.class);
        criteria.add(Restrictions.eq("codigoDepartamento", codigoDepartamento));
        DepartamentoResidencia update = (DepartamentoResidencia) criteria.uniqueResult();
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
