<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản trị - Điện máy Đỏ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/site.css" rel="stylesheet">
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
    <a class="navbar-brand fw-bold text-white" href="${pageContext.request.contextPath}/admin">ĐIỆN MÁY ĐỎ Admin</a>
    <div class="ms-auto">
      <a href="${pageContext.request.contextPath}/auth/logout" class="btn btn-outline-light btn-sm">Đăng xuất</a>
    </div>
  </div>
</nav>
<div style="height:56px"></div>
<div class="d-flex">
  <aside class="sidebar p-3 text-white">
    <h6 class="text-uppercase">Menu</h6>
    <a href="${pageContext.request.contextPath}/admin/users"><i class="fa fa-users me-2"></i>Người dùng</a>
    <a href="${pageContext.request.contextPath}/admin/products"><i class="fa fa-box me-2"></i>Sản phẩm</a>
    <a href="${pageContext.request.contextPath}/admin/orders"><i class="fa fa-shopping-cart me-2"></i>Đơn hàng</a>
    <a href="${pageContext.request.contextPath}/admin/discounts"><i class="fa fa-percent me-2"></i>Chiết khấu</a>
    <a href="${pageContext.request.contextPath}/admin/shipper"><i class="fa fa-truck me-2"></i>Nhà vận chuyển</a>
  </aside>
  <main class="flex-grow-1 p-4" style="background:#f8f9fa">
    <jsp:doBody/>
  </main>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/site.js"></script>
</body>
</html>
