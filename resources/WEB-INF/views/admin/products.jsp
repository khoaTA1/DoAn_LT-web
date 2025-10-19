
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
  <h2>Quản lý sản phẩm</h2>
  <table class="table table-bordered table-hover mt-3">
    <thead class="table-danger">
      <tr><th>ID</th><th>Tên sản phẩm</th><th>Giá</th><th>Danh mục</th><th>Shop</th><th>Hành động</th></tr>
    </thead>
    <tbody>
      <c:forEach var="p" items="${products}">
        <tr>
          <td>${p.id}</td><td>${p.name}</td><td>${p.price}</td><td>${p.category.name}</td><td>${p.shop.name}</td>
          <td>
            <a href="/admin/products/edit/${p.id}" class="btn btn-sm btn-warning">Sửa</a>
            <a href="/admin/products/delete/${p.id}" class="btn btn-sm btn-danger">Xóa</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  <a href="/admin/products/add" class="btn btn-success">Thêm sản phẩm</a>
</div>
