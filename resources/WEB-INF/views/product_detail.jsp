<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="${product.name}"/>
<c:set var="bodyContent" value="/WEB-INF/views/product_detail_content.jsp"/>
<jsp:include page="/WEB-INF/views/decorator.jsp"/>
