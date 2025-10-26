<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="container py-4">
    <h4 class="fw-bold text-primary mb-4">Chỉnh sửa thông tin người dùng</h4>

    <form action="${pageContext.request.contextPath}/admin/user/edit" method="post">
        <input type="hidden" name="id" value="${user.id}">

        <div class="mb-3">
            <label class="form-label">Tên đăng nhập</label>
            <input type="text" name="userName" class="form-control" value="${user.userName}" readonly>
        </div>

        <div class="mb-3">
            <label class="form-label">Họ tên</label>
            <input type="text" name="fullName" class="form-control" value="${user.fullName}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" value="${user.email}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Số điện thoại</label>
            <input type="text" name="phone" class="form-control" value="${user.phone}">
        </div>

        <div class="mb-3">
            <label class="form-label">Vai trò</label>
            <select name="role" class="form-select">
                <option value="USER" ${user.role eq 'USER' ? 'selected' : ''}>USER</option>
                <option value="ADMIN" ${user.role eq 'ADMIN' ? 'selected' : ''}>ADMIN</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Xác minh Email</label>
            <select name="emailVerify" class="form-select">
                <option value="true" ${user.emailVerify ? 'selected' : ''}>Đã xác minh</option>
                <option value="false" ${!user.emailVerify ? 'selected' : ''}>Chưa xác minh</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Cập nhật</button>
        <a href="${pageContext.request.contextPath}/admin/user" class="btn btn-secondary">Quay lại</a>
    </form>
</div>
