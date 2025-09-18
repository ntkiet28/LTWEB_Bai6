package Dao.DaoImpl;



import java.util.Collections;
import java.util.List;

import Configs.Database.JPAConfig;
import Dao.VideoDao;
import Entity.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class VideoDaoImpl implements VideoDao {

    @Override
    public void create(Video video) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(video);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Video video) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(video);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Video video = em.find(Video.class, id);
            if (video != null) {
                em.remove(video);
            }
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public  List<Video>  findByIdRL(int id) {
    	 EntityManager em = JPAConfig.getEntityManager();
         try {
             String jpql = "SELECT v FROM Video v WHERE v.id= :id";
             TypedQuery<Video> query = em.createQuery(jpql, Video.class);
             query.setParameter("id", id);
             return query.getResultList();
         } catch (Exception e) {
             e.printStackTrace();
             return Collections.emptyList();
         } finally {
             em.close();
         }
    }

    @Override
    public List<Video> getListAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video v";
            TypedQuery<Video> query = em.createQuery(jpql, Video.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> searchByTitle(String keyword) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video v WHERE v.title LIKE :keyword";
            TypedQuery<Video> query = em.createQuery(jpql, Video.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    @Override
    public Video findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(Video.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

}