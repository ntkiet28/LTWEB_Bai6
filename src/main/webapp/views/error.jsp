<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lỗi</title>

    <!-- Bootstrap CDN for responsive design -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
    <div class="container" style="max-width: 500px; margin-top: 50px;">
        <h2 class="text-center text-danger">Có lỗi xảy ra</h2>
        <p class="text-center" style="font-size: 18px; color: #495057;">
            ${error} <!-- Hiển thị thông báo lỗi từ servlet -->
        </p>

        <!-- Nút quay lại trang đăng nhập -->
        <a href="${pageContext.request.contextPath}/views/forgot_pas.jsp" class="btn btn-primary mt-3">Quay lại trang reset</a>
    </div>
</body>
</html>
