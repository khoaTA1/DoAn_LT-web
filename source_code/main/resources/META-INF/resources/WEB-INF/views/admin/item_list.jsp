<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container-fluid py-4">
    <div class="row">
        <!-- Sidebar danh mục -->
        <div class="col-md-3">
            <jsp:include page="/WEB-INF/fragments/sidebar_admin_prod.jsp" />
        </div>

        <!-- Main content -->
        <div class="col-md-9">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="fw-bold text-primary mb-0">
                    <c:choose>
                        <c:when test="${not empty selectedCateId}">
                            Danh sách sản phẩm —
                            <span class="text-danger">
                                <c:forEach var="c" items="${categories}">
                                    <c:if test="${c.id == selectedCateId}">${c.categoryName}</c:if>
                                </c:forEach>
                            </span>
                        </c:when>
                        <c:otherwise>
                            Tất cả sản phẩm
                        </c:otherwise>
                    </c:choose>
                </h4>
                <a href="${pageContext.request.contextPath}/admin/item/add" class="btn btn-success">
                    <i class="bi bi-plus-circle"></i> Thêm sản phẩm
                </a>
            </div>

            <div class="card shadow-sm border-0">
                <div class="card-body">
                    <c:if test="${empty listItem}">
                        <div class="alert alert-info text-center">
                            Không có sản phẩm nào trong danh mục này.
                        </div>
                    </c:if>

                    <c:if test="${not empty listItem}">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover align-middle">
                                <thead class="table-dark">
                                    <tr>
                                        <th style="width:5%">ID</th>
                                        <th style="width:12%">Ảnh</th>
                                        <th>Tên sản phẩm</th>
                                        <th style="width:10%">Giá</th>
                                        <th style="width:8%">Giảm giá</th>
                                        <th style="width:10%">Thương hiệu</th>
                                        <th style="width:10%">Xuất xứ</th>
                                        <th style="width:12%">Hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${listItem}">
                                        <tr>
                                            <td>${item.id}</td>
                                            <td>
                                                <img src="${pageContext.request.contextPath}/image?fname=${item.image}"
                                                     alt="Ảnh sản phẩm"
                                                     style="width:60px;height:60px;object-fit:cover;border-radius:6px;"
                                                     onerror="this.src='${pageContext.request.contextPath}/assets/img/default-image.png'">
                                            </td>
                                            <td class="text-start">${item.name}</td>
                                            <td><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> ₫</td>
                                            <td>${item.discount}%</td>
                                            <td>${item.brand}</td>
                                            <td>${item.origin}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/admin/item/edit/${item.id}/${item.categoryId}" 
                                                   class="btn btn-sm btn-warning text-white me-1">
                                                    <i class="bi bi-pencil"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/admin/item/delete/${item.id}" 
                                                   class="btn btn-sm btn-danger"
                                                   onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?');">
                                                    <i class="bi bi-trash"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
