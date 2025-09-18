package Service;

import java.util.List;

import Entity.Category;

public interface CategoryService {
	List<Category> findByUserId(Integer user_id);

	void create(Category category);

	void update(Category category);

	void delete(int id);

	Category findById(int id);

	Category getCategoryId(int id);	
	
	List<Category> getListAll();
	
	List<Category> searchByName(String keyword);
}