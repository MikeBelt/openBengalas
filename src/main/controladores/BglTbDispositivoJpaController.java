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
import main.entidades.BglTbDispositivo;
import main.entidades.BglTbTipodispositivo;

/**
 *
 * @author michael.beltran
 */
public class BglTbDispositivoJpaController implements Serializable {

    public BglTbDispositivoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbDispositivo bglTbDispositivo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbTipodispositivo dispTdispCodigo = bglTbDispositivo.getDispTdispCodigo();
            if (dispTdispCodigo != null) {
                dispTdispCodigo = em.getReference(dispTdispCodigo.getClass(), dispTdispCodigo.getTdispCodigo());
                bglTbDispositivo.setDispTdispCodigo(dispTdispCodigo);
            }
            em.persist(bglTbDispositivo);
            if (dispTdispCodigo != null) {
                dispTdispCodigo.getBglTbDispositivoList().add(bglTbDispositivo);
                dispTdispCodigo = em.merge(dispTdispCodigo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBglTbDispositivo(bglTbDispositivo.getDispCodigo()) != null) {
                throw new PreexistingEntityException("BglTbDispositivo " + bglTbDispositivo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbDispositivo bglTbDispositivo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbDispositivo persistentBglTbDispositivo = em.find(BglTbDispositivo.class, bglTbDispositivo.getDispCodigo());
            BglTbTipodispositivo dispTdispCodigoOld = persistentBglTbDispositivo.getDispTdispCodigo();
            BglTbTipodispositivo dispTdispCodigoNew = bglTbDispositivo.getDispTdispCodigo();
            if (dispTdispCodigoNew != null) {
                dispTdispCodigoNew = em.getReference(dispTdispCodigoNew.getClass(), dispTdispCodigoNew.getTdispCodigo());
                bglTbDispositivo.setDispTdispCodigo(dispTdispCodigoNew);
            }
            bglTbDispositivo = em.merge(bglTbDispositivo);
            if (dispTdispCodigoOld != null && !dispTdispCodigoOld.equals(dispTdispCodigoNew)) {
                dispTdispCodigoOld.getBglTbDispositivoList().remove(bglTbDispositivo);
                dispTdispCodigoOld = em.merge(dispTdispCodigoOld);
            }
            if (dispTdispCodigoNew != null && !dispTdispCodigoNew.equals(dispTdispCodigoOld)) {
                dispTdispCodigoNew.getBglTbDispositivoList().add(bglTbDispositivo);
                dispTdispCodigoNew = em.merge(dispTdispCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bglTbDispositivo.getDispCodigo();
                if (findBglTbDispositivo(id) == null) {
                    throw new NonexistentEntityException("The bglTbDispositivo with id " + id + " no longer exists.");
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
            BglTbDispositivo bglTbDispositivo;
            try {
                bglTbDispositivo = em.getReference(BglTbDispositivo.class, id);
                bglTbDispositivo.getDispCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbDispositivo with id " + id + " no longer exists.", enfe);
            }
            BglTbTipodispositivo dispTdispCodigo = bglTbDispositivo.getDispTdispCodigo();
            if (dispTdispCodigo != null) {
                dispTdispCodigo.getBglTbDispositivoList().remove(bglTbDispositivo);
                dispTdispCodigo = em.merge(dispTdispCodigo);
            }
            em.remove(bglTbDispositivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbDispositivo> findBglTbDispositivoEntities() {
        return findBglTbDispositivoEntities(true, -1, -1);
    }

    public List<BglTbDispositivo> findBglTbDispositivoEntities(int maxResults, int firstResult) {
        return findBglTbDispositivoEntities(false, maxResults, firstResult);
    }

    private List<BglTbDispositivo> findBglTbDispositivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbDispositivo.class));
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

    public BglTbDispositivo findBglTbDispositivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbDispositivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getBglTbDispositivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbDispositivo> rt = cq.from(BglTbDispositivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
