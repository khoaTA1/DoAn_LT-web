<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fm" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ĐIỆN MÁY ĐỎ</title>
<fm:setLocale value="vi_VN" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<style>
#item-preview {
	position: fixed;
	top: 55%;
	right: 15px;
	transform: translateY(-50%);
	width: 350px;
	display: none;
	z-index: 1050;
	background: #fff;
	border: 1px solid #ddd;
	border-radius: 8px;
	box-shadow: 0 0 12px rgba(0, 0, 0, 0.1);
}

.item-card:hover {
	cursor: pointer;
	background-color: #f9f9f9;
}
</style>
</head>
<body>
	<!-- thanh điều hướng -->
	<jsp:include page="/WEB-INF/fragments/admin_navbar.jsp" />

	<div class="container-fluid mt-4">
		<div class="row">
			<!-- (cột trung tâm) -->
			<div class="col-md-10 mx-auto">
				<sitemesh:write property="body" />
			</div>
		</div>
	</div>	

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
		crossorigin="anonymous"></script>
</body>
</html>
