<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký - Điện Máy Đỏ</title>

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

.register-box {
	max-width: 500px;
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
		<div class="register-box">
			<div class="text-center mb-4">
				<div class="brand-text">ĐIỆN MÁY ĐỎ</div>
				<p class="text-muted">Tạo tài khoản mới</p>
			</div>
			
			<c:if test="${msg !=null}">
			<div class="text-center alert alertdanger">${msg}</div>
			</c:if>
			
			<form action="/user/register" method="post">
				<div class="mb-3">
					<label for="username" class="form-label">Tên đăng nhập</label> <input
						type="text" class="form-control" id="username" name="username"
						required>
				</div>

				<div class="mb-3">
					<label for="email" class="form-label">Email</label> <input
						type="email" class="form-control" id="email" name="email" required>
				</div>

				<div class="mb-3">
					<label for="password" class="form-label">Mật khẩu</label> <input
						type="password" class="form-control" id="password" name="password"
						required>
				</div>

				<div class="mb-3">
					<label for="confirmPassword" class="form-label">Xác nhận
						mật khẩu</label> <input type="password" class="form-control"
						id="confirmPassword" name="confirmPassword" required>
				</div>

				<div class="d-grid">
					<button type="submit" class="btn btn-red">Đăng ký</button>
				</div>

				<p class="text-center mt-3 mb-0">
					Đã có tài khoản? <a href="/redirect/login"
						class="text-danger fw-bold">Đăng nhập ngay</a>
				</p>
			</form>
		</div>
	</div>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
		crossorigin="anonymous"></script>
</body>
</html>
