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
import main.entidades.BglTbTipoactividad;

/**
 *
 * @author michael.beltran
 */
public class BglTbTipoactividadJpaController implements Serializable {

    public BglTbTipoactividadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbTipoactividad bglTbTipoactividad) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bglTbTipoactividad);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBglTbTipoactividad(bglTbTipoactividad.getTactCodigo()) != null) {
                throw new PreexistingEntityException("BglTbTipoactividad " + bglTbTipoactividad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbTipoactividad bglTbTipoactividad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            bglTbTipoactividad = em.merge(bglTbTipoactividad);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bglTbTipoactividad.getTactCodigo();
                if (findBglTbTipoactividad(id) == null) {
                    throw new NonexistentEntityException("The bglTbTipoactividad with id " + id + " no longer exists.");
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
            BglTbTipoactividad bglTbTipoactividad;
            try {
                bglTbTipoactividad = em.getReference(BglTbTipoactividad.class, id);
                bglTbTipoactividad.getTactCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbTipoactividad with id " + id + " no longer exists.", enfe);
            }
            em.remove(bglTbTipoactividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbTipoactividad> findBglTbTipoactividadEntities() {
        return findBglTbTipoactividadEntities(true, -1, -1);
    }

    public List<BglTbTipoactividad> findBglTbTipoactividadEntities(int maxResults, int firstResult) {
        return findBglTbTipoactividadEntities(false, maxResults, firstResult);
    }

    private List<BglTbTipoactividad> findBglTbTipoactividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbTipoactividad.class));
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

    public BglTbTipoactividad findBglTbTipoactividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbTipoactividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getBglTbTipoactividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbTipoactividad> rt = cq.from(BglTbTipoactividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
