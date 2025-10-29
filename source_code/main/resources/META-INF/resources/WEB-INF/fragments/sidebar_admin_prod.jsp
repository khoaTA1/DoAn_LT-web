<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="bg-light p-3 rounded shadow-sm">
    <h5 class="fw-bold mb-3 text-primary">
        <i class="bi bi-list-ul"></i> Danh mục sản phẩm
    </h5>

    <ul class="list-group list-group-flush">
        <!-- Tất cả sản phẩm -->
        <li class="list-group-item ${empty selectedCateId ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/admin/item/list"
               class="text-decoration-none fw-bold ${empty selectedCateId ? 'text-white' : 'text-dark'}">
                <i class="bi bi-box-seam"></i> Tất cả sản phẩm
            </a>
        </li>

        <!-- Các danh mục -->
        <c:forEach var="cate" items="${categories}">
            <li class="list-group-item ${selectedCateId == cate.id ? 'active' : ''}">
                <a href="${pageContext.request.contextPath}/admin/item/list?cateId=${cate.id}"
                   class="text-decoration-none fw-bold ${selectedCateId == cate.id ? 'text-white' : 'text-dark'}">
                    <i class="${cate.icon != null ? cate.icon : 'bi bi-tag'}"></i> ${cate.categoryName}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>

