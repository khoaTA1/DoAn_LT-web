<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Giỏ hàng - Điện Máy Đỏ</title>
<!--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">  -->
<style>
body { background-color: #f8f9fa; }
.cart-container { max-width: 900px; margin: 40px auto; background: white; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
.cart-item img { width: 80px; height: 80px; object-fit: cover; border-radius: 5px; }
.total-box { font-size: 18px; font-weight: bold; text-align: right; }
.btn-danger { background-color: #d90429; border: none; }
.btn-danger:hover { background-color: #a90321; }
</style>
</head>
<body>
<div class="container cart-container">
    <h3 class="text-danger mb-4">🛒 Giỏ hàng của bạn</h3>
    <c:if test="${empty cartList}">
        <div class="alert alert-info text-center">Giỏ hàng của bạn đang trống.</div>
    </c:if>
    <c:if test="${not empty cartList}">
        <table class="table table-hover align-middle">
            <thead class="table-light">
                <tr>
                    <th>Hình</th>
                    <th>Tên sản phẩm</th>
                    <th>Giá (VNĐ)</th>
                    <th>Giảm giá</th>
                    <th>Số lượng</th>
                    <th>Tổng (VNĐ)</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="c" items="${cartList}">
                    <tr class="cart-item">
							<td><img
								src="${pageContext.request.contextPath}/image?fname=${c.item.image}"
								alt="${c.item.name}"
								style="width: 60px; height: 60px; object-fit: cover; border-radius: 6px;"></td>
							<td>${c.item.name}</td>
							<td><fmt:formatNumber value="${c.item.price}" type="Number" /></td>
							<td><c:out value="${c.discount}" />%</td>
							<td>${c.quantity}</td>
                        <td><fmt:formatNumber value="${c.totalPrice}" type="Number"/></td>
                        <td>
                            <a href="/user/cart/remove?itemId=${c.item.id}" class="btn btn-sm btn-outline-danger">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="d-flex justify-content-between align-items-center">
            <div class="total-box">
                Tổng cộng: <span class="text-danger fs-5"><fmt:formatNumber value="${totalPrice}" type="number"/>₫</span>
            </div>
            <form action="/redirect/payment/" method="POST">
            	<input type="hidden" name="priceAfterDiscount" value="${totalPrice}">
            	<button type="submit" class="btn btn-danger">Thanh toán</button>
        	</form>
        </div>
    </c:if>
</div>
</body>
</html>
