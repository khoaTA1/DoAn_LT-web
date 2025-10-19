<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang đăng nhập</title>
</head>
<body>
	<form action="login" method="POST">
		<h2>Đăng nhập</h2>
		<c:if test="${msg !=null}">
			<h3 class="alert alertdanger">${msg}</h3>
		</c:if>
		<section>
			<label class="input login-input">
				<div class="input-group">
					<span class="input-group-addon"><i class="fa fa-user"></i></span> <input
						type="text" placeholder="Tài khoản" name="username"
						class="form-control">
				</div>
			</label>
		</section>
		<section>
			<label class="input login-input">
				<div class="input-group">
					<span class="input-group-addon"><i class="fa fa-lock"></i></span> <input
						type="password" placeholder="Mật khẩu" name="password"
						class="form-control">
				</div>
			</label>
		</section>
		<button type="submit">Đăng nhập</button>
		|
		<!-- <a
			href="${pageContext.request.contextPath}/views/general/forgetpass.jsp">Quên
			mật khẩu?</a><br>-->
		<a href="${pageContext.request.contextPath}/general/register">Chưa
			có tài khoản? Đăng ký ngay</a>
</body>
</html>