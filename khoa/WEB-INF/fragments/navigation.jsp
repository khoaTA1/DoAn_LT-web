<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
	<div class="container">
		<a class="navbar-brand text-danger fw-bold" href="/homepage">Điện
			máy đỏ</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navGuest">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div id="navGuest" class="collapse navbar-collapse">
			<ul class="navbar-nav ms-auto">
				<sec:authorize access="!isAuthenticated()">
					<!-- GUEST MODE -->
					<li class="nav-item"><a href="/redirect/login"
						class="nav-link">Đăng nhập</a></li>
					<li class="nav-item"><a href="/redirect/register"
						class="nav-link">Đăng ký</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle text-danger fw-bold" id="userDropdown" href="#"
						role="button" data-bs-toggle="dropdown" > <sec:authentication
								property="name" />
					</a>
						<ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown" >
							<li><a class="dropdown-item" href="/user/profile">Hồ sơ
									cá nhân</a></li>
							<li><a class="dropdown-item" href="/user/cart">Giỏ hàng</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item text-danger" href="/user/logout">Đăng
									xuất</a></li>
						</ul></li>
				</sec:authorize>
			</ul>
		</div>
	</div>
</nav>
