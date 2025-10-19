
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
  <h2>Quản lý nhà vận chuyển</h2>
  <table class="table table-bordered table-hover mt-3">
    <thead class="table-danger">
      <tr><th>ID</th><th>Tên nhà vận chuyển</th><th>Phí vận chuyển</th><th>Hành động</th></tr>
    </thead>
    <tbody>
      <c:forEach var="s" items="${shippings}">
        <tr>
          <td>${s.id}</td><td>${s.name}</td><td>${s.fee}</td>
          <td>
            <a href="/admin/shipping/edit/${s.id}" class="btn btn-sm btn-warning">Sửa</a>
            <a href="/admin/shipping/delete/${s.id}" class="btn btn-sm btn-danger">Xóa</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  <a href="/admin/shipping/add" class="btn btn-success">Thêm nhà vận chuyển</a>
</div>
