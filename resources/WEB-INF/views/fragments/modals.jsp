<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Rating modal -->
<div class="modal fade" id="ratingModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <form id="ratingForm" method="post" action="${pageContext.request.contextPath}/product/${product.id}/rate">
        <div class="modal-header"><h5 class="modal-title">Đánh giá sản phẩm</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
        <div class="modal-body">
          <div class="mb-2">
            <label>Chấm điểm</label>
            <select name="rating" class="form-select">
              <option value="5">5 - Xuất sắc</option>
              <option value="4">4 - Tốt</option>
              <option value="3">3 - Trung bình</option>
              <option value="2">2 - Kém</option>
              <option value="1">1 - Rất tệ</option>
            </select>
          </div>
          <div class="mb-2">
            <label>Bình luận (>=50 kí tự)</label>
            <textarea name="comment" class="form-control" rows="4" minlength="50"></textarea>
          </div>
        </div>
        <div class="modal-footer"><button class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button><button class="btn btn-primary">Gửi</button></div>
      </form>
    </div>
  </div>
</div>

<!-- Quick coupon apply modal -->
<div class="modal fade" id="couponModal" tabindex="-1">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header"><h5 class="modal-title">Áp dụng mã giảm giá</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
      <div class="modal-body">
        <div class="input-group">
          <input id="couponCode" class="form-control" placeholder="Mã giảm giá">
          <button class="btn btn-brand" type="button" onclick="applyCoupon()">Áp dụng</button>
        </div>
      </div>
    </div>
  </div>
</div>
