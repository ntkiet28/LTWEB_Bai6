<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên Mật Khẩu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
    <div class="container" style="max-width: 500px; margin-top: 50px;">
        <h2 class="text-center">Quên Mật Khẩu</h2>

        <!-- Alert thông báo lỗi hoặc thành công -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </c:if>
      

        <form action="${pageContext.request.contextPath}/forgotpassword" method="POST">
            <div class="form-group">
                <label for="email">Địa chỉ Email</label>
                <input type="email" class="form-control" id="email" name="email" required />
            </div>
            <button type="submit" class="btn btn-primary mt-3">Reset mật khẩu</button>
        </form>
    </div>

    <!-- Bootstrap JS for potential interactivity (optional) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
