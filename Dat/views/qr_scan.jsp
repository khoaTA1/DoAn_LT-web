<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>QR Payment</title>
</head>
<body>
    <h2>Quet ma QR de thanh toan</h2>
    <img src="<c:url value='/resources/views/qr_scan.jpg' />" alt="QR Code" width="250">
    <p>${message}</p>
    <a href="<c:url value='/profile-page.jsp' />">Quay ve trang chu</a>

</body>
</html>
