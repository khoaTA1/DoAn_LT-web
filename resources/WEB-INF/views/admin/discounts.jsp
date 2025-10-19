
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
  <h2>Quản lý chiết khấu</h2>
  <table class="table table-bordered table-hover mt-3">
    <thead class="table-danger">
      <tr><th>ID</th><th>Tên shop</th><th>Tỷ lệ chiết khấu (%)</th><th>Ngày áp dụng</th><th>Hành động</th></tr>
    </thead>
    <tbody>
      <c:forEach var="d" items="${discounts}">
        <tr>
          <td>${d.id}</td><td>${d.shop.name}</td><td>${d.rate}</td><td>${d.startDate}</td>
          <td>
            <a href="/admin/discounts/edit/${d.id}" class="btn btn-sm btn-warning">Sửa</a>
            <a href="/admin/discounts/delete/${d.id}" class="btn btn-sm btn-danger">Xóa</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  <a href="/admin/discounts/add" class="btn btn-success">Thêm chiết khấu</a>
</div>
