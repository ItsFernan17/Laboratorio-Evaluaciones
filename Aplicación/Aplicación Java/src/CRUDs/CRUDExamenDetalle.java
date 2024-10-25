/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import Pojos.Detalle;
import Pojos.Empleo;
import Pojos.Examen;
import Pojos.Motivo;
import Pojos.Pregunta;
import Pojos.Respuesta;
import Pojos.Usuario;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ferna
 */
public class CRUDExamenDetalle {

    public static boolean insert(Integer codigoExamen, String serie, String instruccion, Integer codigoPregunta, String usuarioIngreso) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Examen examen = (Examen) session.get(Examen.class, codigoExamen);
            if (examen == null) {
                throw new HibernateException("El examen con código " + codigoExamen + " no existe.");
            }
            Detalle nuevaPregunta = new Detalle();
            nuevaPregunta.setEstado(true);
            nuevaPregunta.setSerie(serie);
            nuevaPregunta.setInstruccion(instruccion);
            nuevaPregunta.setExamen(examen);
            Pregunta pregunta = new Pregunta();
            pregunta.setCodigoPregunta(codigoPregunta);
            nuevaPregunta.setPregunta(pregunta);
            Usuario usuario = new Usuario();
            usuario.setDpi(usuarioIngreso);
            nuevaPregunta.setUsuarioByUsuarioIngreso(usuario);
            nuevaPregunta.setFechaIngreso(fecha);
            session.save(nuevaPregunta);
            transaction.commit();
            flag = true;

            System.out.println("Insertando detalle con CodigoExamen: " + codigoExamen + " Pregunta: " + codigoPregunta);

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

    public static boolean update(Integer codigoDetalle, Integer codigoExamen, String serie, String instruccion, Integer codigoPregunta, String usuarioModifica) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Detalle.class);
        criteria.add(Restrictions.eq("codigoDetalle", codigoDetalle));
        Detalle update = (Detalle) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                Pregunta pregunta = new Pregunta();
                Examen examen = new Examen();
                examen.setCodigoExamen(codigoExamen);
                update.setExamen(examen);
                update.setSerie(serie);
                update.setInstruccion(instruccion);
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

    public static boolean anular(Integer codigoDetalle) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Detalle.class);
        criteria.add(Restrictions.eq("codigoDetalle", codigoDetalle));
        Detalle update = (Detalle) criteria.uniqueResult();
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

    public static boolean anularDetallesPorExamen(Integer codigoExamen) {
        boolean flag = false;
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Detalle.class);
            criteria.createAlias("examen", "ex");
            criteria.add(Restrictions.eq("ex.codigoExamen", codigoExamen));

            List<Detalle> detalles = criteria.list();

            if (detalles != null && !detalles.isEmpty()) {
                for (Detalle detalle : detalles) {
                    detalle.setEstado(false);
                    session.update(detalle);
                }
                flag = true;
            }

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error al anular los detalles: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }

        return flag;
    }

    public static List<Object[]> obtenerExamenesPorUsuarioYCodigoExamen(int codigoExamen, String evaluadoDpi) {
        Session session = HibernetUtil.HibernateUtil.getSessionFactory().openSession();  // Ajusté la clase HibernetUtil
        List<Object[]> listaExamenes = null;

        try {
            Transaction transaction = session.beginTransaction();

            listaExamenes = session.createQuery(
                    "SELECT e.codigoExamen, e.fechaEvaluacion, m.nombreMotivo, emp.descripcion AS empleo, e.punteoMaximo, "
                    + "de.codigoDetalle, de.serie, de.instruccion, p.enunciado AS pregunta, "
                    + "MAX(CASE WHEN r.codigoRespuesta = (SELECT MIN(r2.codigoRespuesta) FROM Respuesta r2 WHERE r2.pregunta = p.codigoPregunta) THEN r.respuesta ELSE NULL END) AS respuesta1, "
                    + "MAX(CASE WHEN r.codigoRespuesta = (SELECT MIN(r2.codigoRespuesta) + 1 FROM Respuesta r2 WHERE r2.pregunta = p.codigoPregunta) THEN r.respuesta ELSE NULL END) AS respuesta2, "
                    + "MAX(CASE WHEN r.codigoRespuesta = (SELECT MIN(r2.codigoRespuesta) + 2 FROM Respuesta r2 WHERE r2.pregunta = p.codigoPregunta) THEN r.respuesta ELSE NULL END) AS respuesta3 "
                    + "FROM Asignacion a "
                    + "JOIN a.examen e "
                    + "JOIN e.motivo m "
                    + "JOIN e.empleo emp "
                    + "JOIN e.detalles de "
                    + "JOIN de.pregunta p "
                    + "JOIN p.respuestas r "
                    + "WHERE e.codigoExamen = :codigoExamen AND a.usuarioByEvaluado.dpi = :usuarioByEvaluado "
                    + // Usamos "usuarioByEvaluado" para el evaluado
                    "GROUP BY e.codigoExamen, e.fechaEvaluacion, m.nombreMotivo, emp.descripcion, e.punteoMaximo, "
                    + "de.codigoDetalle, de.serie, de.instruccion, p.enunciado"
            )
                    .setParameter("codigoExamen", codigoExamen)
                    .setParameter("usuarioByEvaluado", evaluadoDpi)
                    .list();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return listaExamenes;
    }

}
