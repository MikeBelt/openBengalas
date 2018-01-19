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
import main.entidades.BglTbSitioincidente;
import main.entidades.BglTbTipositio;

/**
 *
 * @author michael.beltran
 */
public class BglTbSitioincidenteJpaController implements Serializable {

    public BglTbSitioincidenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbSitioincidente bglTbSitioincidente) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbTipositio sitTsitCodigo = bglTbSitioincidente.getSitTsitCodigo();
            if (sitTsitCodigo != null) {
                sitTsitCodigo = em.getReference(sitTsitCodigo.getClass(), sitTsitCodigo.getTsitCodigo());
                bglTbSitioincidente.setSitTsitCodigo(sitTsitCodigo);
            }
            em.persist(bglTbSitioincidente);
            if (sitTsitCodigo != null) {
                sitTsitCodigo.getBglTbSitioincidenteList().add(bglTbSitioincidente);
                sitTsitCodigo = em.merge(sitTsitCodigo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBglTbSitioincidente(bglTbSitioincidente.getSitCodigo()) != null) {
                throw new PreexistingEntityException("BglTbSitioincidente " + bglTbSitioincidente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbSitioincidente bglTbSitioincidente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbSitioincidente persistentBglTbSitioincidente = em.find(BglTbSitioincidente.class, bglTbSitioincidente.getSitCodigo());
            BglTbTipositio sitTsitCodigoOld = persistentBglTbSitioincidente.getSitTsitCodigo();
            BglTbTipositio sitTsitCodigoNew = bglTbSitioincidente.getSitTsitCodigo();
            if (sitTsitCodigoNew != null) {
                sitTsitCodigoNew = em.getReference(sitTsitCodigoNew.getClass(), sitTsitCodigoNew.getTsitCodigo());
                bglTbSitioincidente.setSitTsitCodigo(sitTsitCodigoNew);
            }
            bglTbSitioincidente = em.merge(bglTbSitioincidente);
            if (sitTsitCodigoOld != null && !sitTsitCodigoOld.equals(sitTsitCodigoNew)) {
                sitTsitCodigoOld.getBglTbSitioincidenteList().remove(bglTbSitioincidente);
                sitTsitCodigoOld = em.merge(sitTsitCodigoOld);
            }
            if (sitTsitCodigoNew != null && !sitTsitCodigoNew.equals(sitTsitCodigoOld)) {
                sitTsitCodigoNew.getBglTbSitioincidenteList().add(bglTbSitioincidente);
                sitTsitCodigoNew = em.merge(sitTsitCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bglTbSitioincidente.getSitCodigo();
                if (findBglTbSitioincidente(id) == null) {
                    throw new NonexistentEntityException("The bglTbSitioincidente with id " + id + " no longer exists.");
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
            BglTbSitioincidente bglTbSitioincidente;
            try {
                bglTbSitioincidente = em.getReference(BglTbSitioincidente.class, id);
                bglTbSitioincidente.getSitCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbSitioincidente with id " + id + " no longer exists.", enfe);
            }
            BglTbTipositio sitTsitCodigo = bglTbSitioincidente.getSitTsitCodigo();
            if (sitTsitCodigo != null) {
                sitTsitCodigo.getBglTbSitioincidenteList().remove(bglTbSitioincidente);
                sitTsitCodigo = em.merge(sitTsitCodigo);
            }
            em.remove(bglTbSitioincidente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbSitioincidente> findBglTbSitioincidenteEntities() {
        return findBglTbSitioincidenteEntities(true, -1, -1);
    }

    public List<BglTbSitioincidente> findBglTbSitioincidenteEntities(int maxResults, int firstResult) {
        return findBglTbSitioincidenteEntities(false, maxResults, firstResult);
    }

    private List<BglTbSitioincidente> findBglTbSitioincidenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbSitioincidente.class));
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

    public BglTbSitioincidente findBglTbSitioincidente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbSitioincidente.class, id);
        } finally {
            em.close();
        }
    }

    public int getBglTbSitioincidenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbSitioincidente> rt = cq.from(BglTbSitioincidente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
