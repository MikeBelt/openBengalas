/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import main.entidades.BglTbSitioincidente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import main.controladores.exceptions.IllegalOrphanException;
import main.controladores.exceptions.NonexistentEntityException;
import main.controladores.exceptions.PreexistingEntityException;
import main.entidades.BglTbTipositio;

/**
 *
 * @author Priscila
 */
public class BglTbTipositioJpaController implements Serializable {

    public BglTbTipositioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbTipositio bglTbTipositio) throws PreexistingEntityException, Exception {
        if (bglTbTipositio.getBglTbSitioincidenteList() == null) {
            bglTbTipositio.setBglTbSitioincidenteList(new ArrayList<BglTbSitioincidente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<BglTbSitioincidente> attachedBglTbSitioincidenteList = new ArrayList<BglTbSitioincidente>();
            for (BglTbSitioincidente bglTbSitioincidenteListBglTbSitioincidenteToAttach : bglTbTipositio.getBglTbSitioincidenteList()) {
                bglTbSitioincidenteListBglTbSitioincidenteToAttach = em.getReference(bglTbSitioincidenteListBglTbSitioincidenteToAttach.getClass(), bglTbSitioincidenteListBglTbSitioincidenteToAttach.getSitCodigo());
                attachedBglTbSitioincidenteList.add(bglTbSitioincidenteListBglTbSitioincidenteToAttach);
            }
            bglTbTipositio.setBglTbSitioincidenteList(attachedBglTbSitioincidenteList);
            em.persist(bglTbTipositio);
            for (BglTbSitioincidente bglTbSitioincidenteListBglTbSitioincidente : bglTbTipositio.getBglTbSitioincidenteList()) {
                BglTbTipositio oldSitTsitCodigoOfBglTbSitioincidenteListBglTbSitioincidente = bglTbSitioincidenteListBglTbSitioincidente.getSitTsitCodigo();
                bglTbSitioincidenteListBglTbSitioincidente.setSitTsitCodigo(bglTbTipositio);
                bglTbSitioincidenteListBglTbSitioincidente = em.merge(bglTbSitioincidenteListBglTbSitioincidente);
                if (oldSitTsitCodigoOfBglTbSitioincidenteListBglTbSitioincidente != null) {
                    oldSitTsitCodigoOfBglTbSitioincidenteListBglTbSitioincidente.getBglTbSitioincidenteList().remove(bglTbSitioincidenteListBglTbSitioincidente);
                    oldSitTsitCodigoOfBglTbSitioincidenteListBglTbSitioincidente = em.merge(oldSitTsitCodigoOfBglTbSitioincidenteListBglTbSitioincidente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBglTbTipositio(bglTbTipositio.getTsitCodigo()) != null) {
                throw new PreexistingEntityException("BglTbTipositio " + bglTbTipositio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbTipositio bglTbTipositio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbTipositio persistentBglTbTipositio = em.find(BglTbTipositio.class, bglTbTipositio.getTsitCodigo());
            List<BglTbSitioincidente> bglTbSitioincidenteListOld = persistentBglTbTipositio.getBglTbSitioincidenteList();
            List<BglTbSitioincidente> bglTbSitioincidenteListNew = bglTbTipositio.getBglTbSitioincidenteList();
            List<String> illegalOrphanMessages = null;
            for (BglTbSitioincidente bglTbSitioincidenteListOldBglTbSitioincidente : bglTbSitioincidenteListOld) {
                if (!bglTbSitioincidenteListNew.contains(bglTbSitioincidenteListOldBglTbSitioincidente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BglTbSitioincidente " + bglTbSitioincidenteListOldBglTbSitioincidente + " since its sitTsitCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<BglTbSitioincidente> attachedBglTbSitioincidenteListNew = new ArrayList<BglTbSitioincidente>();
            for (BglTbSitioincidente bglTbSitioincidenteListNewBglTbSitioincidenteToAttach : bglTbSitioincidenteListNew) {
                bglTbSitioincidenteListNewBglTbSitioincidenteToAttach = em.getReference(bglTbSitioincidenteListNewBglTbSitioincidenteToAttach.getClass(), bglTbSitioincidenteListNewBglTbSitioincidenteToAttach.getSitCodigo());
                attachedBglTbSitioincidenteListNew.add(bglTbSitioincidenteListNewBglTbSitioincidenteToAttach);
            }
            bglTbSitioincidenteListNew = attachedBglTbSitioincidenteListNew;
            bglTbTipositio.setBglTbSitioincidenteList(bglTbSitioincidenteListNew);
            bglTbTipositio = em.merge(bglTbTipositio);
            for (BglTbSitioincidente bglTbSitioincidenteListNewBglTbSitioincidente : bglTbSitioincidenteListNew) {
                if (!bglTbSitioincidenteListOld.contains(bglTbSitioincidenteListNewBglTbSitioincidente)) {
                    BglTbTipositio oldSitTsitCodigoOfBglTbSitioincidenteListNewBglTbSitioincidente = bglTbSitioincidenteListNewBglTbSitioincidente.getSitTsitCodigo();
                    bglTbSitioincidenteListNewBglTbSitioincidente.setSitTsitCodigo(bglTbTipositio);
                    bglTbSitioincidenteListNewBglTbSitioincidente = em.merge(bglTbSitioincidenteListNewBglTbSitioincidente);
                    if (oldSitTsitCodigoOfBglTbSitioincidenteListNewBglTbSitioincidente != null && !oldSitTsitCodigoOfBglTbSitioincidenteListNewBglTbSitioincidente.equals(bglTbTipositio)) {
                        oldSitTsitCodigoOfBglTbSitioincidenteListNewBglTbSitioincidente.getBglTbSitioincidenteList().remove(bglTbSitioincidenteListNewBglTbSitioincidente);
                        oldSitTsitCodigoOfBglTbSitioincidenteListNewBglTbSitioincidente = em.merge(oldSitTsitCodigoOfBglTbSitioincidenteListNewBglTbSitioincidente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bglTbTipositio.getTsitCodigo();
                if (findBglTbTipositio(id) == null) {
                    throw new NonexistentEntityException("The bglTbTipositio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbTipositio bglTbTipositio;
            try {
                bglTbTipositio = em.getReference(BglTbTipositio.class, id);
                bglTbTipositio.getTsitCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbTipositio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<BglTbSitioincidente> bglTbSitioincidenteListOrphanCheck = bglTbTipositio.getBglTbSitioincidenteList();
            for (BglTbSitioincidente bglTbSitioincidenteListOrphanCheckBglTbSitioincidente : bglTbSitioincidenteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This BglTbTipositio (" + bglTbTipositio + ") cannot be destroyed since the BglTbSitioincidente " + bglTbSitioincidenteListOrphanCheckBglTbSitioincidente + " in its bglTbSitioincidenteList field has a non-nullable sitTsitCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(bglTbTipositio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbTipositio> findBglTbTipositioEntities() {
        return findBglTbTipositioEntities(true, -1, -1);
    }

    public List<BglTbTipositio> findBglTbTipositioEntities(int maxResults, int firstResult) {
        return findBglTbTipositioEntities(false, maxResults, firstResult);
    }

    private List<BglTbTipositio> findBglTbTipositioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbTipositio.class));
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

    public BglTbTipositio findBglTbTipositio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbTipositio.class, id);
        } finally {
            em.close();
        }
    }

    public int getBglTbTipositioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbTipositio> rt = cq.from(BglTbTipositio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
