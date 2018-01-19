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
import main.entidades.BglTbCatalogoincidentes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import main.controladores.exceptions.IllegalOrphanException;
import main.controladores.exceptions.NonexistentEntityException;
import main.controladores.exceptions.PreexistingEntityException;
import main.entidades.BglTbTipoincidente;

/**
 *
 * @author michael.beltran
 */
public class BglTbTipoincidenteJpaController implements Serializable {

    public BglTbTipoincidenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbTipoincidente bglTbTipoincidente) throws PreexistingEntityException, Exception {
        if (bglTbTipoincidente.getBglTbCatalogoincidentesList() == null) {
            bglTbTipoincidente.setBglTbCatalogoincidentesList(new ArrayList<BglTbCatalogoincidentes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<BglTbCatalogoincidentes> attachedBglTbCatalogoincidentesList = new ArrayList<BglTbCatalogoincidentes>();
            for (BglTbCatalogoincidentes bglTbCatalogoincidentesListBglTbCatalogoincidentesToAttach : bglTbTipoincidente.getBglTbCatalogoincidentesList()) {
                bglTbCatalogoincidentesListBglTbCatalogoincidentesToAttach = em.getReference(bglTbCatalogoincidentesListBglTbCatalogoincidentesToAttach.getClass(), bglTbCatalogoincidentesListBglTbCatalogoincidentesToAttach.getCatCodigo());
                attachedBglTbCatalogoincidentesList.add(bglTbCatalogoincidentesListBglTbCatalogoincidentesToAttach);
            }
            bglTbTipoincidente.setBglTbCatalogoincidentesList(attachedBglTbCatalogoincidentesList);
            em.persist(bglTbTipoincidente);
            for (BglTbCatalogoincidentes bglTbCatalogoincidentesListBglTbCatalogoincidentes : bglTbTipoincidente.getBglTbCatalogoincidentesList()) {
                BglTbTipoincidente oldCatTincidCodigoOfBglTbCatalogoincidentesListBglTbCatalogoincidentes = bglTbCatalogoincidentesListBglTbCatalogoincidentes.getCatTincidCodigo();
                bglTbCatalogoincidentesListBglTbCatalogoincidentes.setCatTincidCodigo(bglTbTipoincidente);
                bglTbCatalogoincidentesListBglTbCatalogoincidentes = em.merge(bglTbCatalogoincidentesListBglTbCatalogoincidentes);
                if (oldCatTincidCodigoOfBglTbCatalogoincidentesListBglTbCatalogoincidentes != null) {
                    oldCatTincidCodigoOfBglTbCatalogoincidentesListBglTbCatalogoincidentes.getBglTbCatalogoincidentesList().remove(bglTbCatalogoincidentesListBglTbCatalogoincidentes);
                    oldCatTincidCodigoOfBglTbCatalogoincidentesListBglTbCatalogoincidentes = em.merge(oldCatTincidCodigoOfBglTbCatalogoincidentesListBglTbCatalogoincidentes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBglTbTipoincidente(bglTbTipoincidente.getTincidCodigo()) != null) {
                throw new PreexistingEntityException("BglTbTipoincidente " + bglTbTipoincidente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbTipoincidente bglTbTipoincidente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbTipoincidente persistentBglTbTipoincidente = em.find(BglTbTipoincidente.class, bglTbTipoincidente.getTincidCodigo());
            List<BglTbCatalogoincidentes> bglTbCatalogoincidentesListOld = persistentBglTbTipoincidente.getBglTbCatalogoincidentesList();
            List<BglTbCatalogoincidentes> bglTbCatalogoincidentesListNew = bglTbTipoincidente.getBglTbCatalogoincidentesList();
            List<String> illegalOrphanMessages = null;
            for (BglTbCatalogoincidentes bglTbCatalogoincidentesListOldBglTbCatalogoincidentes : bglTbCatalogoincidentesListOld) {
                if (!bglTbCatalogoincidentesListNew.contains(bglTbCatalogoincidentesListOldBglTbCatalogoincidentes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BglTbCatalogoincidentes " + bglTbCatalogoincidentesListOldBglTbCatalogoincidentes + " since its catTincidCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<BglTbCatalogoincidentes> attachedBglTbCatalogoincidentesListNew = new ArrayList<BglTbCatalogoincidentes>();
            for (BglTbCatalogoincidentes bglTbCatalogoincidentesListNewBglTbCatalogoincidentesToAttach : bglTbCatalogoincidentesListNew) {
                bglTbCatalogoincidentesListNewBglTbCatalogoincidentesToAttach = em.getReference(bglTbCatalogoincidentesListNewBglTbCatalogoincidentesToAttach.getClass(), bglTbCatalogoincidentesListNewBglTbCatalogoincidentesToAttach.getCatCodigo());
                attachedBglTbCatalogoincidentesListNew.add(bglTbCatalogoincidentesListNewBglTbCatalogoincidentesToAttach);
            }
            bglTbCatalogoincidentesListNew = attachedBglTbCatalogoincidentesListNew;
            bglTbTipoincidente.setBglTbCatalogoincidentesList(bglTbCatalogoincidentesListNew);
            bglTbTipoincidente = em.merge(bglTbTipoincidente);
            for (BglTbCatalogoincidentes bglTbCatalogoincidentesListNewBglTbCatalogoincidentes : bglTbCatalogoincidentesListNew) {
                if (!bglTbCatalogoincidentesListOld.contains(bglTbCatalogoincidentesListNewBglTbCatalogoincidentes)) {
                    BglTbTipoincidente oldCatTincidCodigoOfBglTbCatalogoincidentesListNewBglTbCatalogoincidentes = bglTbCatalogoincidentesListNewBglTbCatalogoincidentes.getCatTincidCodigo();
                    bglTbCatalogoincidentesListNewBglTbCatalogoincidentes.setCatTincidCodigo(bglTbTipoincidente);
                    bglTbCatalogoincidentesListNewBglTbCatalogoincidentes = em.merge(bglTbCatalogoincidentesListNewBglTbCatalogoincidentes);
                    if (oldCatTincidCodigoOfBglTbCatalogoincidentesListNewBglTbCatalogoincidentes != null && !oldCatTincidCodigoOfBglTbCatalogoincidentesListNewBglTbCatalogoincidentes.equals(bglTbTipoincidente)) {
                        oldCatTincidCodigoOfBglTbCatalogoincidentesListNewBglTbCatalogoincidentes.getBglTbCatalogoincidentesList().remove(bglTbCatalogoincidentesListNewBglTbCatalogoincidentes);
                        oldCatTincidCodigoOfBglTbCatalogoincidentesListNewBglTbCatalogoincidentes = em.merge(oldCatTincidCodigoOfBglTbCatalogoincidentesListNewBglTbCatalogoincidentes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bglTbTipoincidente.getTincidCodigo();
                if (findBglTbTipoincidente(id) == null) {
                    throw new NonexistentEntityException("The bglTbTipoincidente with id " + id + " no longer exists.");
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
            BglTbTipoincidente bglTbTipoincidente;
            try {
                bglTbTipoincidente = em.getReference(BglTbTipoincidente.class, id);
                bglTbTipoincidente.getTincidCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbTipoincidente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<BglTbCatalogoincidentes> bglTbCatalogoincidentesListOrphanCheck = bglTbTipoincidente.getBglTbCatalogoincidentesList();
            for (BglTbCatalogoincidentes bglTbCatalogoincidentesListOrphanCheckBglTbCatalogoincidentes : bglTbCatalogoincidentesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This BglTbTipoincidente (" + bglTbTipoincidente + ") cannot be destroyed since the BglTbCatalogoincidentes " + bglTbCatalogoincidentesListOrphanCheckBglTbCatalogoincidentes + " in its bglTbCatalogoincidentesList field has a non-nullable catTincidCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(bglTbTipoincidente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbTipoincidente> findBglTbTipoincidenteEntities() {
        return findBglTbTipoincidenteEntities(true, -1, -1);
    }

    public List<BglTbTipoincidente> findBglTbTipoincidenteEntities(int maxResults, int firstResult) {
        return findBglTbTipoincidenteEntities(false, maxResults, firstResult);
    }

    private List<BglTbTipoincidente> findBglTbTipoincidenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbTipoincidente.class));
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

    public BglTbTipoincidente findBglTbTipoincidente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbTipoincidente.class, id);
        } finally {
            em.close();
        }
    }

    public int getBglTbTipoincidenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbTipoincidente> rt = cq.from(BglTbTipoincidente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
