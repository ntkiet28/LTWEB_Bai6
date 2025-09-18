<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông báo</title>

    <!-- Bootstrap CDN for responsive design -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" />

    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Arial, sans-serif;
        }
        .container {
            max-width: 600px;
            margin-top: 50px;
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .message {
            font-size: 18px;
            color: #495057;
            margin-bottom: 20px;
        }
        .btn-login {
            width: 100%;
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 5px;
            font-size: 16px;
        }
        .btn-login:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2 class="text-center">Thông báo</h2>
        
        <!-- Hiển thị thông báo -->
        <p class="message">
            ${message}
        </p>



        <!-- Nút quay lại trang đăng nhập -->
        <a href="${pageContext.request.contextPath}/views/reset_pas.jsp" class="btn btn-login">Đến trang reset password</a>
    </div>






    <!-- Bootstrap JS (optional) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-w5kFwnT2RlgmcTgFzRU6MYTTzZ1ksw9rTe5xu9i+OIHpsZ2oVwJpqkI0Afo9w6aH" crossorigin="anonymous"></script>

</body>
</html>
