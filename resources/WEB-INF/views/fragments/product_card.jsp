<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="product-card-fragment col-6 col-md-3 mb-4">
  <div class="card h-100 card-highlight">
    <img src="${p.imageUrl}" class="card-img-top" alt="${p.name}">
    <div class="card-body d-flex flex-column">
      <h6 class="card-title">${p.name}</h6>
      <p class="mb-2 text-danger fw-bold">${p.price}đ</p>
      <div class="d-flex justify-content-between align-items-center mt-2">
        <a href="${pageContext.request.contextPath}/product/${p.id}" class="btn btn-sm btn-outline-primary">Chi tiết</a>
        <div>
          <button class="btn btn-sm" onclick="toggleFavorite(${p.id}, this)"><i class="fa fa-heart"></i></button>
        </div>
      </div>
    </div>
  </div>
</div>
