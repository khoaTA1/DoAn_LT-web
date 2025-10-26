<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container-fluid py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="fw-bold text-primary mb-0">Quản lý người dùng</h4>
        <a href="${pageContext.request.contextPath}/admin/user/add" class="btn btn-success">
            <i class="bi bi-person-plus"></i> Thêm người dùng
        </a>
    </div>

    <!-- 🔍 Ô tìm kiếm -->
    <form class="mb-3" method="get" action="${pageContext.request.contextPath}/admin/user">
        <div class="input-group" style="max-width: 400px;">
            <input type="text" name="keyword" class="form-control" placeholder="Tìm theo tên đăng nhập hoặc email..."
                   value="${keyword != null ? keyword : ''}">
            <button type="submit" class="btn btn-primary">
                <i class="bi bi-search"></i> Tìm kiếm
            </button>
            <a href="${pageContext.request.contextPath}/admin/user" class="btn btn-secondary">Tất cả</a>
        </div>
    </form>

    <div class="card shadow-sm border-0">
        <div class="card-body">
            <c:if test="${empty userList}">
                <div class="alert alert-info text-center">Không tìm thấy người dùng nào.</div>
            </c:if>

            <c:if test="${not empty userList}">
                <table class="table table-bordered table-hover align-middle text-center">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Tên đăng nhập</th>
                            <th>Họ tên</th>
                            <th>Email</th>
                            <th>Điện thoại</th>
                            <th>Vai trò</th>
                            <th>Xác minh email</th>
                            <th>Ngày tạo</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="u" items="${userList}">
                            <tr>
                                <td>${u.id}</td>
                                <td>${u.userName}</td>
                                <td>${u.fullName}</td>
                                <td>${u.email}</td>
                                <td>${u.phone}</td>
                                <td>
                                    <span class="badge ${u.role eq 'ADMIN' ? 'bg-danger' : 'bg-primary'}">${u.role}</span>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${u.emailVerify}">
                                            <span class="text-success fw-bold">✔</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-danger fw-bold">✖</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${u.createdDate}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/user/edit/${u.id}" 
                                       class="btn btn-warning btn-sm text-white me-1">
                                        <i class="bi bi-pencil"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/user/delete/${u.id}" 
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Xóa người dùng này?');">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>
