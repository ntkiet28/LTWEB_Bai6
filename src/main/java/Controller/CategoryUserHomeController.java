package Controller;

import java.io.IOException;
import java.util.List;

import Service.CategoryService;
import Service.ServiceImpl.CategoryServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Cần thêm import này

import Entity.User; // Cần thêm import này nếu chưa có

@WebServlet(urlPatterns = { "/user/home/categories" })
public class CategoryUserHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryService cateService = new CategoryServiceImpl();
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Lấy session hiện tại
		HttpSession session = req.getSession();
		User loggedInUser = (User) session.getAttribute("account");
		Integer user_id;
		if (loggedInUser != null) {
			// Lấy ID của người dùng từ đối tượng đã lấy được
			user_id = loggedInUser.getId();
			
			List<Entity.Category> cateList = cateService.findByUserId(user_id);
			req.setAttribute("cateList", cateList); //req về cái cateList thì nó lại load lại trang jsp rồi 
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/listcategory.jsp");
			dispatcher.forward(req, resp);
		}
	}

}