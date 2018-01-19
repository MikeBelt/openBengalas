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
import main.entidades.BglTbDispositivo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import main.controladores.exceptions.IllegalOrphanException;
import main.controladores.exceptions.NonexistentEntityException;
import main.controladores.exceptions.PreexistingEntityException;
import main.entidades.BglTbTipodispositivo;

/**
 *
 * @author Priscila
 */
public class BglTbTipodispositivoJpaController implements Serializable {

    public BglTbTipodispositivoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbTipodispositivo bglTbTipodispositivo) throws PreexistingEntityException, Exception {
        if (bglTbTipodispositivo.getBglTbDispositivoList() == null) {
            bglTbTipodispositivo.setBglTbDispositivoList(new ArrayList<BglTbDispositivo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<BglTbDispositivo> attachedBglTbDispositivoList = new ArrayList<BglTbDispositivo>();
            for (BglTbDispositivo bglTbDispositivoListBglTbDispositivoToAttach : bglTbTipodispositivo.getBglTbDispositivoList()) {
                bglTbDispositivoListBglTbDispositivoToAttach = em.getReference(bglTbDispositivoListBglTbDispositivoToAttach.getClass(), bglTbDispositivoListBglTbDispositivoToAttach.getDispCodigo());
                attachedBglTbDispositivoList.add(bglTbDispositivoListBglTbDispositivoToAttach);
            }
            bglTbTipodispositivo.setBglTbDispositivoList(attachedBglTbDispositivoList);
            em.persist(bglTbTipodispositivo);
            for (BglTbDispositivo bglTbDispositivoListBglTbDispositivo : bglTbTipodispositivo.getBglTbDispositivoList()) {
                BglTbTipodispositivo oldDispTdispCodigoOfBglTbDispositivoListBglTbDispositivo = bglTbDispositivoListBglTbDispositivo.getDispTdispCodigo();
                bglTbDispositivoListBglTbDispositivo.setDispTdispCodigo(bglTbTipodispositivo);
                bglTbDispositivoListBglTbDispositivo = em.merge(bglTbDispositivoListBglTbDispositivo);
                if (oldDispTdispCodigoOfBglTbDispositivoListBglTbDispositivo != null) {
                    oldDispTdispCodigoOfBglTbDispositivoListBglTbDispositivo.getBglTbDispositivoList().remove(bglTbDispositivoListBglTbDispositivo);
                    oldDispTdispCodigoOfBglTbDispositivoListBglTbDispositivo = em.merge(oldDispTdispCodigoOfBglTbDispositivoListBglTbDispositivo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBglTbTipodispositivo(bglTbTipodispositivo.getTdispCodigo()) != null) {
                throw new PreexistingEntityException("BglTbTipodispositivo " + bglTbTipodispositivo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbTipodispositivo bglTbTipodispositivo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbTipodispositivo persistentBglTbTipodispositivo = em.find(BglTbTipodispositivo.class, bglTbTipodispositivo.getTdispCodigo());
            List<BglTbDispositivo> bglTbDispositivoListOld = persistentBglTbTipodispositivo.getBglTbDispositivoList();
            List<BglTbDispositivo> bglTbDispositivoListNew = bglTbTipodispositivo.getBglTbDispositivoList();
            List<String> illegalOrphanMessages = null;
            for (BglTbDispositivo bglTbDispositivoListOldBglTbDispositivo : bglTbDispositivoListOld) {
                if (!bglTbDispositivoListNew.contains(bglTbDispositivoListOldBglTbDispositivo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BglTbDispositivo " + bglTbDispositivoListOldBglTbDispositivo + " since its dispTdispCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<BglTbDispositivo> attachedBglTbDispositivoListNew = new ArrayList<BglTbDispositivo>();
            for (BglTbDispositivo bglTbDispositivoListNewBglTbDispositivoToAttach : bglTbDispositivoListNew) {
                bglTbDispositivoListNewBglTbDispositivoToAttach = em.getReference(bglTbDispositivoListNewBglTbDispositivoToAttach.getClass(), bglTbDispositivoListNewBglTbDispositivoToAttach.getDispCodigo());
                attachedBglTbDispositivoListNew.add(bglTbDispositivoListNewBglTbDispositivoToAttach);
            }
            bglTbDispositivoListNew = attachedBglTbDispositivoListNew;
            bglTbTipodispositivo.setBglTbDispositivoList(bglTbDispositivoListNew);
            bglTbTipodispositivo = em.merge(bglTbTipodispositivo);
            for (BglTbDispositivo bglTbDispositivoListNewBglTbDispositivo : bglTbDispositivoListNew) {
                if (!bglTbDispositivoListOld.contains(bglTbDispositivoListNewBglTbDispositivo)) {
                    BglTbTipodispositivo oldDispTdispCodigoOfBglTbDispositivoListNewBglTbDispositivo = bglTbDispositivoListNewBglTbDispositivo.getDispTdispCodigo();
                    bglTbDispositivoListNewBglTbDispositivo.setDispTdispCodigo(bglTbTipodispositivo);
                    bglTbDispositivoListNewBglTbDispositivo = em.merge(bglTbDispositivoListNewBglTbDispositivo);
                    if (oldDispTdispCodigoOfBglTbDispositivoListNewBglTbDispositivo != null && !oldDispTdispCodigoOfBglTbDispositivoListNewBglTbDispositivo.equals(bglTbTipodispositivo)) {
                        oldDispTdispCodigoOfBglTbDispositivoListNewBglTbDispositivo.getBglTbDispositivoList().remove(bglTbDispositivoListNewBglTbDispositivo);
                        oldDispTdispCodigoOfBglTbDispositivoListNewBglTbDispositivo = em.merge(oldDispTdispCodigoOfBglTbDispositivoListNewBglTbDispositivo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bglTbTipodispositivo.getTdispCodigo();
                if (findBglTbTipodispositivo(id) == null) {
                    throw new NonexistentEntityException("The bglTbTipodispositivo with id " + id + " no longer exists.");
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
            BglTbTipodispositivo bglTbTipodispositivo;
            try {
                bglTbTipodispositivo = em.getReference(BglTbTipodispositivo.class, id);
                bglTbTipodispositivo.getTdispCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbTipodispositivo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<BglTbDispositivo> bglTbDispositivoListOrphanCheck = bglTbTipodispositivo.getBglTbDispositivoList();
            for (BglTbDispositivo bglTbDispositivoListOrphanCheckBglTbDispositivo : bglTbDispositivoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This BglTbTipodispositivo (" + bglTbTipodispositivo + ") cannot be destroyed since the BglTbDispositivo " + bglTbDispositivoListOrphanCheckBglTbDispositivo + " in its bglTbDispositivoList field has a non-nullable dispTdispCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(bglTbTipodispositivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbTipodispositivo> findBglTbTipodispositivoEntities() {
        return findBglTbTipodispositivoEntities(true, -1, -1);
    }

    public List<BglTbTipodispositivo> findBglTbTipodispositivoEntities(int maxResults, int firstResult) {
        return findBglTbTipodispositivoEntities(false, maxResults, firstResult);
    }

    private List<BglTbTipodispositivo> findBglTbTipodispositivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbTipodispositivo.class));
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

    public BglTbTipodispositivo findBglTbTipodispositivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbTipodispositivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getBglTbTipodispositivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbTipodispositivo> rt = cq.from(BglTbTipodispositivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
