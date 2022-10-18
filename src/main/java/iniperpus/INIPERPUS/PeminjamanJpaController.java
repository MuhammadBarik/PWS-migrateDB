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
public class PeminjamanJpaController implements Serializable {

    public PeminjamanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("iniperpus_INIPERPUS_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Peminjaman peminjaman) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(peminjaman);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPeminjaman(peminjaman.getIdPeminjaman()) != null) {
                throw new PreexistingEntityException("Peminjaman " + peminjaman + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Peminjaman peminjaman) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            peminjaman = em.merge(peminjaman);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = peminjaman.getIdPeminjaman();
                if (findPeminjaman(id) == null) {
                    throw new NonexistentEntityException("The peminjaman with id " + id + " no longer exists.");
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
            Peminjaman peminjaman;
            try {
                peminjaman = em.getReference(Peminjaman.class, id);
                peminjaman.getIdPeminjaman();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The peminjaman with id " + id + " no longer exists.", enfe);
            }
            em.remove(peminjaman);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Peminjaman> findPeminjamanEntities() {
        return findPeminjamanEntities(true, -1, -1);
    }

    public List<Peminjaman> findPeminjamanEntities(int maxResults, int firstResult) {
        return findPeminjamanEntities(false, maxResults, firstResult);
    }

    private List<Peminjaman> findPeminjamanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Peminjaman.class));
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

    public Peminjaman findPeminjaman(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Peminjaman.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeminjamanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Peminjaman> rt = cq.from(Peminjaman.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
