<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<sec:authorize access="isAuthenticated()">
		<p style="color: green;">
			User đã đăng nhập:
			<sec:authentication property="name" />
		</p>
	</sec:authorize>

	<sec:authorize access="!isAuthenticated()">
		<p style="color: red;">User chưa đăng nhập</p>
	</sec:authorize>
</body>
</html>