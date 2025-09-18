package Entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity // Đánh dấu đây là một Entity JPA
@Table(name = "users") // Ánh xạ tới bảng 'users' trong database
public class User implements Serializable {
    
    // Serial Version UID giúp đảm bảo tính nhất quán khi serialize
    private static final long serialVersionUID = 1L;

    @Id // Đánh dấu đây là khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
    private int id;
    
    @Column(name = "username", unique = true, nullable = false) // Tên cột, yêu cầu duy nhất và không rỗng
    private String username;
    
    @Column(name = "password", nullable = false) // Tên cột, không rỗng
    private String password;
    
    @Column(name = "email", unique = true, nullable = false) // Tên cột, yêu cầu duy nhất và không rỗng
    private String email;
    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    @Column(name = "role_id")
    private int roleId =  2;


    @OneToMany(mappedBy = "user") // user này là private User user; bên Category
    private List<Category> categories;
    
    

    // Constructors
    public User() {
    }
    
    public User(String username, String password, String email) {
    	this.username = username;
    	this.password = password;
    	this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}