<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fm" uri="jakarta.tags.fmt"%>

<head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>
	<div class="container mt-4">
		<div class="row justify-content-center">
			<div class="col-md-7">
				<form method="get" action="/item/search-bar" class="input-group">
					<input type="text" name="keyword" class="form-control"
						placeholder="Nhập loại sản phẩm cần tìm ..."
						value="${param.keyword != null ? param.keyword : ''}" required />
					<button type="submit" class="btn btn-danger">
						<i class="bi bi-search"></i>
					</button>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>