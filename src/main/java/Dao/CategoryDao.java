package Dao;

import java.util.List;

import Entity.Category;

public interface CategoryDao {

	List<Category> findByUserId(Integer user_id);

	void create(Category category);

	void update(Category category);

	void delete(int id);

	Category findById(int id);
	Category getCategoryId(int id);	
	List<Category> getListAll();
	
	List<Category> searchByName(String keyword);

}