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
import main.entidades.BglTbCiudad;

/**
 *
 * @author michael.beltran
 */
public class BglTbCiudadJpaController implements Serializable {

    public BglTbCiudadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbCiudad bglTbCiudad) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bglTbCiudad);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBglTbCiudad(bglTbCiudad.getCiuCodigo()) != null) {
                throw new PreexistingEntityException("BglTbCiudad " + bglTbCiudad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbCiudad bglTbCiudad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            bglTbCiudad = em.merge(bglTbCiudad);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = bglTbCiudad.getCiuCodigo();
                if (findBglTbCiudad(id) == null) {
                    throw new NonexistentEntityException("The bglTbCiudad with id " + id + " no longer exists.");
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
            BglTbCiudad bglTbCiudad;
            try {
                bglTbCiudad = em.getReference(BglTbCiudad.class, id);
                bglTbCiudad.getCiuCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbCiudad with id " + id + " no longer exists.", enfe);
            }
            em.remove(bglTbCiudad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbCiudad> findBglTbCiudadEntities() {
        return findBglTbCiudadEntities(true, -1, -1);
    }

    public List<BglTbCiudad> findBglTbCiudadEntities(int maxResults, int firstResult) {
        return findBglTbCiudadEntities(false, maxResults, firstResult);
    }

    private List<BglTbCiudad> findBglTbCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbCiudad.class));
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

    public BglTbCiudad findBglTbCiudad(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbCiudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getBglTbCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbCiudad> rt = cq.from(BglTbCiudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
