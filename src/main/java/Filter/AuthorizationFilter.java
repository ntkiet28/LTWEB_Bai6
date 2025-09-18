package Filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/manager/*", "/user/*"})
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // Lấy session hiện tại, không tạo mới

        // 1. Kiểm tra xem người dùng đã đăng nhập chưa
        if (session == null || session.getAttribute("roleId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login"); // Chuyển hướng về trang đăng nhập
            return;
        }

        // 2. Lấy roleId và path của request
        int userRoleId = (int) session.getAttribute("roleId");
        String path = req.getRequestURI().substring(req.getContextPath().length());

        // 3. Phân quyền dựa trên URL
        if (path.startsWith("/admin") && userRoleId != 0) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
            return;
        } else if (path.startsWith("/manager") && userRoleId != 1 && userRoleId != 0) { // Admin cũng có thể truy cập trang của manager
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        } else if (path.startsWith("/user") && userRoleId != 2 && userRoleId != 1 && userRoleId != 0) { // Tất cả các role đều có thể truy cập trang của user
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // 4. Nếu mọi thứ hợp lệ, cho phép request tiếp tục
        chain.doFilter(request, response);
    }
}