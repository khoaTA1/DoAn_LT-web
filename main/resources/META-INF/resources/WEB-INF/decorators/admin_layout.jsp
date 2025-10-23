<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang quản trị - Điện Máy Đỏ</title>    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" 
    integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
      .navbar.bg-danger { background: linear-gradient(90deg,#b30000,#8a0000); }
      .btn-danger, .btn-brand { background: #b30000 !important; border-color: #b30000 !important; }
      .btn-danger:hover { background: #8a0000 !important; }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-danger fixed-top shadow-sm">
  <div class="container-fluid">
    <button class="btn btn-danger sidebar-toggle me-2"><i class="fa fa-bars"></i></button>
    <a class="navbar-brand fw-bold text-white" href="${pageContext.request.contextPath}/admin">ÃÂIÃ¡Â»ÂN MÃÂY ÃÂÃ¡Â»Â Admin</a>
    <div class="ms-auto">
      <a href="${pageContext.request.contextPath}/auth/logout" class="btn btn-outline-light btn-sm">Đăng xuất</a>
    </div>
  </div>
</nav>
<div style="height:56px"></div>
<div class="d-flex">
  <aside class="sidebar p-3 text-white">
    <h6 class="text-uppercase">Menu</h6>
    <a href="${pageContext.request.contextPath}/admin/users"><i class="fa fa-users me-2"></i>NgÃÂ°Ã¡Â»Âi dÃÂ¹ng</a>
    <a href="${pageContext.request.contextPath}/admin/products"><i class="fa fa-box me-2"></i>SÃ¡ÂºÂ£n phÃ¡ÂºÂ©m</a>
    <a href="${pageContext.request.contextPath}/admin/orders"><i class="fa fa-shopping-cart me-2"></i>ÃÂÃÂ¡n hÃÂ ng</a>
    <a href="${pageContext.request.contextPath}/admin/discounts"><i class="fa fa-percent me-2"></i>ChiÃ¡ÂºÂ¿t khÃ¡ÂºÂ¥u</a>
    <a href="${pageContext.request.contextPath}/admin/shipper"><i class="fa fa-truck me-2"></i>NhÃÂ  vÃ¡ÂºÂ­n chuyÃ¡Â»Ân</a>
  </aside>
  <main class="flex-grow-1 p-4" style="background:#f8f9fa">
    <jsp:doBody/>
  </main>
</div>
<!--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/site.js"></script> -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" 
integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>
