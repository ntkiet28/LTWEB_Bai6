package Controller;

import java.io.IOException;
import java.util.List;

import Entity.Video;
import Service.VideoService;
import Service.ServiceImpl.VideoServiceImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/user/home/videos" })
public class VideoUserHomeController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final VideoService videoService = new VideoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cateIdParam = req.getParameter("categoryId");

        if (cateIdParam == null || cateIdParam.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu categoryId");
            return;
        }

        try {
            int cateId = Integer.parseInt(cateIdParam);

            // Lấy danh sách video thuộc category
            List<Video> videos = videoService.findByIdRL(cateId);
            req.setAttribute("videoList", videos);
            req.setAttribute("cateId", cateId);

            // Forward sang JSP
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/user-video-list.jsp");
            dispatcher.forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "categoryId không hợp lệ");
        }
    }
}