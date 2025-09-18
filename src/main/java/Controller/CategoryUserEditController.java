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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import Service.ServiceImpl.*;

@WebServlet(urlPatterns = { "/user/home/categories/add", "/user/home/categories/update", "/user/home/categories/delete" })
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10) // 5MB file, 10MB request
public class CategoryUserEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Khai báo service để giao tiếp với tầng nghiệp vụ
	private final CategoryService categoryService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy URI của yêu cầu và loại bỏ phần context path
		String path = req.getRequestURI().substring(req.getContextPath().length());

		switch (path) {
		case "/user/home/categories/add":
			doGetAdd(req, resp);
			break;
		case "/user/home/categories/update":
			doGetUpdate(req, resp);
			break;
		case "/user/home/categories/delete":
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
		case "/user/home/categories/add":
			doPostAdd(req, resp);
			break;
		case "/user/home/categories/update":
			doPostUpdate(req, resp);
			break;
		default:
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	private void doGetAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/user-category-add.jsp");
		dispatcher.forward(req, resp);
	}

	private void doPostAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Category category = new Category();
		UserService usservice = new UserServiceImpl();
		try {
			resp.setContentType("text/html; charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");

			// Get form field "name"
			String name = req.getParameter("name");
			if (name == null || name.trim().isEmpty()) {
				throw new ServletException("Tên danh mục là bắt buộc");
			}

			category.setCategoryName(name.trim());

			HttpSession session = req.getSession();
			User us = (User) session.getAttribute("account");

			category.setUser(us);

			// Handle file upload for "icon"
			Part filePart = req.getPart("icon");
			if (filePart != null && filePart.getSize() > 0) {
				String originalFileName = new File(filePart.getSubmittedFileName()).getName();
				int index = originalFileName.lastIndexOf(".");
				String ext = index > 0 ? originalFileName.substring(index + 1).toLowerCase() : "";
				if (!ext.matches("jpg|jpeg|png|gif")) {
					throw new ServletException("Chỉ cho phép các tệp JPG, JPEG, PNG, GIF");
				}
				String fileName = System.currentTimeMillis() + "." + ext;
				File uploadDir = new File(ConstantUploadImages.DIR, "category");
				Files.createDirectories(uploadDir.toPath());
				File file = new File(uploadDir, fileName);
				FileUtils.copyInputStreamToFile(filePart.getInputStream(), file);
				category.setIcon("category/" + fileName);
			}

			// Insert category into database
			categoryService.create(category);

		
				resp.sendRedirect(req.getContextPath() + "/user/home");
			
		} catch (ServletException e) {
			req.setAttribute("error", e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/add-category.jsp");
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("error", "Lỗi khi xử lý yêu cầu: " + e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/add-category.jsp");
			dispatcher.forward(req, resp);
		}
	}

	private void doGetDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy ID của danh mục từ request parameter
		String idParam = req.getParameter("id");

		if (idParam != null && !idParam.isEmpty()) {
			try {
				int id = Integer.parseInt(idParam);
				// Gọi tầng Service để thực hiện xóa danh mục
				categoryService.delete(id);

				HttpSession session = req.getSession();
				User us = (User) session.getAttribute("account");
				
					resp.sendRedirect(req.getContextPath() + "/user/home");
				

			} catch (NumberFormatException e) {
				// Xử lý lỗi nếu ID không phải là số
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category ID format.");
			}
		} else {
			// Xử lý lỗi nếu không có ID được cung cấp
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing category ID.");
		}
	}

	private void doGetUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String id = req.getParameter("id");
			if (id == null || id.trim().isEmpty()) {
				throw new ServletException("ID danh mục là bắt buộc");
			}
			int cateId;
			try {
				cateId = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				throw new ServletException("ID danh mục không hợp lệ");
			}
			Category category = categoryService.findById(cateId);
			if (category == null) {
				throw new ServletException("Không tìm thấy danh mục với ID: " + cateId);
			}
			req.setAttribute("category", category);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/user-category-update.jsp");
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			req.setAttribute("error", e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/user-category-update.jsp");
			dispatcher.forward(req, resp);
		}
	}

	private void doPostUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Category category = new Category();
		try {
			resp.setContentType("text/html; charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");

			// Get form fields
			String id = req.getParameter("id");
			if (id == null || id.trim().isEmpty()) {
				throw new ServletException("ID danh mục là bắt buộc");
			}
			try {
				category = categoryService.findById(Integer.parseInt(id));
			} catch (NumberFormatException e) {
				throw new ServletException("ID danh mục không hợp lệ");
			}

			String name = req.getParameter("name");
			if (name == null || name.trim().isEmpty()) {
				throw new ServletException("Tên danh mục là bắt buộc");
			}
			category.setCategoryName(name.trim());

			// Handle file upload for "icon"
			Part filePart = req.getPart("icon");
			if (filePart != null && filePart.getSize() > 0) {
				String originalFileName = new File(filePart.getSubmittedFileName()).getName();
				int index = originalFileName.lastIndexOf(".");
				String ext = index > 0 ? originalFileName.substring(index + 1).toLowerCase() : "";
				if (!ext.matches("jpg|jpeg|png|gif")) {
					throw new ServletException("Chỉ cho phép các tệp JPG, JPEG, PNG, GIF");
				}
				String fileName = System.currentTimeMillis() + "." + ext;
				File uploadDir = new File(ConstantUploadImages.DIR, "category");
				Files.createDirectories(uploadDir.toPath());
				File file = new File(uploadDir, fileName);
				FileUtils.copyInputStreamToFile(filePart.getInputStream(), file);
				category.setIcon("category/" + fileName);
			} else {
				// Retain existing icon if no new file is uploaded
				Category existingCategory = categoryService.getCategoryId(category.getId());
				if (existingCategory != null) {
					category.setIcon(existingCategory.getIcon());
				}
			}

			// Update category in database
			categoryService.update(category);
			HttpSession session = req.getSession();
			User us = (User) session.getAttribute("account");
	
				resp.sendRedirect(req.getContextPath() + "/user/home");
			

		} catch (ServletException e) {
			req.setAttribute("error", e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/user-category-update.jsp");
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("error", "Lỗi khi xử lý yêu cầu: " + e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/user-category-update.jsp");
			dispatcher.forward(req, resp);
		}
	}
}