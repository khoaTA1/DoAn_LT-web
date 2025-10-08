<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang đăng ký</title>
</head>
<body>
	<c:url value="register" var="register"></c:url>

	<form role="form" action="${register}" method="post"
		enctype="multipart/form-data">
		<h2>Tạo tài khoản mới</h2>
		<c:if test="${msg != null}">
			<h3 class="alert alert-danger">${msg}</h3>
		</c:if>

		<section>
			<label class="input login-input">
				<div class="input-group">
					<span class="input-group-addon"> <i class="fa fa-user"></i>
					</span> <input type="text" placeholder="Username" name="username"
						class="form-control">
				</div>
			</label>
		</section>

		<!-- 
		<section>
			<label class="input login-input">
				<div class="input-group">
					<span class="input-group-addon"> <i class="fa fa-user"></i>
					</span> <input type="text" placeholder="Họ tên" name="fullname"
						class="form-control">
				</div>
			</label>
		</section> 

		<section>
			<label class="input login-input">
				<div class="input-group">
					<span class="input-group-addon"> <i class="fa fa-user"></i>
					</span> <input type="text" placeholder="Email" name="email"
						class="form-control">
				</div>
			</label>
		</section>

		<section>
			<label class="input login-input">
				<div class="input-group">
					<span class="input-group-addon"> <i class="fa fa-user"></i>
					</span> <input type="text" placeholder="Số điện thoại" name="phone"
						class="form-control">
				</div>
			</label>
		</section>-->

		<section>
			<label class="input login-input">
				<div class="input-group">
					<span class="input-group-addon"> <i class="fa fa-user"></i>
					</span> <input type="text" placeholder="Mật khẩu" name="password"
						class="form-control">
				</div>
			</label>
		</section>
		
		<!-- 
		<div class="form-group">
			<label>Ảnh đại diện</label> <input type="file" name="avatar" />
		</div>-->
		<button type="submit">Đăng kí</button>
	</form> 
	|
	<a href="${pageContext.request.contextPath}/views/general/login.jsp">Đăng
		nhập</a>
</body>
</html>