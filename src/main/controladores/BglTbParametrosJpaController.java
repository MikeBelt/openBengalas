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
import main.entidades.BglTbParametros;
import main.entidades.BglTbParametrosPK;

/**
 *
 * @author michael.beltran
 */
public class BglTbParametrosJpaController implements Serializable {

    public BglTbParametrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbParametros bglTbParametros) throws PreexistingEntityException, Exception {
        if (bglTbParametros.getBglTbParametrosPK() == null) {
            bglTbParametros.setBglTbParametrosPK(new BglTbParametrosPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bglTbParametros);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBglTbParametros(bglTbParametros.getBglTbParametrosPK()) != null) {
                throw new PreexistingEntityException("BglTbParametros " + bglTbParametros + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbParametros bglTbParametros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            bglTbParametros = em.merge(bglTbParametros);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BglTbParametrosPK id = bglTbParametros.getBglTbParametrosPK();
                if (findBglTbParametros(id) == null) {
                    throw new NonexistentEntityException("The bglTbParametros with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BglTbParametrosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbParametros bglTbParametros;
            try {
                bglTbParametros = em.getReference(BglTbParametros.class, id);
                bglTbParametros.getBglTbParametrosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbParametros with id " + id + " no longer exists.", enfe);
            }
            em.remove(bglTbParametros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbParametros> findBglTbParametrosEntities() {
        return findBglTbParametrosEntities(true, -1, -1);
    }

    public List<BglTbParametros> findBglTbParametrosEntities(int maxResults, int firstResult) {
        return findBglTbParametrosEntities(false, maxResults, firstResult);
    }

    private List<BglTbParametros> findBglTbParametrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbParametros.class));
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

    public BglTbParametros findBglTbParametros(BglTbParametrosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbParametros.class, id);
        } finally {
            em.close();
        }
    }

    public int getBglTbParametrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbParametros> rt = cq.from(BglTbParametros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
