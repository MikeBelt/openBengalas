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
import main.entidades.BglTbTipoincidente;
import main.entidades.BglTbEmergencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import main.controladores.exceptions.IllegalOrphanException;
import main.controladores.exceptions.NonexistentEntityException;
import main.controladores.exceptions.PreexistingEntityException;
import main.entidades.BglTbCatalogoincidentes;

/**
 *
 * @author michael.beltran
 */
public class BglTbCatalogoincidentesJpaController implements Serializable {

    public BglTbCatalogoincidentesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BglTbCatalogoincidentes bglTbCatalogoincidentes) throws PreexistingEntityException, Exception {
        if (bglTbCatalogoincidentes.getBglTbEmergenciaList() == null) {
            bglTbCatalogoincidentes.setBglTbEmergenciaList(new ArrayList<BglTbEmergencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbTipoincidente catTincidCodigo = bglTbCatalogoincidentes.getCatTincidCodigo();
            if (catTincidCodigo != null) {
                catTincidCodigo = em.getReference(catTincidCodigo.getClass(), catTincidCodigo.getTincidCodigo());
                bglTbCatalogoincidentes.setCatTincidCodigo(catTincidCodigo);
            }
            List<BglTbEmergencia> attachedBglTbEmergenciaList = new ArrayList<BglTbEmergencia>();
            for (BglTbEmergencia bglTbEmergenciaListBglTbEmergenciaToAttach : bglTbCatalogoincidentes.getBglTbEmergenciaList()) {
                bglTbEmergenciaListBglTbEmergenciaToAttach = em.getReference(bglTbEmergenciaListBglTbEmergenciaToAttach.getClass(), bglTbEmergenciaListBglTbEmergenciaToAttach.getEmIdfuente());
                attachedBglTbEmergenciaList.add(bglTbEmergenciaListBglTbEmergenciaToAttach);
            }
            bglTbCatalogoincidentes.setBglTbEmergenciaList(attachedBglTbEmergenciaList);
            em.persist(bglTbCatalogoincidentes);
            if (catTincidCodigo != null) {
                catTincidCodigo.getBglTbCatalogoincidentesList().add(bglTbCatalogoincidentes);
                catTincidCodigo = em.merge(catTincidCodigo);
            }
            for (BglTbEmergencia bglTbEmergenciaListBglTbEmergencia : bglTbCatalogoincidentes.getBglTbEmergenciaList()) {
                BglTbCatalogoincidentes oldEmTincCodigoOfBglTbEmergenciaListBglTbEmergencia = bglTbEmergenciaListBglTbEmergencia.getEmTincCodigo();
                bglTbEmergenciaListBglTbEmergencia.setEmTincCodigo(bglTbCatalogoincidentes);
                bglTbEmergenciaListBglTbEmergencia = em.merge(bglTbEmergenciaListBglTbEmergencia);
                if (oldEmTincCodigoOfBglTbEmergenciaListBglTbEmergencia != null) {
                    oldEmTincCodigoOfBglTbEmergenciaListBglTbEmergencia.getBglTbEmergenciaList().remove(bglTbEmergenciaListBglTbEmergencia);
                    oldEmTincCodigoOfBglTbEmergenciaListBglTbEmergencia = em.merge(oldEmTincCodigoOfBglTbEmergenciaListBglTbEmergencia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBglTbCatalogoincidentes(bglTbCatalogoincidentes.getCatCodigo()) != null) {
                throw new PreexistingEntityException("BglTbCatalogoincidentes " + bglTbCatalogoincidentes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BglTbCatalogoincidentes bglTbCatalogoincidentes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BglTbCatalogoincidentes persistentBglTbCatalogoincidentes = em.find(BglTbCatalogoincidentes.class, bglTbCatalogoincidentes.getCatCodigo());
            BglTbTipoincidente catTincidCodigoOld = persistentBglTbCatalogoincidentes.getCatTincidCodigo();
            BglTbTipoincidente catTincidCodigoNew = bglTbCatalogoincidentes.getCatTincidCodigo();
            List<BglTbEmergencia> bglTbEmergenciaListOld = persistentBglTbCatalogoincidentes.getBglTbEmergenciaList();
            List<BglTbEmergencia> bglTbEmergenciaListNew = bglTbCatalogoincidentes.getBglTbEmergenciaList();
            List<String> illegalOrphanMessages = null;
            for (BglTbEmergencia bglTbEmergenciaListOldBglTbEmergencia : bglTbEmergenciaListOld) {
                if (!bglTbEmergenciaListNew.contains(bglTbEmergenciaListOldBglTbEmergencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BglTbEmergencia " + bglTbEmergenciaListOldBglTbEmergencia + " since its emTincCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (catTincidCodigoNew != null) {
                catTincidCodigoNew = em.getReference(catTincidCodigoNew.getClass(), catTincidCodigoNew.getTincidCodigo());
                bglTbCatalogoincidentes.setCatTincidCodigo(catTincidCodigoNew);
            }
            List<BglTbEmergencia> attachedBglTbEmergenciaListNew = new ArrayList<BglTbEmergencia>();
            for (BglTbEmergencia bglTbEmergenciaListNewBglTbEmergenciaToAttach : bglTbEmergenciaListNew) {
                bglTbEmergenciaListNewBglTbEmergenciaToAttach = em.getReference(bglTbEmergenciaListNewBglTbEmergenciaToAttach.getClass(), bglTbEmergenciaListNewBglTbEmergenciaToAttach.getEmIdfuente());
                attachedBglTbEmergenciaListNew.add(bglTbEmergenciaListNewBglTbEmergenciaToAttach);
            }
            bglTbEmergenciaListNew = attachedBglTbEmergenciaListNew;
            bglTbCatalogoincidentes.setBglTbEmergenciaList(bglTbEmergenciaListNew);
            bglTbCatalogoincidentes = em.merge(bglTbCatalogoincidentes);
            if (catTincidCodigoOld != null && !catTincidCodigoOld.equals(catTincidCodigoNew)) {
                catTincidCodigoOld.getBglTbCatalogoincidentesList().remove(bglTbCatalogoincidentes);
                catTincidCodigoOld = em.merge(catTincidCodigoOld);
            }
            if (catTincidCodigoNew != null && !catTincidCodigoNew.equals(catTincidCodigoOld)) {
                catTincidCodigoNew.getBglTbCatalogoincidentesList().add(bglTbCatalogoincidentes);
                catTincidCodigoNew = em.merge(catTincidCodigoNew);
            }
            for (BglTbEmergencia bglTbEmergenciaListNewBglTbEmergencia : bglTbEmergenciaListNew) {
                if (!bglTbEmergenciaListOld.contains(bglTbEmergenciaListNewBglTbEmergencia)) {
                    BglTbCatalogoincidentes oldEmTincCodigoOfBglTbEmergenciaListNewBglTbEmergencia = bglTbEmergenciaListNewBglTbEmergencia.getEmTincCodigo();
                    bglTbEmergenciaListNewBglTbEmergencia.setEmTincCodigo(bglTbCatalogoincidentes);
                    bglTbEmergenciaListNewBglTbEmergencia = em.merge(bglTbEmergenciaListNewBglTbEmergencia);
                    if (oldEmTincCodigoOfBglTbEmergenciaListNewBglTbEmergencia != null && !oldEmTincCodigoOfBglTbEmergenciaListNewBglTbEmergencia.equals(bglTbCatalogoincidentes)) {
                        oldEmTincCodigoOfBglTbEmergenciaListNewBglTbEmergencia.getBglTbEmergenciaList().remove(bglTbEmergenciaListNewBglTbEmergencia);
                        oldEmTincCodigoOfBglTbEmergenciaListNewBglTbEmergencia = em.merge(oldEmTincCodigoOfBglTbEmergenciaListNewBglTbEmergencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bglTbCatalogoincidentes.getCatCodigo();
                if (findBglTbCatalogoincidentes(id) == null) {
                    throw new NonexistentEntityException("The bglTbCatalogoincidentes with id " + id + " no longer exists.");
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
            BglTbCatalogoincidentes bglTbCatalogoincidentes;
            try {
                bglTbCatalogoincidentes = em.getReference(BglTbCatalogoincidentes.class, id);
                bglTbCatalogoincidentes.getCatCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bglTbCatalogoincidentes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<BglTbEmergencia> bglTbEmergenciaListOrphanCheck = bglTbCatalogoincidentes.getBglTbEmergenciaList();
            for (BglTbEmergencia bglTbEmergenciaListOrphanCheckBglTbEmergencia : bglTbEmergenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This BglTbCatalogoincidentes (" + bglTbCatalogoincidentes + ") cannot be destroyed since the BglTbEmergencia " + bglTbEmergenciaListOrphanCheckBglTbEmergencia + " in its bglTbEmergenciaList field has a non-nullable emTincCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            BglTbTipoincidente catTincidCodigo = bglTbCatalogoincidentes.getCatTincidCodigo();
            if (catTincidCodigo != null) {
                catTincidCodigo.getBglTbCatalogoincidentesList().remove(bglTbCatalogoincidentes);
                catTincidCodigo = em.merge(catTincidCodigo);
            }
            em.remove(bglTbCatalogoincidentes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BglTbCatalogoincidentes> findBglTbCatalogoincidentesEntities() {
        return findBglTbCatalogoincidentesEntities(true, -1, -1);
    }

    public List<BglTbCatalogoincidentes> findBglTbCatalogoincidentesEntities(int maxResults, int firstResult) {
        return findBglTbCatalogoincidentesEntities(false, maxResults, firstResult);
    }

    private List<BglTbCatalogoincidentes> findBglTbCatalogoincidentesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BglTbCatalogoincidentes.class));
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

    public BglTbCatalogoincidentes findBglTbCatalogoincidentes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BglTbCatalogoincidentes.class, id);
        } finally {
            em.close();
        }
    }

    public int getBglTbCatalogoincidentesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BglTbCatalogoincidentes> rt = cq.from(BglTbCatalogoincidentes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
