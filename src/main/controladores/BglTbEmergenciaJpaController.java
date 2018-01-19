/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import main.controladores.exceptions.NonexistentEntityException;
import main.controladores.exceptions.PreexistingEntityException;
import main.entidades.BglTbEstadoemergencia;
import main.entidades.BglTbCatalogoincidentes;
import main.entidades.BglTbEmergencia;

/**
 *
 * @author michael.beltran
 */
public class BglTbEmergenciaJpaController implements Serializable {

    public BglTbEmergenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbEmergencia bglTbEmergencia) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbEstadoemergencia emEstado = bglTbEmergencia.getEmEstado();
            if (emEstado != null) {
                emEstado = em.getReference(emEstado.getClass(), emEstado.getEstCodigo());
                bglTbEmergencia.setEmEstado(emEstado);
            }
            BglTbCatalogoincidentes emTincCodigo = bglTbEmergencia.getEmTincCodigo();
            if (emTincCodigo != null) {
                emTincCodigo = em.getReference(emTincCodigo.getClass(), emTincCodigo.getCatCodigo());
                bglTbEmergencia.setEmTincCodigo(emTincCodigo);
            }
            em.persist(bglTbEmergencia);
            if (emEstado != null) {
                emEstado.getBglTbEmergenciaList().add(bglTbEmergencia);
                emEstado = em.merge(emEstado);
            }
            if (emTincCodigo != null) {
                emTincCodigo.getBglTbEmergenciaList().add(bglTbEmergencia);
                emTincCodigo = em.merge(emTincCodigo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBglTbEmergencia(bglTbEmergencia.getEmIdfuente()) != null) {
                throw new PreexistingEntityException("BglTbEmergencia " + bglTbEmergencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbEmergencia bglTbEmergencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbEmergencia persistentBglTbEmergencia = em.find(BglTbEmergencia.class, bglTbEmergencia.getEmIdfuente());
            BglTbEstadoemergencia emEstadoOld = persistentBglTbEmergencia.getEmEstado();
            BglTbEstadoemergencia emEstadoNew = bglTbEmergencia.getEmEstado();
            BglTbCatalogoincidentes emTincCodigoOld = persistentBglTbEmergencia.getEmTincCodigo();
            BglTbCatalogoincidentes emTincCodigoNew = bglTbEmergencia.getEmTincCodigo();
            if (emEstadoNew != null) {
                emEstadoNew = em.getReference(emEstadoNew.getClass(), emEstadoNew.getEstCodigo());
                bglTbEmergencia.setEmEstado(emEstadoNew);
            }
            if (emTincCodigoNew != null) {
                emTincCodigoNew = em.getReference(emTincCodigoNew.getClass(), emTincCodigoNew.getCatCodigo());
                bglTbEmergencia.setEmTincCodigo(emTincCodigoNew);
            }
            bglTbEmergencia = em.merge(bglTbEmergencia);
            if (emEstadoOld != null && !emEstadoOld.equals(emEstadoNew)) {
                emEstadoOld.getBglTbEmergenciaList().remove(bglTbEmergencia);
                emEstadoOld = em.merge(emEstadoOld);
            }
            if (emEstadoNew != null && !emEstadoNew.equals(emEstadoOld)) {
                emEstadoNew.getBglTbEmergenciaList().add(bglTbEmergencia);
                emEstadoNew = em.merge(emEstadoNew);
            }
            if (emTincCodigoOld != null && !emTincCodigoOld.equals(emTincCodigoNew)) {
                emTincCodigoOld.getBglTbEmergenciaList().remove(bglTbEmergencia);
                emTincCodigoOld = em.merge(emTincCodigoOld);
            }
            if (emTincCodigoNew != null && !emTincCodigoNew.equals(emTincCodigoOld)) {
                emTincCodigoNew.getBglTbEmergenciaList().add(bglTbEmergencia);
                emTincCodigoNew = em.merge(emTincCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = bglTbEmergencia.getEmIdfuente();
                if (findBglTbEmergencia(id) == null) {
                    throw new NonexistentEntityException("The bglTbEmergencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbEmergencia bglTbEmergencia;
            try {
                bglTbEmergencia = em.getReference(BglTbEmergencia.class, id);
                bglTbEmergencia.getEmIdfuente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbEmergencia with id " + id + " no longer exists.", enfe);
            }
            BglTbEstadoemergencia emEstado = bglTbEmergencia.getEmEstado();
            if (emEstado != null) {
                emEstado.getBglTbEmergenciaList().remove(bglTbEmergencia);
                emEstado = em.merge(emEstado);
            }
            BglTbCatalogoincidentes emTincCodigo = bglTbEmergencia.getEmTincCodigo();
            if (emTincCodigo != null) {
                emTincCodigo.getBglTbEmergenciaList().remove(bglTbEmergencia);
                emTincCodigo = em.merge(emTincCodigo);
            }
            em.remove(bglTbEmergencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbEmergencia> findBglTbEmergenciaEntities() {
        return findBglTbEmergenciaEntities(true, -1, -1);
    }

    public List<BglTbEmergencia> findBglTbEmergenciaEntities(int maxResults, int firstResult) {
        return findBglTbEmergenciaEntities(false, maxResults, firstResult);
    }

    private List<BglTbEmergencia> findBglTbEmergenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbEmergencia.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public BglTbEmergencia findBglTbEmergencia(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbEmergencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getBglTbEmergenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbEmergencia> rt = cq.from(BglTbEmergencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
