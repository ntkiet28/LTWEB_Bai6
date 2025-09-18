package Dao.DaoImpl;

import java.util.Collections;
import java.util.List;

import Configs.Database.JPAConfig;
import Dao.CategoryDao;
import Entity.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findByUserId(Integer user_id) {
		
		 EntityManager enma = null;
	        try {
	            enma = JPAConfig.getEntityManager();
	            
	            // Viết JPQL để lấy tất cả Category thuộc về User có userId cho trước
	            // Giả định Category entity có một trường là 'user' liên kết đến User
	            String jpql = "SELECT c FROM Category c WHERE c.user.id = :userId";
	            
	            // Tạo truy vấn có kiểu an toàn
	            TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
	            query.setParameter("userId", user_id);
	            
	            // Trả về danh sách kết quả
	            return query.getResultList();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Nếu có lỗi, trả về một danh sách rỗng thay vì null để tránh lỗi NullPointerException
	            return Collections.emptyList();
	            
	        } finally {
	            if (enma != null && enma.isOpen()) {
	                enma.close();
	            }
	        }
	    }
	@Override
	public List<Category> getListAll() {
		
		 EntityManager enma = null;
	        try {
	            enma = JPAConfig.getEntityManager();
	            

	            String jpql = "SELECT c FROM Category c "; // lấy tất cả category
	            
	            // Tạo truy vấn có kiểu an toàn
	            TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
	            // Trả về danh sách kết quả
	            return query.getResultList();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Nếu có lỗi, trả về một danh sách rỗng thay vì null để tránh lỗi NullPointerException
	            return Collections.emptyList();
	            
	        } finally {
	            if (enma != null && enma.isOpen()) {
	                enma.close();
	            }
	        }
	    }
	

	@Override
	public void create(Category category) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.persist(category); // Thêm đối tượng vào CSDL
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (trans.isActive()) {
				trans.rollback(); // Nếu có lỗi, rollback để hủy giao dịch
			}
		} finally {
			enma.close();
		}
	}

	@Override
	public void update(Category category) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.merge(category); // Cập nhật đối tượng vào CSDL
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (trans.isActive()) {
				trans.rollback();
			}
		} finally {
			enma.close();
		}
	}

	@Override
	public void delete(int id) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			// Tìm đối tượng cần xóa
			Category category = enma.find(Category.class, id);
			if (category != null) {
				enma.remove(category); // Xóa đối tượng
			}
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (trans.isActive()) {
				trans.rollback();
			}
		} finally {
			enma.close();
		}
	}

	@Override
	public Category findById(int id) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			// Tìm đối tượng theo ID
			return enma.find(Category.class, id);
		} finally {
			enma.close();
		}
	}
	
	
	@Override
	public Category getCategoryId(int id) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			// Tìm đối tượng theo ID
			return enma.find(Category.class, id);
		} finally {
			enma.close();
		}
	}

	@Override
	public List<Category> searchByName(String keyword) {
		EntityManager enma = null;
		try {
			enma = JPAConfig.getEntityManager();
			String jpql = "SELECT c FROM Category c WHERE c.categoryName LIKE :keyword";
			TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
			query.setParameter("keyword", "%" + keyword + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			if (enma != null && enma.isOpen()) {
				enma.close();
			}
		}
	}
}