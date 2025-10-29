<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<style>
.list-group-item.active {
    background-color: #cce5ff;
    color: #000;
}
</style>

<h5 class="fw-bold mb-3">Danh mục sản phẩm</h5>
<ul class="list-group">
	<li class="list-group-item"><a href="/item/getall"
		class="text-decoration-none text-dark fw-bold">Tất cả sản phẩm</a></li>
	<c:forEach var="cat" items="${cateList}">
		<li class="list-group-item ${selectedCategoryId == cat.id ? 'active' : ''}"><a
			href="/item/getbycateid/${cat.id}?page=1&isAsc=true"
			class="text-decoration-none text-dark">${cat.categoryName}</a></li>
	</c:forEach>
</ul>