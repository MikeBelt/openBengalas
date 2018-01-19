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
import main.entidades.BglTbSector;

/**
 *
 * @author michael.beltran
 */
public class BglTbSectorJpaController implements Serializable {

    public BglTbSectorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbSector bglTbSector) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bglTbSector);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBglTbSector(bglTbSector.getSecCodigo()) != null) {
                throw new PreexistingEntityException("BglTbSector " + bglTbSector + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbSector bglTbSector) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            bglTbSector = em.merge(bglTbSector);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bglTbSector.getSecCodigo();
                if (findBglTbSector(id) == null) {
                    throw new NonexistentEntityException("The bglTbSector with id " + id + " no longer exists.");
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
            BglTbSector bglTbSector;
            try {
                bglTbSector = em.getReference(BglTbSector.class, id);
                bglTbSector.getSecCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbSector with id " + id + " no longer exists.", enfe);
            }
            em.remove(bglTbSector);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbSector> findBglTbSectorEntities() {
        return findBglTbSectorEntities(true, -1, -1);
    }

    public List<BglTbSector> findBglTbSectorEntities(int maxResults, int firstResult) {
        return findBglTbSectorEntities(false, maxResults, firstResult);
    }

    private List<BglTbSector> findBglTbSectorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbSector.class));
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

    public BglTbSector findBglTbSector(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbSector.class, id);
        } finally {
            em.close();
        }
    }

    public int getBglTbSectorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbSector> rt = cq.from(BglTbSector.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
