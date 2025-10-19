<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title><c:out value="${pageTitle != null ? pageTitle : 'ElectroStore'}"/></title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="${pageContext.request.contextPath}/static/css/site.css" rel="stylesheet"/>
</head>
<body>
  <jsp:include page="/WEB-INF/views/fragments/header.jsp" />
  <main class="container mt-4">
    <jsp:include page="${bodyContent}" />
  </main>
  <jsp:include page="/WEB-INF/views/fragments/footer.jsp" />
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/static/js/site.js"></script>
</body>
</html>
