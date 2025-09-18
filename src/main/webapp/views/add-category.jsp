<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Danh Mục</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f5f6fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .form-container {
            max-width: 500px;
            margin: 60px auto;
        }
        .card {
            border: none;
            border-radius: 16px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.1);
        }
        .card-header {
            background: linear-gradient(135deg, #4e73df, #1cc88a);
            color: #fff;
            font-size: 1.25rem;
            font-weight: 600;
            text-align: center;
            border-top-left-radius: 16px;
            border-top-right-radius: 16px;
            padding: 1rem;
        }
        .btn-submit {
            background-color: #4e73df;
            border: none;
            font-weight: 600;
        }
        .btn-submit:hover {
            background-color: #2e59d9;
        }
        .btn-cancel {
            background-color: #e74a3b;
            border: none;
            font-weight: 600;
        }
        .btn-cancel:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>

<div class="form-container">
    <div class="card">
        <div class="card-header">
            Thêm Danh Mục Mới
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/categories/add" method="post" enctype="multipart/form-data">
                <div class="mb-3">
                    <label for="name" class="form-label">Tên danh mục</label>
                    <input type="text" class="form-control" id="name" name="name"
                           placeholder="Nhập tên danh mục" required>
                </div>
                <div class="mb-3">
                    <label for="icon" class="form-label">Ảnh đại diện</label>
                    <input type="file" class="form-control" id="icon" name="icon" accept="image/*" required>
                </div>
                <div class="d-flex justify-content-between">
                    <button type="reset" class="btn btn-cancel">Hủy</button>
                    <button type="submit" class="btn btn-submit">Thêm</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>