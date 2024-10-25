package CRUDs;

import Pojos.Asignacion;
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

public class CRUDAsignacion {

    public static boolean insert(String codigoEvaluado, Integer codigoExamen, String usuarioIngreso) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Asignacion insert = new Asignacion();
            insert.setEstado(true);
            Usuario user = new Usuario();
            user.setDpi(codigoEvaluado);
            insert.setUsuarioByEvaluado(user);
            Examen examen = new Examen();
            examen.setCodigoExamen(codigoExamen);
            insert.setExamen(examen);
            Usuario usuario = new Usuario();
            usuario.setDpi(usuarioIngreso);
            insert.setUsuarioByUsuarioIngreso(usuario);
            insert.setFechaIngreso(fecha);
            session.save(insert);

            flag = true;
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error " + e);
        } finally {
            session.close();
        }

        return flag;
    }

    public static boolean verificarAsignacionExistente(String codigoEvaluado, Integer codigoExamen) {
        boolean existe = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(Asignacion.class);
            criteria.createAlias("usuarioByEvaluado", "usuario");
            criteria.createAlias("examen", "examen");
            criteria.add(Restrictions.eq("usuario.dpi", codigoEvaluado));
            criteria.add(Restrictions.eq("examen.codigoExamen", codigoExamen));
            criteria.add(Restrictions.eq("estado", true));
            Asignacion asignacionExistente = (Asignacion) criteria.uniqueResult();
            if (asignacionExistente != null) {
                existe = true;
            }

        } catch (HibernateException e) {
            System.out.println("Error al verificar la asignación existente: " + e);
        } finally {
            session.close();
        }

        return existe;
    }

    public static boolean update(Integer codigoAsignacion, String codigoEvaluado, Integer codigoExamen, String usuarioModifica) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Asignacion.class);
        criteria.add(Restrictions.eq("codigoAsignacion", codigoAsignacion));
        Asignacion update = (Asignacion) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                Usuario evaluado = new Usuario();
                evaluado.setDpi(codigoEvaluado);
                update.setUsuarioByEvaluado(evaluado);
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

    public static List<Asignacion> universo() {
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Asignacion> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Asignacion.class);
            criteria.createAlias("examen", "ex");
            criteria.createAlias("usuarioByEvaluado", "us");
            criteria.createAlias("ex.motivo", "mot");
            criteria.createAlias("ex.empleo", "emp");            
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.asc("codigoAsignacion"));
            lista = criteria.list();
        } catch (HibernateException e) {
            System.out.println("Error " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    } 

    public static List<Asignacion> asignacionPorEvaluado(String dpiEvaluado) {
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Asignacion> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Asignacion.class);
            criteria.createAlias("examen", "ex");
            criteria.createAlias("usuarioByEvaluado", "us");
            criteria.createAlias("ex.motivo", "mot");
            criteria.createAlias("ex.empleo", "emp");
            criteria.add(Restrictions.eq("us.dpi", dpiEvaluado));
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.asc("codigoAsignacion"));
            lista = criteria.list();

        } catch (HibernateException e) {
            System.out.println("Error " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }
    
    
    public static boolean anular(Integer codigoAsignacion) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Asignacion.class);
        criteria.add(Restrictions.eq("codigoAsignacion", codigoAsignacion));
        Asignacion update = (Asignacion) criteria.uniqueResult();
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
