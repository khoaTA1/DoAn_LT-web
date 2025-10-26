<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>QR Payment</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #f5f7fa, #c3cfe2);
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background: #fff;
            padding: 30px 40px;
            border-radius: 15px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            text-align: center;
            width: 320px;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        img {
            border-radius: 10px;
            margin-bottom: 20px;
        }
        p {
            color: #555;
            margin-bottom: 20px;
        }
        a {
            text-decoration: none;
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border-radius: 6px;
            transition: 0.3s;
        }
        a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Quet ma QR de thanh toan</h2>
		<img src="<c:url value='/images/qr_scan.jpg' />" alt="QR Code" width="250">
        <p>${message}</p>
		<a href="<c:url value='/item/getall' />" class="btn btn-success mt-3">Quay ve trang chu</a>
    </div>
</body>
</html>
