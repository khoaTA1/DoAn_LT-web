<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title><sitemesh:write property="title"/> - Điện Máy Đỏ</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/static/css/site.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-danger">
  <div class="container">
    <a class="navbar-brand fw-bold text-white" href="${pageContext.request.contextPath}/">ĐIỆN MÁY ĐỎ</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navUser"><span class="navbar-toggler-icon"></span></button>
    <div id="navUser" class="collapse navbar-collapse">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item"><a href="${pageContext.request.contextPath}/cart" class="nav-link"><i class="fa fa-shopping-cart"></i> Giỏ hàng</a></li>
        <li class="nav-item"><a href="${pageContext.request.contextPath}/orders" class="nav-link">Đơn hàng</a></li>
        <li class="nav-item"><a href="${pageContext.request.contextPath}/user/profile" class="nav-link">Hồ sơ</a></li>
        <li class="nav-item"><a href="${pageContext.request.contextPath}/auth/logout" class="nav-link">Đăng xuất</a></li>
      </ul>
    </div>
  </div>
</nav>
<div class="container py-4">
  <sitemesh:write property="body"/>
</div>
<footer class="bg-light text-center text-muted py-3 mt-5">
  © 2025 Điện Máy Đỏ - Tận tâm phục vụ khách hàng.
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/site.js"></script>
</body>
</html>
