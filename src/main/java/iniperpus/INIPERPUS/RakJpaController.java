/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iniperpus.INIPERPUS;

import iniperpus.INIPERPUS.exceptions.NonexistentEntityException;
import iniperpus.INIPERPUS.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author LENOVO
 */
public class RakJpaController implements Serializable {

    public RakJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("iniperpus_INIPERPUS_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rak rak) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(rak);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRak(rak.getIdRak()) != null) {
                throw new PreexistingEntityException("Rak " + rak + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rak rak) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            rak = em.merge(rak);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = rak.getIdRak();
                if (findRak(id) == null) {
                    throw new NonexistentEntityException("The rak with id " + id + " no longer exists.");
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
            Rak rak;
            try {
                rak = em.getReference(Rak.class, id);
                rak.getIdRak();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rak with id " + id + " no longer exists.", enfe);
            }
            em.remove(rak);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rak> findRakEntities() {
        return findRakEntities(true, -1, -1);
    }

    public List<Rak> findRakEntities(int maxResults, int firstResult) {
        return findRakEntities(false, maxResults, firstResult);
    }

    private List<Rak> findRakEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rak.class));
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

    public Rak findRak(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rak.class, id);
        } finally {
            em.close();
        }
    }

    public int getRakCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rak> rt = cq.from(Rak.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
