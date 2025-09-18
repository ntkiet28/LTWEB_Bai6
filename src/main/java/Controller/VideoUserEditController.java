package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.io.FileUtils;

import Constant.ConstantUploadVideos;
import Entity.Category;
import Entity.Video;
import Service.CategoryService;
import Service.VideoService;
import Service.ServiceImpl.CategoryServiceImpl;
import Service.ServiceImpl.VideoServiceImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(urlPatterns = { "/user/home/videos/add", "/user/home/videos/update", "/user/home/videos/delete" })
@MultipartConfig(maxFileSize = 1024 * 1024 * 50, 
		maxRequestSize = 1024 * 1024 * 100) 
public class VideoUserEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final VideoService videoService = new VideoServiceImpl();
	private final CategoryService categoryService = new CategoryServiceImpl();

	// ==== GET ====
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI().substring(req.getContextPath().length());

		switch (path) {
		case "/user/home/videos/add":
			doGetAdd(req, resp);
			break;
		case "/user/home/videos/update":
			doGetUpdate(req, resp);
			break;
		case "/user/home/videos/delete":
			doGetDelete(req, resp);
			break;
		default:
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	// ==== POST ====
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI().substring(req.getContextPath().length());

		switch (path) {
		case "/user/home/videos/add":
			doPostAdd(req, resp);
			break;
		case "/user/home/videos/update":
			doPostUpdate(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/user/home/videos");
			break;
		}
	}

	// ==== ADD ====
	private void doGetAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Category> categories = categoryService.getListAll();
		req.setAttribute("categories", categories);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/user-video-add.jsp");
		dispatcher.forward(req, resp);
	}

	private void doPostAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");

			String title = req.getParameter("title");
			String description = req.getParameter("description");
			int views = Integer.parseInt(req.getParameter("views"));
			int categoryId = Integer.parseInt(req.getParameter("categoryId"));

			Category category = categoryService.findById(categoryId);

			Video video = new Video();
			video.setTitle(title);
			video.setDescription(description);
			video.setViews(views);
			video.setCategory(category);

			// ==== Upload file video ====
			Part filePart = req.getPart("videoFile");
			if (filePart != null && filePart.getSize() > 0) {
				String originalFileName = new File(filePart.getSubmittedFileName()).getName();
				int index = originalFileName.lastIndexOf(".");
				String ext = index > 0 ? originalFileName.substring(index + 1).toLowerCase() : "";
				if (!ext.matches("mp4|avi|mkv")) {
					throw new ServletException("Chỉ cho phép file video: mp4, avi, mkv");
				}
				String fileName = System.currentTimeMillis() + "." + ext;
				File uploadDir = new File(ConstantUploadVideos.DIR, "videos");
				Files.createDirectories(uploadDir.toPath());
				File file = new File(uploadDir, fileName);
				FileUtils.copyInputStreamToFile(filePart.getInputStream(), file);
				video.setFilePath("videos/" + fileName);
			}

			videoService.create(video);

			resp.sendRedirect(req.getContextPath() + "/user/home/videos");

		} catch (Exception e) {
			req.setAttribute("error", "Lỗi khi thêm video: " + e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/user-video-add.jsp");
			dispatcher.forward(req, resp);
		}
	}

	// ==== DELETE ====
	private void doGetDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idParam = req.getParameter("id");

		if (idParam != null && !idParam.isEmpty()) {
			try {
				Long id = Long.parseLong(idParam);
				videoService.delete(id);
				resp.sendRedirect(req.getContextPath() + "/user/home/videos");
			} catch (NumberFormatException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid video ID format.");
			}
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing video ID.");
		}
	}

	// ==== UPDATE ====
	private void doGetUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String id = req.getParameter("id");
			if (id == null || id.trim().isEmpty()) {
				throw new ServletException("ID video là bắt buộc");
			}
			int videoId = Integer.parseInt(id);

			Video video = videoService.findById(videoId);
			if (video == null) {
				throw new ServletException("Không tìm thấy video với ID: " + videoId);
			}

			List<Category> categories = categoryService.getListAll();
			req.setAttribute("categories", categories);
			req.setAttribute("video", video);

			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/user-video-update.jsp");
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("error", e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/user-video-update.jsp");
			dispatcher.forward(req, resp);
		}
	}

	private void doPostUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");

			int videoId = Integer.parseInt(req.getParameter("id"));
			Video video = videoService.findById(videoId);
			if (video == null) {
				throw new ServletException("Video không tồn tại");
			}

			String title = req.getParameter("title");
			String description = req.getParameter("description");
			int views = Integer.parseInt(req.getParameter("views"));
			int categoryId = Integer.parseInt(req.getParameter("categoryId"));

			Category category = categoryService.findById(categoryId);

			video.setTitle(title);
			video.setDescription(description);
			video.setViews(views);
			video.setCategory(category);

			// ==== Upload file video mới (nếu có) ====
			Part filePart = req.getPart("videoFile");
			if (filePart != null && filePart.getSize() > 0) {
				String originalFileName = new File(filePart.getSubmittedFileName()).getName();
				int index = originalFileName.lastIndexOf(".");
				String ext = index > 0 ? originalFileName.substring(index + 1).toLowerCase() : "";
				if (!ext.matches("mp4|avi|mkv")) {
					throw new ServletException("Chỉ cho phép file video: mp4, avi, mkv");
				}
				String fileName = System.currentTimeMillis() + "." + ext;
				File uploadDir = new File(ConstantUploadVideos.DIR, "videos");
				Files.createDirectories(uploadDir.toPath());
				File file = new File(uploadDir, fileName);
				FileUtils.copyInputStreamToFile(filePart.getInputStream(), file);
				video.setFilePath("videos/" + fileName);
			}

			videoService.update(video);

			resp.sendRedirect(req.getContextPath() + "/user/home/videos");

		} catch (Exception e) {
			req.setAttribute("error", "Lỗi khi cập nhật video: " + e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/user-video-update.jsp");
			dispatcher.forward(req, resp);
		}
	}
}