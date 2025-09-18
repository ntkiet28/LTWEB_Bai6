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
@WebServlet(urlPatterns = { "/user/home" })
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	CategoryService cateService = new CategoryServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Entity.Category> cateList = cateService.getListAll();
		req.setAttribute("cateList", cateList); 
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/user-list-category.jsp");
		dispatcher.forward(req, resp);
	}
}