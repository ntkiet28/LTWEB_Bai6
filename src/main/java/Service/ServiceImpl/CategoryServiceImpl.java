package Service.ServiceImpl;

import java.util.List;
import Service.*;
import Dao.CategoryDao;
import Dao.DaoImpl.CategoryDaoImpl;
import Entity.Category;


public class CategoryServiceImpl implements CategoryService {
	
	// Khởi tạo một đối tượng DAO để giao tiếp với database
	// Thay vì gọi chính Service, ta gọi lớp DAO
	private CategoryDao categoryDao = new CategoryDaoImpl(); 
	
	@Override
	public List<Category> findByUserId(Integer user_id) {
		// Gọi phương thức tương ứng từ lớp DAO
		return categoryDao.findByUserId(user_id);
	}

	@Override
	public void create(Category category) {
		// Gọi DAO để tạo mới category
		categoryDao.create(category);
	}

	@Override
	public void update(Category category) {
		// Gọi DAO để cập nhật category
		categoryDao.update(category);
	}

	@Override
	public void delete(int id) {
		// Gọi DAO để xóa category theo ID
		categoryDao.delete(id);
	}

	@Override
	public Category findById(int id) {
		// Gọi DAO để tìm category theo ID
		return categoryDao.findById(id);
	}
	
	public Category getCategoryId(int id) {
		// Gọi DAO để tìm category theo ID
		return categoryDao.getCategoryId(id);
	}

	@Override
	public List<Category> getListAll() {
		return categoryDao.getListAll();
	}

	@Override
	public List<Category> searchByName(String keyword) {
		return categoryDao.searchByName(keyword);
	}
}