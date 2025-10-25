<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fm" uri="jakarta.tags.fmt"%>



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
