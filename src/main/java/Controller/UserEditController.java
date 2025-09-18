package Controller;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;

import Constant.ConstantUploadImages;
import Entity.Category;
import Entity.User;
import Service.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Service.ServiceImpl.*;

@WebServlet(urlPatterns = { "/admin/home/users/add", "/admin/home/users/update", "/admin/home/users/delete" })
public class UserEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final UserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy URI của yêu cầu và loại bỏ phần context path
		String path = req.getRequestURI().substring(req.getContextPath().length());

		switch (path) {
		case "/admin/home/users/add":
			doGetAdd(req, resp);
			break;
		case "/admin/home/users/update":
			doGetUpdate(req, resp);
			break;
		case "/admin/home/users/delete":
			doGetDelete(req, resp);
			break;

		default:
			// Xử lý trường hợp URL không hợp lệ
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy URI của yêu cầu và loại bỏ phần context path
		String path = req.getRequestURI().substring(req.getContextPath().length());

		switch (path) {
		case "/admin/home/users/add":
			doPostAdd(req, resp);
			break;
		case "/admin/home/users/update":
			doPostUpdate(req, resp);
			break;
		default:
			
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	private void doGetAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/user-add.jsp");
		dispatcher.forward(req, resp);
	}

	private void doPostAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	
		User user = new User();
		try {
			resp.setContentType("text/html; charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");

			// Get form field "name"
			String name = req.getParameter("username");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			int  roleid = Integer.parseInt(req.getParameter("roleId"));
			if (name == null || name.trim().isEmpty()) {
				throw new ServletException("Tên user là bắt buộc");
			}
			if (password == null || password.trim().isEmpty()) {
				throw new ServletException("Password user là bắt buộc");
			}
			if (email== null || email.trim().isEmpty()) {
				throw new ServletException("Email user là bắt buộc");
			}
			user.setUsername(name.trim());
			user.setPassword(password.trim());
			user.setEmail(email.trim());
			user.setRoleId(roleid);

			userService.create(user);
			resp.sendRedirect(req.getContextPath() + "/admin/home/users");
		

		} catch (ServletException e) {
			req.setAttribute("error", e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/user-add.jsp");
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("error", "Lỗi khi xử lý yêu cầu: " + e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/user-add.jsp");
			dispatcher.forward(req, resp);
		}
	}

	private void doGetDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy ID của danh mục từ request parameter
		String idParam = req.getParameter("id");

		if (idParam != null && !idParam.isEmpty()) {
			try {
				int id = Integer.parseInt(idParam);
				// Gọi tầng Service để thực hiện xóa 
				userService.delete(id);
				resp.sendRedirect(req.getContextPath() + "/admin/home/users");
			
			} catch (NumberFormatException e) {
				// Xử lý lỗi nếu ID không phải là số
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format.");
			}
		} else {
			// Xử lý lỗi nếu không có ID được cung cấp
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing user ID.");
		}
	}

	private void doGetUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String id = req.getParameter("id");
			if (id == null || id.trim().isEmpty()) {
				throw new ServletException("ID userlà bắt buộc");
			}
			int userId;
			try {
				userId = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				throw new ServletException("ID user không hợp lệ");
			}
			User user =userService.findById(userId);
			if (user == null) {
				throw new ServletException("Không tìm thấy user với ID: " + userId);
			}
			req.setAttribute("userget", user);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/user-update.jsp");
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			req.setAttribute("error", e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/user-update.jsp");
			dispatcher.forward(req, resp);
		}
	}

	private void doPostUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User();
		try {
			resp.setContentType("text/html; charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");

			// Get form fields
			String id = req.getParameter("id");
			if (id == null || id.trim().isEmpty()) {
				throw new ServletException("ID user là bắt buộc");
			}
			try {
				user = userService.findById(Integer.parseInt(id));
			} catch (NumberFormatException e) {
				throw new ServletException("ID user không hợp lệ");
			}

			String name = req.getParameter("username");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			int  roleid = Integer.parseInt(req.getParameter("roleId"));
			if (name == null || name.trim().isEmpty()) {
				throw new ServletException("Tên user là bắt buộc");
			}
			if (password == null || password.trim().isEmpty()) {
				throw new ServletException("Password user là bắt buộc");
			}
			if (email== null || email.trim().isEmpty()) {
				throw new ServletException("Email user là bắt buộc");
			}
		
			user.setUsername(name.trim());
			user.setPassword(password.trim());
			user.setEmail(email.trim());
			user.setRoleId(roleid);

			// Update category in database
			userService.update(user);
				resp.sendRedirect(req.getContextPath() + "/admin/home/users");
			

		} catch (ServletException e) {
			req.setAttribute("error", e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/user-update.jsp");
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("error", "Lỗi khi xử lý yêu cầu: " + e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/user-update.jsp");
			dispatcher.forward(req, resp);
		}
	}
}