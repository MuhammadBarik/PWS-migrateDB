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
public class AnggotaJpaController implements Serializable {

    public AnggotaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("iniperpus_INIPERPUS_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Anggota anggota) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(anggota);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAnggota(anggota.getIdAnggota()) != null) {
                throw new PreexistingEntityException("Anggota " + anggota + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Anggota anggota) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            anggota = em.merge(anggota);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = anggota.getIdAnggota();
                if (findAnggota(id) == null) {
                    throw new NonexistentEntityException("The anggota with id " + id + " no longer exists.");
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
            Anggota anggota;
            try {
                anggota = em.getReference(Anggota.class, id);
                anggota.getIdAnggota();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The anggota with id " + id + " no longer exists.", enfe);
            }
            em.remove(anggota);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Anggota> findAnggotaEntities() {
        return findAnggotaEntities(true, -1, -1);
    }

    public List<Anggota> findAnggotaEntities(int maxResults, int firstResult) {
        return findAnggotaEntities(false, maxResults, firstResult);
    }

    private List<Anggota> findAnggotaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Anggota.class));
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

    public Anggota findAnggota(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Anggota.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnggotaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Anggota> rt = cq.from(Anggota.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
