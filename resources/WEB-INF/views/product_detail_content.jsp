<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
  <div class="col-md-6">
    <img src="${product.imageUrl}" class="img-fluid" alt="${product.name}">
  </div>
  <div class="col-md-6">
    <h3>${product.name}</h3>
    <p class="text-danger h4">${product.price}đ</p>
    <form action="${pageContext.request.contextPath}/cart/add" method="post">
      <input type="hidden" name="productId" value="${product.id}" />
      <div class="mb-2">
        <label>Số lượng</label>
        <input type="number" name="qty" value="1" min="1" class="form-control" />
      </div>
      <button class="btn btn-primary">Thêm vào giỏ</button>
    </form>
  </div>
</div>
<hr/>
<h5>Bình luận</h5>
<form id="commentForm" method="post" action="${pageContext.request.contextPath}/product/${product.id}/comment" enctype="multipart/form-data" onsubmit="return validateComment()">
  <div class="mb-2">
    <textarea id="commentText" name="text" class="form-control" rows="4" placeholder="Viết bình luận (tối thiểu 50 ký tự)"></textarea>
  </div>
  <div class="mb-2">
    <input type="file" name="media" accept="image/*,video/*" />
  </div>
  <button class="btn btn-outline-success">Gửi bình luận</button>
</form>
<script>
function validateComment(){
  var t = document.getElementById('commentText').value.trim();
  if(t.length < 50){
    alert('Bình luận phải có tối thiểu 50 ký tự.');
    return false;
  }
  return true;
}
</script>
