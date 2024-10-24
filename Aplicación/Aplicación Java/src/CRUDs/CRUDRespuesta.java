package CRUDs;

import Pojos.Pregunta;
import Pojos.Respuesta;
import Pojos.Usuario;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author escobo
 */
public class CRUDRespuesta {

    public static boolean insert(String respuesta, Boolean esCorrecta, Integer codigoPregunta, String usuarioIngreso) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            System.out.println("Insertando respuesta: " + respuesta + " | Correcta: " + esCorrecta + " | Codigo Pregunta: " + codigoPregunta);
            Respuesta nuevaRespuesta = new Respuesta();
            nuevaRespuesta.setEstado(true);
            nuevaRespuesta.setRespuesta(respuesta);
            nuevaRespuesta.setEscorrecta(esCorrecta);
            Pregunta pregunta = new Pregunta();
            pregunta.setCodigoPregunta(codigoPregunta);
            nuevaRespuesta.setPregunta(pregunta);
            Usuario usuario = new Usuario();
            usuario.setDpi(usuarioIngreso);
            nuevaRespuesta.setUsuarioByUsuarioIngreso(usuario);
            nuevaRespuesta.setFechaIngreso(fecha);
            session.save(nuevaRespuesta);

            transaction.commit();
            flag = true;

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error al insertar la respuesta: " + e.getMessage());
            e.printStackTrace();

        } finally {
            session.close();
        }

        return flag;
    }

    public static boolean preguntaTieneRespuestas(Integer codigoPregunta) {
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Long countRespuestas = 0L;

        try {
            Criteria criteria = session.createCriteria(Respuesta.class);
            criteria.createAlias("pregunta", "p");
            criteria.add(Restrictions.eq("p.codigoPregunta", codigoPregunta));
            criteria.setProjection(Projections.rowCount());

            countRespuestas = (Long) criteria.uniqueResult();
        } catch (HibernateException e) {
            System.out.println("Error al verificar respuestas de la pregunta: " + e.getMessage());
        } finally {
            session.close();
        }

        return countRespuestas > 0;
    }

    public static boolean update(Integer codigoRespuesta, String respuestaUno, Boolean esCorrectaUno, String respuestaDos, Boolean esCorrectaDos, String respuestaTres, Boolean esCorrectaTres, Integer codigoPregunta, String usuarioModifica) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Respuesta.class);
        criteria.add(Restrictions.eq("codigoRespuesta", codigoRespuesta));
        Respuesta update = (Respuesta) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                Pregunta pregunta = new Pregunta();
                pregunta.setCodigoPregunta(codigoPregunta);
                update.setPregunta(pregunta);
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

    public static List<Object[]> universo() {
        Session session = null;
        List<Object[]> listaResultado = null;
        try {
            session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            Query query = session.createSQLQuery("CALL PivotPreguntasRespuestas()");

            listaResultado = query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return listaResultado;
    }

    public static boolean anular(Integer codigoRespuesta) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Respuesta.class);
        criteria.add(Restrictions.eq("codigoRespuesta", codigoRespuesta));
        Respuesta update = (Respuesta) criteria.uniqueResult();
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
