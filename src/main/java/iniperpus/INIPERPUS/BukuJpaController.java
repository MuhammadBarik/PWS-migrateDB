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
public class BukuJpaController implements Serializable {

    public BukuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("iniperpus_INIPERPUS_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Buku buku) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(buku);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBuku(buku.getIdBuku()) != null) {
                throw new PreexistingEntityException("Buku " + buku + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Buku buku) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            buku = em.merge(buku);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = buku.getIdBuku();
                if (findBuku(id) == null) {
                    throw new NonexistentEntityException("The buku with id " + id + " no longer exists.");
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
            Buku buku;
            try {
                buku = em.getReference(Buku.class, id);
                buku.getIdBuku();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The buku with id " + id + " no longer exists.", enfe);
            }
            em.remove(buku);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Buku> findBukuEntities() {
        return findBukuEntities(true, -1, -1);
    }

    public List<Buku> findBukuEntities(int maxResults, int firstResult) {
        return findBukuEntities(false, maxResults, firstResult);
    }

    private List<Buku> findBukuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Buku.class));
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

    public Buku findBuku(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Buku.class, id);
        } finally {
            em.close();
        }
    }

    public int getBukuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Buku> rt = cq.from(Buku.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
