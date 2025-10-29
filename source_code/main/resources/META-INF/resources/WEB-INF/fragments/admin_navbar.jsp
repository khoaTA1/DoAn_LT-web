<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!--<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">  -->
<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
    <div class="container-fluid">
		<a class="navbar-brand text-danger fw-bold" href="/homepage">
			Điện máy đỏ </a>

		<!-- Nút thu gọn trên mobile -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#adminNavbar" aria-controls="adminNavbar"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Nội dung navbar -->
        <div class="collapse navbar-collapse" id="adminNavbar">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <!-- Hàng hóa -->
                <li class="nav-item">
                    <a class="nav-link" href="/admin/item/list">
                        <i class="bi bi-box-seam"></i> Hàng hóa
                    </a>
                </li>

                <!-- Người dùng -->
                <li class="nav-item">
                    <a class="nav-link" href="/admin/user">
                        <i class="bi bi-people"></i> Người dùng
                    </a>
                </li>
            </ul>

            <!-- Góc phải: tài khoản admin -->
            <ul class="navbar-nav ms-auto">
                <sec:authorize access="hasAuthority('ADMIN')">
                    <li class="nav-item dropdown">
                    	<!-- nav-link dropdown-toggle text-danger fw-bold -->
                    	<!-- nav-link dropdown-toggle text-warning fw-bold -->
                        <a class="nav-link dropdown-toggle text-danger fw-bold" href="#"
                           id="adminUserDropdown" role="button" data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle"></i> <sec:authentication property="name"/>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end"
                            aria-labelledby="adminUserDropdown">
                            <li><a class="dropdown-item" href="/admin/item/list">Trang quản trị</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item text-danger" href="/user/logout">Đăng xuất</a></li>
                        </ul>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>
