<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="container mt-4">
	<c:if test="${not empty msg}">
		<div class="alert alert-info">${msg}</div>
	</c:if>
	
	<h3 class="fw-bold mb-3">Chọn danh mục sản phẩm cần thêm</h3>
	<form action="/admin/item/adddetail" method="get"
		enctype="multipart/form-data">
		<div class="mb-3">
			<label>Danh mục</label> <select name="cateid" class="form-select">
				<c:forEach var="c" items="${categories}">
					<option value="${c.id}">${c.categoryName}</option>
				</c:forEach>
			</select>
		</div>

		<button type="submit" class="btn btn-primary">Chọn</button>
		<a href="/admin/item/list" class="btn btn-secondary">Hủy</a>
	</form>
</div>