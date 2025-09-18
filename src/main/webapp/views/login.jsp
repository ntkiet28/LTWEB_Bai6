<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #74ebd5 0%, #9face6 100%);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            font-family: 'Segoe UI', Arial, sans-serif;
        }

        .login-card {
            width: 100%;
            max-width: 380px;
            background: #fff;
            border-radius: 16px;
            padding: 35px 30px;
            box-shadow: 0 10px 35px rgba(0, 0, 0, 0.15);
        }

        .login-card h2 {
            font-weight: 600;
            color: #222;
            margin-bottom: 25px;
            text-align: center;
        }

        .form-label {
            font-weight: 500;
            color: #444;
        }

        .form-control {
            border-radius: 10px;
            padding: 11px;
            font-size: 0.95rem;
        }

        .form-control:focus {
            border-color: #007bff;
            box-shadow: 0 0 0 0.2rem rgba(0,123,255,0.2);
        }

        .btn-login {
            background-color: #007bff;
            color: #fff;
            border-radius: 10px;
            padding: 12px;
            font-size: 1rem;
            font-weight: 600;
            width: 100%;
            transition: background-color 0.25s, transform 0.1s;
        }

        .btn-login:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
        }

        .alert-danger {
            font-size: 0.9rem;
            border-radius: 8px;
        }

        .extra-links {
            text-align: center;
            margin-top: 20px;
            font-size: 0.9rem;
        }

        .extra-links a {
            color: #007bff;
            text-decoration: none;
            transition: color 0.25s;
        }

        .extra-links a:hover {
            color: #0056b3;
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="login-card">
        <h2>Đăng nhập</h2>

        <c:if test="${not empty alert}">
            <div class="alert alert-danger">${alert}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="mb-3">
                <label for="username" class="form-label">Tên đăng nhập</label>
                <input type="text" id="username" name="username" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Mật khẩu</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <div class="mb-3 form-check">
                <input type="checkbox" id="remember" name="remember" class="form-check-input">
                <label class="form-check-label" for="remember">Ghi nhớ đăng nhập</label>
            </div>

            <button type="submit" class="btn btn-login">Đăng nhập</button>
        </form>

        <div class="extra-links">
            <a href="${pageContext.request.contextPath}/views/forgot_pas.jsp">Quên mật khẩu?</a>
            <p class="mt-2">Chưa có tài khoản? 
                <a href="${pageContext.request.contextPath}/views/register.jsp">Đăng ký ngay</a>
            </p>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
