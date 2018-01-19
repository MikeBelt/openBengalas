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
import main.entidades.BglTbUsuario;

/**
 *
 * @author Priscila
 */
public class BglTbUsuarioJpaController implements Serializable {

    public BglTbUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbUsuario bglTbUsuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bglTbUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbUsuario bglTbUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            bglTbUsuario = em.merge(bglTbUsuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bglTbUsuario.getUsuCodigo();
                if (findBglTbUsuario(id) == null) {
                    throw new NonexistentEntityException("The bglTbUsuario with id " + id + " no longer exists.");
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
            BglTbUsuario bglTbUsuario;
            try {
                bglTbUsuario = em.getReference(BglTbUsuario.class, id);
                bglTbUsuario.getUsuCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbUsuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(bglTbUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbUsuario> findBglTbUsuarioEntities() {
        return findBglTbUsuarioEntities(true, -1, -1);
    }

    public List<BglTbUsuario> findBglTbUsuarioEntities(int maxResults, int firstResult) {
        return findBglTbUsuarioEntities(false, maxResults, firstResult);
    }

    private List<BglTbUsuario> findBglTbUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbUsuario.class));
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

    public BglTbUsuario findBglTbUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbUsuario.class, id);
        } finally {
            em.close();
        }
    }
    
    public BglTbUsuario findBglTbUsuario(String alias,String hash) {
        EntityManager em = getEntityManager();
        try {
            Query query=em.createQuery("Select a from BglTbUsuario a where a.usuAlias=:alias and a.usuHash=:hash",BglTbUsuario.class);
            query.setParameter("alias",alias);
            query.setParameter("hash",hash);
            BglTbUsuario usuario=(BglTbUsuario)query.getSingleResult();
            return usuario;
        } finally {
            em.close();
        }
    }

    public int getBglTbUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbUsuario> rt = cq.from(BglTbUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
