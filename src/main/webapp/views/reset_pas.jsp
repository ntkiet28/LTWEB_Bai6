<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt lại mật khẩu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
    <div class="container" style="max-width: 500px; margin-top: 50px;">
        <h2 class="text-center">Đặt lại mật khẩu</h2>
        <form action="${pageContext.request.contextPath}/resetpassword" method="POST">
            <!-- Trường nhập token từ người dùng -->
            <div class="form-group">
                <label for="token">Mã xác nhận (Token)</label>
                <input type="text" class="form-control" id="token" name="token" required placeholder="Nhập mã token bạn nhận được" />
            </div>

            <!-- Trường nhập mật khẩu mới -->
            <div class="form-group mt-3">
                <label for="password">Mật khẩu mới</label>
                <input type="password" class="form-control" id="password" name="password" required placeholder="Nhập mật khẩu mới" />
            </div>

            <!-- Gửi token cùng với mật khẩu mới -->
            <button type="submit" class="btn btn-primary mt-3">Đặt lại mật khẩu</button>
        </form>
    </div>
</body>
</html>
