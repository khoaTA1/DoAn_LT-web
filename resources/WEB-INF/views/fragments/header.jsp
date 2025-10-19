<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">ElectroStore</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMain">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navMain">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/">Trang chủ</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/products">Sản phẩm</a></li>
      </ul>
      <form class="d-flex me-3" action="${pageContext.request.contextPath}/search" method="get">
        <input class="form-control me-2" name="q" type="search" placeholder="Tìm kiếm..." aria-label="Search">
        <button class="btn btn-outline-primary" type="submit">Tìm</button>
      </form>
      <ul class="navbar-nav">
        <c:choose>
          <c:when test="${empty sessionScope.currentUser}">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/auth/login">Đăng nhập</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/auth/register">Đăng ký</a></li>
          </c:when>
          <c:otherwise>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="userMenu" role="button" data-bs-toggle="dropdown">
                ${sessionScope.currentUser.fullName}
              </a>
              <ul class="dropdown-menu dropdown-menu-end">
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/profile">Hồ sơ</a></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/orders">Đơn hàng</a></li>
                <li><hr class="dropdown-divider"/></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/logout">Đăng xuất</a></li>
              </ul>
            </li>
          </c:otherwise>
        </c:choose>
        <li class="nav-item">
          <a class="nav-link position-relative" href="${pageContext.request.contextPath}/cart">
            Giỏ (<span id="cartCount">${sessionScope.cartSize}</span>)
          </a>
        </li>
      </ul>
    </div>
  </div>
</nav>
