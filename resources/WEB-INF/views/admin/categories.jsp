
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
  <h2>Quản lý danh mục</h2>
  <table class="table table-bordered table-hover mt-3">
    <thead class="table-danger">
      <tr><th>ID</th><th>Tên danh mục</th><th>Mô tả</th><th>Hành động</th></tr>
    </thead>
    <tbody>
      <c:forEach var="c" items="${categories}">
        <tr>
          <td>${c.id}</td><td>${c.name}</td><td>${c.description}</td>
          <td>
            <a href="/admin/categories/edit/${c.id}" class="btn btn-sm btn-warning">Sửa</a>
            <a href="/admin/categories/delete/${c.id}" class="btn btn-sm btn-danger">Xóa</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  <a href="/admin/categories/add" class="btn btn-success">Thêm danh mục</a>
</div>
