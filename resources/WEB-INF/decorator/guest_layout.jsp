<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Điện Máy Đỏ</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/static/css/site.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
  <div class="container">
    <a class="navbar-brand text-danger fw-bold" href="${pageContext.request.contextPath}/">ĐIỆN MÁY ĐỎ</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navGuest"><span class="navbar-toggler-icon"></span></button>
    <div id="navGuest" class="collapse navbar-collapse">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item"><a href="${pageContext.request.contextPath}/auth/login" class="nav-link">Đăng nhập</a></li>
        <li class="nav-item"><a href="${pageContext.request.contextPath}/auth/register" class="nav-link">Đăng ký</a></li>
      </ul>
    </div>
  </div>
</nav>
<div class="container py-4">
  <sitemesh:write property="body"/>
</div>
<footer class="bg-danger text-white text-center py-3 mt-5">
  © 2025 Điện Máy Đỏ. All rights reserved.
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/site.js"></script>
</body>
</html>
