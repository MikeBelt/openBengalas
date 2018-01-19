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
import main.entidades.BglTbEmergencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import main.controladores.exceptions.IllegalOrphanException;
import main.controladores.exceptions.NonexistentEntityException;
import main.controladores.exceptions.PreexistingEntityException;
import main.entidades.BglTbEstadoemergencia;

/**
 *
 * @author michael.beltran
 */
public class BglTbEstadoemergenciaJpaController implements Serializable {

    public BglTbEstadoemergenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbEstadoemergencia bglTbEstadoemergencia) throws PreexistingEntityException, Exception {
        if (bglTbEstadoemergencia.getBglTbEmergenciaList() == null) {
            bglTbEstadoemergencia.setBglTbEmergenciaList(new ArrayList<BglTbEmergencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<BglTbEmergencia> attachedBglTbEmergenciaList = new ArrayList<BglTbEmergencia>();
            for (BglTbEmergencia bglTbEmergenciaListBglTbEmergenciaToAttach : bglTbEstadoemergencia.getBglTbEmergenciaList()) {
                bglTbEmergenciaListBglTbEmergenciaToAttach = em.getReference(bglTbEmergenciaListBglTbEmergenciaToAttach.getClass(), bglTbEmergenciaListBglTbEmergenciaToAttach.getEmIdfuente());
                attachedBglTbEmergenciaList.add(bglTbEmergenciaListBglTbEmergenciaToAttach);
            }
            bglTbEstadoemergencia.setBglTbEmergenciaList(attachedBglTbEmergenciaList);
            em.persist(bglTbEstadoemergencia);
            for (BglTbEmergencia bglTbEmergenciaListBglTbEmergencia : bglTbEstadoemergencia.getBglTbEmergenciaList()) {
                BglTbEstadoemergencia oldEmEstadoOfBglTbEmergenciaListBglTbEmergencia = bglTbEmergenciaListBglTbEmergencia.getEmEstado();
                bglTbEmergenciaListBglTbEmergencia.setEmEstado(bglTbEstadoemergencia);
                bglTbEmergenciaListBglTbEmergencia = em.merge(bglTbEmergenciaListBglTbEmergencia);
                if (oldEmEstadoOfBglTbEmergenciaListBglTbEmergencia != null) {
                    oldEmEstadoOfBglTbEmergenciaListBglTbEmergencia.getBglTbEmergenciaList().remove(bglTbEmergenciaListBglTbEmergencia);
                    oldEmEstadoOfBglTbEmergenciaListBglTbEmergencia = em.merge(oldEmEstadoOfBglTbEmergenciaListBglTbEmergencia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBglTbEstadoemergencia(bglTbEstadoemergencia.getEstCodigo()) != null) {
                throw new PreexistingEntityException("BglTbEstadoemergencia " + bglTbEstadoemergencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbEstadoemergencia bglTbEstadoemergencia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbEstadoemergencia persistentBglTbEstadoemergencia = em.find(BglTbEstadoemergencia.class, bglTbEstadoemergencia.getEstCodigo());
            List<BglTbEmergencia> bglTbEmergenciaListOld = persistentBglTbEstadoemergencia.getBglTbEmergenciaList();
            List<BglTbEmergencia> bglTbEmergenciaListNew = bglTbEstadoemergencia.getBglTbEmergenciaList();
            List<String> illegalOrphanMessages = null;
            for (BglTbEmergencia bglTbEmergenciaListOldBglTbEmergencia : bglTbEmergenciaListOld) {
                if (!bglTbEmergenciaListNew.contains(bglTbEmergenciaListOldBglTbEmergencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BglTbEmergencia " + bglTbEmergenciaListOldBglTbEmergencia + " since its emEstado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<BglTbEmergencia> attachedBglTbEmergenciaListNew = new ArrayList<BglTbEmergencia>();
            for (BglTbEmergencia bglTbEmergenciaListNewBglTbEmergenciaToAttach : bglTbEmergenciaListNew) {
                bglTbEmergenciaListNewBglTbEmergenciaToAttach = em.getReference(bglTbEmergenciaListNewBglTbEmergenciaToAttach.getClass(), bglTbEmergenciaListNewBglTbEmergenciaToAttach.getEmIdfuente());
                attachedBglTbEmergenciaListNew.add(bglTbEmergenciaListNewBglTbEmergenciaToAttach);
            }
            bglTbEmergenciaListNew = attachedBglTbEmergenciaListNew;
            bglTbEstadoemergencia.setBglTbEmergenciaList(bglTbEmergenciaListNew);
            bglTbEstadoemergencia = em.merge(bglTbEstadoemergencia);
            for (BglTbEmergencia bglTbEmergenciaListNewBglTbEmergencia : bglTbEmergenciaListNew) {
                if (!bglTbEmergenciaListOld.contains(bglTbEmergenciaListNewBglTbEmergencia)) {
                    BglTbEstadoemergencia oldEmEstadoOfBglTbEmergenciaListNewBglTbEmergencia = bglTbEmergenciaListNewBglTbEmergencia.getEmEstado();
                    bglTbEmergenciaListNewBglTbEmergencia.setEmEstado(bglTbEstadoemergencia);
                    bglTbEmergenciaListNewBglTbEmergencia = em.merge(bglTbEmergenciaListNewBglTbEmergencia);
                    if (oldEmEstadoOfBglTbEmergenciaListNewBglTbEmergencia != null && !oldEmEstadoOfBglTbEmergenciaListNewBglTbEmergencia.equals(bglTbEstadoemergencia)) {
                        oldEmEstadoOfBglTbEmergenciaListNewBglTbEmergencia.getBglTbEmergenciaList().remove(bglTbEmergenciaListNewBglTbEmergencia);
                        oldEmEstadoOfBglTbEmergenciaListNewBglTbEmergencia = em.merge(oldEmEstadoOfBglTbEmergenciaListNewBglTbEmergencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bglTbEstadoemergencia.getEstCodigo();
                if (findBglTbEstadoemergencia(id) == null) {
                    throw new NonexistentEntityException("The bglTbEstadoemergencia with id " + id + " no longer exists.");
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
            BglTbEstadoemergencia bglTbEstadoemergencia;
            try {
                bglTbEstadoemergencia = em.getReference(BglTbEstadoemergencia.class, id);
                bglTbEstadoemergencia.getEstCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbEstadoemergencia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<BglTbEmergencia> bglTbEmergenciaListOrphanCheck = bglTbEstadoemergencia.getBglTbEmergenciaList();
            for (BglTbEmergencia bglTbEmergenciaListOrphanCheckBglTbEmergencia : bglTbEmergenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This BglTbEstadoemergencia (" + bglTbEstadoemergencia + ") cannot be destroyed since the BglTbEmergencia " + bglTbEmergenciaListOrphanCheckBglTbEmergencia + " in its bglTbEmergenciaList field has a non-nullable emEstado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(bglTbEstadoemergencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbEstadoemergencia> findBglTbEstadoemergenciaEntities() {
        return findBglTbEstadoemergenciaEntities(true, -1, -1);
    }

    public List<BglTbEstadoemergencia> findBglTbEstadoemergenciaEntities(int maxResults, int firstResult) {
        return findBglTbEstadoemergenciaEntities(false, maxResults, firstResult);
    }

    private List<BglTbEstadoemergencia> findBglTbEstadoemergenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbEstadoemergencia.class));
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

    public BglTbEstadoemergencia findBglTbEstadoemergencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbEstadoemergencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getBglTbEstadoemergenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbEstadoemergencia> rt = cq.from(BglTbEstadoemergencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
