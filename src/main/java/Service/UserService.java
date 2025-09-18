package Service;

import java.util.List;

import Entity.Category;
import Entity.User;

public interface UserService {
	User login(String username, String password);

	User getUserName(String username);
	

	void insert(User user);

	boolean register(String email, String password, String username);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);
	
	void setToken(String email, String token);
	
	boolean checkExistToken(String token);
	
	boolean updatePasswordWithToken(String token, String newPassword);
	
	User findById(Integer id);

	List<User> getListAll();

	void create(User user);

	void delete(int id);

	void update(User user);
	
	List<User> searchByUsername(String keyword);
	
	List<User> searchByEmail(String keyword);


}