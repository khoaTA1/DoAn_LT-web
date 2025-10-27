<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thanh Toán - Điện Máy Đỏ</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" 
      rel="stylesheet"
      integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
      crossorigin="anonymous">

<style>
body {
	background-color: #f8f9fa;
}
.payment-box {
	max-width: 600px;
	margin: 60px auto;
	padding: 30px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	background-color: #ffffff;
}
.brand-text {
	color: #d90429;
	font-weight: bold;
	font-size: 28px;
}
.btn-red {
	background-color: #d90429;
	border-color: #d90429;
	color: #fff;
}
.btn-red:hover {
	background-color: #a90321;
	border-color: #a90321;
}
</style>
</head>
<body>
	<div class="container">
		<div class="payment-box">
			<div class="text-center mb-4">
				<div class="brand-text">ĐIỆN MÁY ĐỎ</div>
				<p class="text-muted">Xác nhận thanh toán đơn hàng</p>
			</div>

			<c:if test="${msg != null}">
				<div class="alert alert-danger text-center">${msg}</div>
			</c:if>

			<form id="paymentForm" action="${pageContext.request.contextPath}/payment/confirm" method="post">
				<input type="hidden" name="userId" value="${user.id}">

				<div class="mb-3">
					<label class="form-label">Họ tên người nhận</label>
					<input type="text" class="form-control" name="hoten" required>
				</div>

				<div class="mb-3">
					<label class="form-label">Số điện thoại</label>
					<input type="text" class="form-control" name="sdt" required>
				</div>

				<div class="mb-3">
					<label class="form-label">Địa chỉ giao hàng</label>
					<textarea class="form-control" name="diachi" rows="3" required></textarea>
				</div>

				<div class="mb-3">
					<label class="form-label">Phương thức thanh toán</label>
					<select class="form-select" id="phuongthuc" name="phuongthuc" required>
						<option value="tienmat">Thanh toán khi nhận hàng (COD)</option>
						<option value="chuyenkhoan">Chuyển khoản ngân hàng</option>
						<option value="the">Thẻ tín dụng / Ghi nợ</option>
					</select>
				</div>

				<div class="mb-3">
					<label class="form-label">Tổng tiền</label>
					<input type="text" class="form-control" name="tongtien" readonly value="${tongTien}">
				</div>

				<div class="text-center">
					<button type="button" class="btn btn-red px-4" onclick="handlePayment()">Xác nhận thanh toán</button>
				</div>
			</form>
		</div>
	</div>

<script>
function handlePayment() {
    const form = document.getElementById("paymentForm");
    const phuongThuc = document.getElementById("phuongthuc").value;

    if (phuongThuc === "chuyenkhoan") {
        // Chuyển sang trang quét mã QR
        window.location.href = "<c:url value='/redirect/qr_scan' />";
    } else if (phuongThuc === "tienmat") {
    	// Chuyển sang trang cod
        window.location.href = "<c:url value='/redirect/cod' />";
    } else if (phuongThuc === "the") {
        alert("Website hiện chưa hỗ trợ phương thức này.");
    }
}
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
		crossorigin="anonymous"></script>
</body>
</html>
