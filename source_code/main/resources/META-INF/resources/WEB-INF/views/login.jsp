<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập - Điện Máy Đỏ</title>
<!--<meta name="viewport" content="width=device-width, initial-scale=1">-->

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

.login-box {
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
		<div class="login-box">
			<div class="text-center mb-4">
				<a href="/homepage" class="brand-text" style="text-decoration: none;">ĐIỆN MÁY ĐỎ</a>
				<p class="text-muted">Đăng nhập tài khoản của bạn</p>
			</div>

			<c:if test="${msg !=null}">
			<div class="text-center alert alertdanger">${msg}</div>
			</c:if>
			
			<form action="/user/login" method="post">
				<div class="mb-3">
					<label for="email" class="form-label">Email hoặc tên đăng
						nhập</label> <input type="text" class="form-control" id="email"
						name="emailorusern" required autofocus>
				</div>

				<div class="mb-3">
					<label for="password" class="form-label">Mật khẩu</label> <input
						type="password" class="form-control" id="password" name="password"
						required>
				</div>

				<div class="d-flex justify-content-between align-items-center mb-3">
				<!-- 
					<div class="form-check">
						<input class="form-check-input" type="checkbox" value=""
							id="rememberMe" name="remember-me"> <label class="form-check-label"
							for="rememberMe"> Ghi nhớ đăng nhập </label>
					</div> -->
					<a href="/redirect/gencode" class="small text-danger">Quên mật
						khẩu?</a>
				</div>

				<div class="d-grid">
					<button type="submit" class="btn btn-red">Đăng nhập</button>
				</div>

				<p class="text-center mt-3 mb-0">
					Chưa có tài khoản? <a href="/redirect/register"
						class="text-danger fw-bold">Đăng ký ngay</a>
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