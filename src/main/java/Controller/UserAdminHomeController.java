package Controller;

import java.io.IOException;
import java.util.List;

import Entity.User;
import Service.UserService;
import Service.ServiceImpl.UserServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/admin/home/users" })
public class UserAdminHomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	UserService userService = new UserServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyword = req.getParameter("keyword");
		String searchType = req.getParameter("searchType");
		List<User> userList;
		
		if (keyword != null && !keyword.trim().isEmpty()) {
			if ("email".equals(searchType)) {
				userList = userService.searchByEmail(keyword);
			} else {
				userList = userService.searchByUsername(keyword);
			}
			req.setAttribute("keyword", keyword);
			req.setAttribute("searchType", searchType);
		} else {
			userList = userService.getListAll();
		}
		
		req.setAttribute("userList", userList); 
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/listuseradmin.jsp");
		dispatcher.forward(req, resp);
	}

}