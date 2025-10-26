<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Đơn hàng đã được xác nhận</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .confirm-box {
            max-width: 600px;
            margin: 100px auto;
            padding: 30px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .brand-text { color: #d90429; font-weight: bold; font-size: 26px; }
    </style>
</head>
<body>
    <div class="confirm-box text-center">
        <div class="brand-text mb-3">ĐIỆN MÁY ĐỎ</div>
        <h4>Đơn hàng của bạn đã được xác nhận!</h4>
        <p>Cảm ơn bạn đã đặt hàng. Nhân viên của chúng tôi sẽ liên hệ để giao hàng sớm nhất.</p>
        <a href="<c:url value='/item/getall' />" class="btn btn-success mt-3">Quay ve trang chu</a>
    </div>
</body>
</html>
