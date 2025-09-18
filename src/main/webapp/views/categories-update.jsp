<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh Sửa Danh Mục</title>
    <link rel="stylesheet" href="path/to/your/styles.css">
</head>
<body>
    <h1>Chỉnh Sửa Danh Mục</h1>
    
    <form role="form" action="${pageContext.request.contextPath}/categories/update" method="post" enctype="multipart/form-data">
        <!-- Giả sử "category" là đối tượng chứa dữ liệu danh mục hiện tại -->
        <input type="hidden" name="id" value="${category.id}"/>
        
        
        <div class="form-group">
            <label for="name">Tên danh mục:</label>
            <input type="text" class="form-control" value="${category.categoryName}" name="name" id="name" />
        </div>

        <div class="form-group">
            <label for="icon">Ảnh đại diện:</label>
            <!-- Hiển thị ảnh hiện tại của danh mục -->
            <c:url value="/image?fname=${category.icon}" var="imgUrl"/>
            <img class="img-responsive" width="100px" src="${imgUrl}" alt="Ảnh hiện tại" />
            <input type="file" class="form-control" name="icon" id="icon" />
        </div>

        <button type="submit" class="btn btn-default">Chỉnh Sửa</button>
        <button type="reset" class="btn btn-primary">Hủy</button>
    </form>
</body>
</html>
