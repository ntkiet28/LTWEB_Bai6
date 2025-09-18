<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
    <style>
        /* Đặt lại các thuộc tính mặc định của trình duyệt */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Arial, sans-serif;
        }

        /* Định dạng cho toàn bộ trang với gradient màu tím/hồng */
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background: linear-gradient(to right top, #8e44ad, #d45d79); /* Gradient tím sang hồng */
            color: #fff; /* Màu chữ trắng cho toàn bộ trang */
        }

        /* Định dạng cho khung chứa form */
        .container {
            width: 400px;
            padding: 40px;
            background: rgba(255, 255, 255, 0.9); /* Nền trắng hơi trong suốt */
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            text-align: center;
            color: #333; /* Màu chữ mặc định trong form */
        }

        /* Tiêu đề */
        h2 {
            font-size: 2em;
            color: #8e44ad; /* Màu tím cho tiêu đề */
            margin-bottom: 25px;
            font-weight: 600;
        }

        /* Thông báo lỗi */
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            border-radius: 8px;
            text-align: left;
            font-size: 0.9em;
        }

        /* Nhóm các trường nhập liệu */
        .form-group {
            margin-bottom: 20px;
            text-align: left;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-weight: 500;
        }

        .form-group input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 1em;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        .form-group input:focus {
            outline: none;
            border-color: #8e44ad; /* Viền tím khi focus */
            box-shadow: 0 0 0 0.25rem rgba(142, 68, 173, 0.25);
        }

        /* Nút đăng ký */
        .btn {
            width: 100%;
            padding: 15px;
            border: none;
            background: linear-gradient(to right, #8e44ad, #d45d79); /* Gradient tím sang hồng cho nút */
            color: #fff; /* Chữ trắng */
            font-size: 1.1em;
            font-weight: bold;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.1s;
        }

        .btn:hover {
            background: linear-gradient(to right, #7b3a99, #c04c64); /* Gradient đậm hơn khi rê chuột */
            transform: translateY(-2px);
        }

        /* Liên kết */
        .link {
            margin-top: 25px;
            font-size: 0.9em;
            color: #666;
        }

        .link a {
            color: #8e44ad; /* Liên kết màu tím */
            text-decoration: none;
            font-weight: bold;
            transition: color 0.3s;
        }

        .link a:hover {
            color: #d45d78; /* Liên kết màu hồng khi rê chuột */
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Đăng ký</h2>

    <c:if test="${not empty alert}">
        <div class="alert">${alert}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">
        <div class="form-group">
            <label for="username">Tên đăng nhập:</label>
            <input type="text" id="username" name="username" required/>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required/>
        </div>

        <div class="form-group">
            <label for="password">Mật khẩu:</label>
            <input type="password" id="password" name="password" required/>
        </div>

        <button type="submit" class="btn">Đăng ký</button>
    </form>

    <div class="link">
        Đã có tài khoản? <a href="${pageContext.request.contextPath}/views/login.jsp">Đăng nhập</a>
    </div>
</div>
</body>
</html>