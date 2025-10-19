
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Trang quản trị - Điện Máy Đỏ</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <style>
    body { background-color: #f8f9fa; }
    .sidebar { height: 100vh; background-color: #b30000; color: white; position: fixed; width: 250px; }
    .sidebar a { color: white; display: block; padding: 10px 20px; text-decoration: none; }
    .sidebar a:hover, .sidebar a.active { background-color: #e60000; }
    .content { margin-left: 260px; padding: 20px; }
    .navbar { background-color: #cc0000; color: white; }
    .navbar-brand { color: white !important; }
  </style>
</head>
<body>
  <div class="sidebar">
    <h4 class="text-center mt-3">Điện Máy Đỏ Admin</h4>
    <hr>
    <a href="/admin/users"><i class="fa fa-users"></i> Người dùng</a>
    <a href="/admin/products"><i class="fa fa-box"></i> Sản phẩm</a>
    <a href="/admin/categories"><i class="fa fa-list"></i> Danh mục</a>
    <a href="/admin/discounts"><i class="fa fa-percent"></i> Chiết khấu</a>
    <a href="/admin/promotion"><i class="fa fa-gift"></i> Khuyến mãi</a>
    <a href="/admin/shipping"><i class="fa fa-truck"></i> Vận chuyển</a>
  </div>
  <div class="content">
    <nav class="navbar mb-4">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">Trang quản trị hệ thống Điện Máy Đỏ</a>
      </div>
    </nav>
    <decorator:body/>
  </div>
</body>
</html>
