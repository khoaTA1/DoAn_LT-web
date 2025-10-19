<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h3>Giỏ hàng của bạn</h3>
<table class='table'>
<thead><tr><th>Sản phẩm</th><th>SL</th><th>Giá</th></tr></thead>
<tbody>
<c:forEach var='it' items='${cartItems}'><tr><td>${it.product.name}</td><td>${it.quantity}</td><td>${it.total}</td></tr></c:forEach>
</tbody>
</table>
