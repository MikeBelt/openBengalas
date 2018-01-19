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
import main.entidades.BglTbTracking;

/**
 *
 * @author michael.beltran
 */
public class BglTbTrackingJpaController implements Serializable {

    public BglTbTrackingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbTracking bglTbTracking) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bglTbTracking);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbTracking bglTbTracking) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            bglTbTracking = em.merge(bglTbTracking);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bglTbTracking.getCodEvento();
                if (findBglTbTracking(id) == null) {
                    throw new NonexistentEntityException("The bglTbTracking with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbTracking bglTbTracking;
            try {
                bglTbTracking = em.getReference(BglTbTracking.class, id);
                bglTbTracking.getCodEvento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbTracking with id " + id + " no longer exists.", enfe);
            }
            em.remove(bglTbTracking);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbTracking> findBglTbTrackingEntities() {
        return findBglTbTrackingEntities(true, -1, -1);
    }

    public List<BglTbTracking> findBglTbTrackingEntities(int maxResults, int firstResult) {
        return findBglTbTrackingEntities(false, maxResults, firstResult);
    }

    private List<BglTbTracking> findBglTbTrackingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbTracking.class));
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

    public BglTbTracking findBglTbTracking(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbTracking.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<BglTbTracking> findBglTbTrackingByDate(java.sql.Date fecinic,java.sql.Date fecfin) {
        EntityManager em = getEntityManager();
        try {
            Query query=em.createQuery("Select a from BglTbTracking a where a.trkFecha between :fecinic and :fecfin",BglTbTracking.class);
            query.setParameter("fecinic",fecfin);
            query.setParameter("fecfin",fecfin);
            List<BglTbTracking> listTracking=query.getResultList();
            return listTracking;
        } finally {
            em.close();
        }
    }

    public int getBglTbTrackingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbTracking> rt = cq.from(BglTbTracking.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
