<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section>
  <h2>Sản phẩm nổi bật</h2>
  <div class="row">
    <c:forEach var="p" items="${featuredProducts}">
      <div class="col-6 col-md-3 mb-4">
        <c:set var="p" value="${p}"/>
        <jsp:include page="/WEB-INF/views/fragments/product_card.jsp"/>
      </div>
    </c:forEach>
  </div>
</section>
