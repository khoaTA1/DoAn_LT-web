<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fm" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:if test="${not empty msg}">
    <div class="alert alert-info">
        ${msg}
    </div>
</c:if>

<div class="row">
	<c:forEach var="p" items="${listItem}">
		<div class="col-md-4 mb-4">
			<div class="card item-card h-100"
				data-item='{"name":"${p.name}","origin":"${p.origin}","brand":"${p.brand}","image":"${p.image}"}'
				onclick="window.location.href='/item/getinfo/${p.id}'">
				<img src="/image?fname=${p.image}" alt="${p.name}"
					class="card-img-top" />
				<div class="card-body d-flex flex-column">
					<h5 class="card-title">${p.name}</h5>
					<p class="card-text text-muted">
						Giá:
						<fm:formatNumber value="${p.price}" type="number"
							groupingUsed="true" maxFractionDigits="0" />
						VNĐ
					</p>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
<nav>
	<ul class="pagination justify-content-center">
		<c:forEach begin="1" end="${totalPages}" var="i">
			<li class="page-item ${i == page ? 'active' : ''}"><a
				class="page-link"
				href="${selectedCategoryId == null ? '/item/getall' : '/item/category/' += selectedCategoryId}?page=${i}">
					${i} </a></li>
		</c:forEach>
	</ul>
</nav>
