<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nhập OTP - Điện Máy Đỏ</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<style>
body {
	background-color: #f8f9fa;
}

.otp-box {
	max-width: 400px;
	margin: 80px auto;
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
}

.btn-red:hover {
	background-color: #a90321;
	border-color: #a90321;
}
</style>
</head>
<body>
	<div class="container">
		<div class="otp-box">
			<div class="text-center mb-4">
				<div class="brand-text">ĐIỆN MÁY ĐỎ</div>
				<p class="text-muted">Nhập mã OTP đã gửi tới email</p>
			</div>

			<c:if test="${msg != null}">
				<div class="alert alert-info text-center">${msg}</div>
			</c:if>

			<form action="/user/entercode" method="post">
				<div class="mb-3">
					<label for="otp" class="form-label">Mã OTP</label> <input
						type="text" class="form-control" id="otp" name="code" required
						autofocus>
				</div>

				<div class="d-grid">
					<button type="submit" class="btn btn-red">Xác thực OTP</button>
				</div>

				<p class="text-center mt-3 mb-0">
					Quay lại <a href="/redirect/login" class="text-danger fw-bold">Đăng
						nhập</a>
				</p>
			</form>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
		crossorigin="anonymous"></script>
</body>
</html>
