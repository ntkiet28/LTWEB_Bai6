package Controller;

import java.io.IOException;
import java.util.UUID;

import Entity.User;
import Utils.EmailUtil;
import Service.UserService;
import Service.ServiceImpl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/login", "/logout", "/register", "/resetpassword", "/forgotpassword" })
public class AuthController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Hằng số
    public static final String SESSION_ACCOUNT = "account";
    public static final String COOKIE_REMEMBER = "username";
    public static final String REGISTER_PAGE = "/views/register.jsp";

    private UserService service = new UserServiceImpl();

    // ===================== doGet =====================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // Lấy URI không kèm context path
        String path = req.getRequestURI().substring(req.getContextPath().length());

        switch (path) {
            case "/login":
                doGetLogin(req, resp);
                break;
            case "/logout":
                doGetLogout(req, resp);
                break;
            case "/register":
                doGetRegister(req, resp);
                break;
            case "/resetpassword":
                doGetResetPassword(req, resp);
                break;
          
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    // ===================== doPost =====================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getRequestURI().substring(req.getContextPath().length());

        switch (path) {
            case "/login":
                doPostLogin(req, resp);
                break;
            case "/register":
                doPostRegister(req, resp);
                break;
            case "/resetpassword":
                doPostResetPassword(req, resp);
                break;
            case "/forgotpassword":
            	doPostForgotPassword(req,resp);
            	break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }


 // ===================== LOGIN =====================
    // dogetlogin để xử lý trường hợp bấm vào đăng nhập thì vào thẳng không cần nhập pass do đã check session 
    private void doGetLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute(SESSION_ACCOUNT) != null) {
            // Kiểm tra roleId từ session
            if (session.getAttribute("roleId") != null) {
                int roleId = (int) session.getAttribute("roleId");
                switch (roleId) {
                    case 2:
                        resp.sendRedirect(req.getContextPath() + "/user/home");
                        break;
                    case 1:
                        resp.sendRedirect(req.getContextPath() + "/manager/home/categories");
                        break;
                    case 0:
                        resp.sendRedirect(req.getContextPath() + "/admin/home");
                        break;
//                    default:
//                        resp.sendRedirect(req.getContextPath() + "/waiting"); // Hoặc trang mặc định
//                        break;
                }
            } else {
                // Trường hợp session có account nhưng không có roleId
                // Xử lý bằng cách chuyển hướng đến trang chờ hoặc đăng nhập lại
                resp.sendRedirect(req.getContextPath() + "/login"); 
            }
            return;
        }

        // Check cookie remember-me
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_REMEMBER.equals(cookie.getName())) {
                    String username = cookie.getValue();
                    User user = service.getUserName(username); 
                    if (user != null) {
                        session = req.getSession(true);
                        session.setAttribute(SESSION_ACCOUNT, user);
                        session.setAttribute("roleId", user.getRoleId());
                        int roleId = user.getRoleId();
                        switch (roleId) {
                            case 2:
                                resp.sendRedirect(req.getContextPath() + "/user/home");
                                break;
                            case 1:
                                resp.sendRedirect(req.getContextPath() + "/manager/home/categories");
                                break;
                            case 0:
                                resp.sendRedirect(req.getContextPath() + "/admin/home");
                                break;
//                            default:
//                                resp.sendRedirect(req.getContextPath() + "/waiting"); // Hoặc trang mặc định
//                                break;
                        }
                        return;
                    }
                }
            }
        }
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }
    private void doPostLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean isRememberMe = "on".equals(req.getParameter("remember"));

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            req.setAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        User user = service.login(username, password);

        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute(SESSION_ACCOUNT, user);
            session.setAttribute("roleId", user.getRoleId()); //thêm roleId vào session

            if (isRememberMe) {
                saveRememberMe(resp, username);
            }
            
            int roleId = (int) session.getAttribute("roleId");
            switch (roleId) {
                case 2:
                    resp.sendRedirect(req.getContextPath() + "/user/home");
                    break;
                case 1:
                    resp.sendRedirect(req.getContextPath() + "/manager/home/categories");
                    break;
                case 0:
                    resp.sendRedirect(req.getContextPath() + "/admin/home");
                    break;
                default:
                    // Xử lý trường hợp không xác định
                    break;
            }
//            resp.sendRedirect(req.getContextPath() + "/waiting");
        } else {
            req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }

    private void saveRememberMe(HttpServletResponse response, String username) {
        Cookie cookie = new Cookie(COOKIE_REMEMBER, username);
        cookie.setMaxAge(30 * 60); // 30 phút
        response.addCookie(cookie);
    }

    
    
    
    
    
    private void doGetLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie(COOKIE_REMEMBER, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        response.sendRedirect(request.getContextPath() + "/login");
    }

    // ===================== REGISTER =====================
	
	private void doGetRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		HttpSession session = req.getSession(false);
//		if (session != null && session.getAttribute("account") != null) {
//			resp.sendRedirect(req.getContextPath() + "/admin");
//			return;
//		}
//		Cookie[] cookies = req.getCookies();
//		if (cookies != null) {
//			for (Cookie cookie : cookies) {
//				if (cookie.getName().equals("username")) {
//					session = req.getSession(true);
//					session.setAttribute("username", cookie.getValue());
//					resp.sendRedirect(req.getContextPath() + "/admin");
//					return;
//				}
//			}
//		}
		req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
	}

    private void doPostRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if (service.checkExistEmail(email)) {
            req.setAttribute("alert", "Email đã tồn tại!");
            req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
            return;
        }
        if (service.checkExistUsername(username)) {
            req.setAttribute("alert", "Tài khoản đã tồn tại!");
            req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
            return;
        }

        boolean isSuccess = service.register(username, password, email);
        if (isSuccess) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("alert", "System error!");
            req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
        }
    }

    // ===================== RESET PASSWORD =====================
    private void doGetResetPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");

        if (service.checkExistToken(token)) {
            request.getRequestDispatcher("/views/reset_pas.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Token không hợp lệ hoặc đã hết hạn.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void doPostResetPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("password");

        if (service.checkExistToken(token)) {
            boolean isUpdated = service.updatePasswordWithToken(token, newPassword);
            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                request.setAttribute("error", "Không thể cập nhật mật khẩu.");
                request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Token không hợp lệ hoặc đã hết hạn.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
    


    private void doPostForgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        // Kiểm tra xem email có tồn tại trong hệ thống không
        UserService service = new UserServiceImpl();
        Boolean isEmailExist = service.checkExistEmail(email);

        if (isEmailExist) {
            // Tạo mã token ngẫu nhiên để xác nhận
            String token = generateToken();
            
            
            // Lưu token vào cơ sở dữ liệu cùng với thông tin người dùng (có thể lưu trong bảng password_reset_tokens)
            service.setToken(email, token); // Cập nhật token vào CSDL

            // Gửi email với token
            EmailUtil send = new EmailUtil();
            EmailUtil.sendResetPasswordEmail(email, token); // Gửi email chứa token

            // Thông báo cho người dùng rằng họ đã nhận được email
            request.setAttribute("message", "Một mã reset mật khẩu đã được gửi tới email của bạn.");
            request.getRequestDispatcher("/views/mess_forgot_pass.jsp").forward(request, response);
        } else {
            // Nếu email không tồn tại trong hệ thống, thông báo lỗi
            request.setAttribute("error", "Email không tồn tại trong hệ thống.");
            request.getRequestDispatcher("/views/forgot_pas.jsp").forward(request, response);
        }
    }

    private String generateToken() {
        // Tạo token ngẫu nhiên bằng UUID
        return UUID.randomUUID().toString(); // Ví dụ sử dụng UUID làm token
    }

}