package Dao.DaoImpl;

import java.util.Collections;
import java.util.List;

import Configs.Database.JPAConfig;

import Dao.UserDao;

import Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class UserDaoImpl implements UserDao {

	@Override
	public User getUserName(String username) {

		EntityManager enma = JPAConfig.getEntityManager();
		// EntityTransaction trans = enma.getTransaction(); //chỉ thêm khi thêm, xóa,
		// sửa
		try {

			String jpql = "SELECT u FROM User u WHERE u.username = :username_find";
			User user = enma.createQuery(jpql, User.class).setParameter("username_find", username).getSingleResult();
			return user;

		} catch (NoResultException e) {
			// Xử lý trường hợp không tìm thấy người dùng
			System.out.println("Không tìm thấy người dùng với username: " + username);
			return null; // Trả về null nếu không tìm thấy
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (enma != null && enma.isOpen()) {
				enma.close();
			}
		}

	}

	@Override
	public User findById(Integer id) {

		EntityManager enma = JPAConfig.getEntityManager();
		// EntityTransaction trans = enma.getTransaction(); //chỉ thêm khi thêm, xóa,
		// sửa
		try {

			String jpql = "SELECT u FROM User u WHERE u.id = :id_find";
			User user = enma.createQuery(jpql, User.class).setParameter("id_find", id).getSingleResult();
			return user;

		} catch (NoResultException e) {
			// Xử lý trường hợp không tìm thấy người dùng
			System.out.println("Không tìm thấy người dùng với id: " + id);
			return null; // Trả về null nếu không tìm thấy
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (enma != null && enma.isOpen()) {
				enma.close();
			}
		}

	}

	@Override
	public void insert(User user) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin(); // khởi tạo giao dịch
			enma.persist(user); // Thêm đối tượng vào CSDL
			trans.commit(); // lưu vào cơ sở dữ liệu
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
	public boolean checkExistEmail(String email) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT u FROM User u WHERE u.email = :email_find";
			User user = enma.createQuery(jpql, User.class).setParameter("email_find", email).getSingleResult();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		} finally {
			enma.close();
		}
	}

	@Override
	public boolean checkExistUsername(String username) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT u FROM User u WHERE u.username = :username_find";
			User user = enma.createQuery(jpql, User.class).setParameter("email_find", username).getSingleResult();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		} finally {
			enma.close();
		}

	}

	@Override
	public void setToken(String email, String token) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin(); // Start the transaction

			// JPQL to update the user's password and clear the token
			String jpql = "UPDATE User u SET u.resetPasswordToken = :new_token  WHERE u.email= :email_find";

			// Execute the update query
			enma.createQuery(jpql).setParameter("new_token", token).setParameter("email_find", email).executeUpdate();

			trans.commit(); // Commit the transaction if successful

		} catch (Exception e) {
			// Handle potential errors
			if (trans != null && trans.isActive()) {
				trans.rollback(); // Rollback the transaction on error
			}
			e.printStackTrace();

		} finally {
			if (enma != null && enma.isOpen()) {
				enma.close(); // Always close the EntityManager
			}
		}
	}

	@Override
	public boolean checkExistToken(String token) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT u FROM User u WHERE u.resetPasswordToken = :token_check";
			enma.createQuery(jpql, User.class).setParameter("token_check", token).getSingleResult();
			// Nếu không có ngoại lệ, tức là tìm thấy user
			return true;
		} catch (jakarta.persistence.NoResultException e) {
			// Xử lý riêng trường hợp không tìm thấy kết quả
			return false;
		} catch (Exception e) {
			// Bắt các ngoại lệ khác (ví dụ: lỗi kết nối DB, lỗi truy vấn...)
			e.printStackTrace();
			return false;
		} finally {
			enma.close();
		}
	}

	@Override
	public boolean updatePasswordWithToken(String token, String newPassword) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin(); // Start the transaction

			// JPQL to update the user's password and clear the token
			String jpql = "UPDATE User u SET u.password = :newPassword, u.resetPasswordToken = NULL WHERE u.resetPasswordToken = :token";

			// Execute the update query
			int updatedCount = enma.createQuery(jpql).setParameter("newPassword", newPassword)
					.setParameter("token", token).executeUpdate();

			trans.commit(); // Commit the transaction if successful

			// Return true if exactly one user was updated
			return updatedCount == 1;

		} catch (Exception e) {
			// Handle potential errors
			if (trans != null && trans.isActive()) {
				trans.rollback(); // Rollback the transaction on error
			}
			e.printStackTrace();
			return false;
		} finally {
			if (enma != null && enma.isOpen()) {
				enma.close(); // Always close the EntityManager
			}
		}
	}

	@Override
	public List<User> getListAll() {

		EntityManager enma = null;
		try {
			enma = JPAConfig.getEntityManager();
			String jpql = "SELECT u FROM User u ";
			TypedQuery<User> query = enma.createQuery(jpql, User.class);

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

	@Override
	public void create(User user) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.persist(user); // Thêm đối tượng vào CSDL
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
	public void delete(int id) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			// Tìm đối tượng cần xóa
			User user = enma.find(User.class, id);
			if (user != null) {
				enma.remove(user); // Xóa đối tượng
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
	public void update(User user) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.merge(user); // Cập nhật đối tượng vào CSDL
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
	public List<User> searchByUsername(String keyword) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT u FROM User u WHERE u.username LIKE :keyword";
			TypedQuery<User> query = enma.createQuery(jpql, User.class);
			query.setParameter("keyword", "%" + keyword + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			enma.close();
		}
	}

	@Override
	public List<User> searchByEmail(String keyword) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT u FROM User u WHERE u.email LIKE :keyword";
			TypedQuery<User> query = enma.createQuery(jpql, User.class);
			query.setParameter("keyword", "%" + keyword + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			enma.close();
		}
	}

}