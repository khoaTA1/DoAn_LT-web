
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
  <h2>Quản lý người dùng</h2>
  <table class="table table-bordered table-hover mt-3">
    <thead class="table-danger">
      <tr><th>ID</th><th>Tên đăng nhập</th><th>Email</th><th>Vai trò</th><th>Trạng thái</th><th>Hành động</th></tr>
    </thead>
    <tbody>
      <c:forEach var="u" items="${users}">
        <tr>
          <td>${u.id}</td><td>${u.username}</td><td>${u.email}</td><td>${u.role}</td>
          <td>${u.status}</td>
          <td>
            <a href="/admin/users/edit/${u.id}" class="btn btn-sm btn-warning">Sửa</a>
            <a href="/admin/users/delete/${u.id}" class="btn btn-sm btn-danger">Xóa</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  <a href="/admin/users/add" class="btn btn-success">Thêm người dùng</a>
</div>
