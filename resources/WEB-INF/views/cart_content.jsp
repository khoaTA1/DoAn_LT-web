<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h3>Giỏ hàng của bạn</h3>
<form id="cartForm" action="${pageContext.request.contextPath}/cart/update" method="post">
<table class="table table-striped">
<thead><tr><th>Sản phẩm</th><th>Giá</th><th>Số lượng</th><th>Tổng</th><th></th></tr></thead>
<tbody>
<c:forEach var="item" items="${cartItems}">
<tr>
  <td>${item.product.name}</td>
  <td>${item.product.price}đ</td>
  <td><input type="number" onchange="updateCartQty(${item.product.id}, this)" value="${item.quantity}" min="1" class="form-control" style="width:90px"></td>
  <td id="lineTotal_${item.product.id}">${item.totalPrice}đ</td>
  <td><a href="${pageContext.request.contextPath}/cart/remove/${item.product.id}" class="btn btn-sm btn-outline-danger">Xóa</a></td>
</tr>
</c:forEach>
</tbody>
</table>
<div class="d-flex justify-content-between align-items-center">
  <div>
    <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#couponModal">Áp mã</button>
  </div>
  <div>
    <strong>Tổng cộng: </strong> <span id="cartTotal">${cartTotal}đ</span>
    <a href="${pageContext.request.contextPath}/checkout" class="btn btn-primary ms-3">Thanh toán</a>
  </div>
</div>
<jsp:include page="/WEB-INF/views/fragments/modals.jsp"/>
</form>
