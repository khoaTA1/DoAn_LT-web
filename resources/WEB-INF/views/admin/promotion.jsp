
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
  <h2>Chương trình khuyến mãi</h2>
  <table class="table table-bordered table-hover mt-3">
    <thead class="table-danger">
      <tr><th>ID</th><th>Tên chương trình</th><th>Giảm giá (%)</th><th>Phí vận chuyển</th><th>Thời gian áp dụng</th><th>Hành động</th></tr>
    </thead>
    <tbody>
      <c:forEach var="p" items="${promotions}">
        <tr>
          <td>${p.id}</td><td>${p.name}</td><td>${p.discountRate}</td><td>${p.shippingFee}</td><td>${p.validDate}</td>
          <td>
            <a href="/admin/promotion/edit/${p.id}" class="btn btn-sm btn-warning">Sửa</a>
            <a href="/admin/promotion/delete/${p.id}" class="btn btn-sm btn-danger">Xóa</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  <a href="/admin/promotion/add" class="btn btn-success">Thêm khuyến mãi</a>
</div>
