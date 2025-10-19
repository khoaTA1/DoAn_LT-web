<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Use decorator: set attributes and include decorator.jsp --%>
<c:set var="pageTitle" value="Trang chủ - Guest"/>
<c:set var="bodyContent" value="/WEB-INF/views/guest_content.jsp"/>
<jsp:include page="/WEB-INF/views/decorator.jsp"/>
