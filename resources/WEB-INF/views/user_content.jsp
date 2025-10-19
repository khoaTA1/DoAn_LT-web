<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section>
  <h3>Sản phẩm mới</h3>
  <div id="newProducts" class="row">
    <c:forEach var="p" items="${newProducts}">
      <div class="col-6 col-md-3 mb-4">
        <c:set var="p" value="${p}"/>
        <jsp:include page="/WEB-INF/views/fragments/product_card.jsp"/>
      </div>
    </c:forEach>
  </div>
  <nav aria-label="Page navigation">
    <ul class="pagination">
      <c:if test="${page>1}">
        <li class="page-item"><a class="page-link" href="?page=${page-1}">Trước</a></li>
      </c:if>
      <c:forEach begin="1" end="${totalPages}" var="i">
        <li class="page-item ${i==page?'active':''}"><a class="page-link" href="?page=${i}">${i}</a></li>
      </c:forEach>
      <c:if test="${page<totalPages}">
        <li class="page-item"><a class="page-link" href="?page=${page+1}">Sau</a></li>
      </c:if>
    </ul>
  </nav>
</section>
