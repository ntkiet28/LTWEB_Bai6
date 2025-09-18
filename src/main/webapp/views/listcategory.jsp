<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet" />
<link href="https://cdn.datatables.net/2.3.3/css/dataTables.bootstrap5.css" rel="stylesheet" />

<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/2.3.3/js/dataTables.js"></script>
<script src="https://cdn.datatables.net/2.3.3/js/dataTables.bootstrap5.js"></script>

<div class="container my-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <!-- Logout -->
        <form action="${pageContext.request.contextPath}/logout" method="post" class="m-0">
            <button type="submit" class="btn btn-outline-danger btn-sm">Đăng xuất</button>
        </form>

        <!-- Thêm mới -->
        <c:if test="${sessionScope.roleId == 0 || sessionScope.roleId == 2}">
            <a href="<c:url value='/views/add-category.jsp'/>" class="btn btn-success btn-sm">+ Thêm mới danh mục</a>
        </c:if>
    </div>

    <div class="card shadow-sm">
        <div class="card-body">
            <table id="example" class="table table-bordered table-striped align-middle">
                <thead class="table-light">
                    <tr>
                        <th style="width: 60px;">STT</th>
                        <th style="width: 200px;">Icon</th>
                        <th>Tên danh mục</th>
                        <c:if test="${sessionScope.roleId == 0 || sessionScope.roleId == 2}">
                            <th style="width: 150px;">Hành động</th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cateList}" var="cate" varStatus="STT">
                        <tr>
                            <td>${STT.index + 1}</td>
                            <c:url value="/image?fname=${cate.icon}" var="imgUrl"></c:url>
                            <td>
                                <img src="${imgUrl}" class="img-thumbnail" style="max-height:120px; max-width:180px;" />
                            </td>
                            <td>${cate.categoryName}</td>
                            <c:if test="${sessionScope.roleId == 0 || sessionScope.roleId == 2}">
                                <td>
                                    <a href="<c:url value='/categories/update?id=${cate.id}'/>" 
                                       class="btn btn-sm btn-primary">Sửa</a>
                                    <a href="<c:url value='/categories/delete?id=${cate.id}'/>" 
                                       class="btn btn-sm btn-danger"
                                       onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?');">Xóa</a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        $('#example').DataTable({
            paging: true,
            ordering: true,
            info: true,
            language: {
                url: "https://cdn.datatables.net/plug-ins/1.13.4/i18n/vi.json"
            }
        });
    });
</script>