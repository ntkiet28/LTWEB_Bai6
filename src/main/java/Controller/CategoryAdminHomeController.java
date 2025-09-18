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


@WebServlet(urlPatterns = { "/admin/home/categories" })
public class CategoryAdminHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryService cateService = new CategoryServiceImpl();
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyword = req.getParameter("keyword");
		List<Entity.Category> cateList;
		
		if (keyword != null && !keyword.trim().isEmpty()) {
			cateList = cateService.searchByName(keyword);
			req.setAttribute("keyword", keyword);
		} else {
			cateList = cateService.getListAll();
		}
		
		req.setAttribute("cateList", cateList); 
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/listcategoryadmin.jsp");
		dispatcher.forward(req, resp);
	}
}