<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fm" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<style>
.custom-popup {
	display: none;
	position: fixed;
	z-index: 9999;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.5);
	backdrop-filter: blur(3px);
	justify-content: center;
	align-items: center;
	animation: fadeIn 0.3s ease;
}

.custom-popup-content {
	background: #fff;
	padding: 25px 30px;
	border-radius: 12px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	text-align: center;
	max-width: 350px;
	width: 90%;
	animation: slideUp 0.3s ease;
}

.custom-popup-content h5 {
	color: #dc3545;
	font-weight: bold;
	margin-bottom: 10px;
}

.custom-popup-content p {
	color: #333;
	font-size: 15px;
	margin-bottom: 20px;
}

.popup-buttons {
	display: flex;
	justify-content: center;
	gap: 15px;
}

.btn-login {
	background: #dc3545;
	color: #fff;
	border: none;
	padding: 8px 18px;
	border-radius: 6px;
	text-decoration: none;
	font-weight: 500;
	transition: background 0.2s;
}

.btn-login:hover {
	background: #bb2d3b;
}

.btn-cancel {
	background: #6c757d;
	color: #fff;
	border: none;
	padding: 8px 18px;
	border-radius: 6px;
	font-weight: 500;
	cursor: pointer;
	transition: background 0.2s;
}

.btn-cancel:hover {
	background: #5a6268;
}

/* Animation */
@keyframes fadeIn {
	from { opacity: 0; }
	to { opacity: 1; }
}
@keyframes slideUp {
	from { transform: translateY(20px); opacity: 0; }
	to { transform: translateY(0); opacity: 1; }
}
</style>

<div class="card shadow-sm p-3 mb-4">
	<h5 class="text-center text-uppercase mb-3 fw-bold text-danger">Mua
		ngay!</h5>

	<c:choose>
		<%-- nếu có giảm giá --%>
		<c:when test="${itemdetail.discount > 0}">
			<div class="text-center">
				<%-- Giá gốc bị gạch ngang và mờ --%>
				<p class="mb-1">
					<span class="fw-semibold text-dark">Giá gốc:</span> <span
						class="text-muted text-decoration-line-through"> <fm:formatNumber
							value="${itemdetail.price}" /> VNĐ
					</span>
				</p>

				<%-- Tính giá sau giảm --%>
				<p class="mb-1">
					<span class="fw-semibold text-dark">Nay chỉ còn:</span> <span
						class="fs-4 fw-bold text-danger"> <fm:formatNumber
							value="${itemdetail.price * (1 - itemdetail.discount / 100)}" />
						VNĐ
					</span>
				</p>

				<small class="text-success"> Bạn sẽ tiết kiệm được <fm:formatNumber
						value="${itemdetail.discount}" maxFractionDigits="0" />%!
				</small>
			</div>
		</c:when>

		<%-- Trường hợp không giảm giá --%>
		<c:otherwise>
			<div class="text-center">
				<p class="fs-4 fw-bold text-dark">
					<fm:formatNumber value="${itemdetail.price}" />
					VNĐ
				</p>
			</div>
		</c:otherwise>
	</c:choose>

	<hr>

	<!-- Nút thanh toán và thêm vào giỏ hàng -->
	<div class="d-grid">
		<!-- Nếu người dùng đã đăng nhập -->
		<sec:authorize access="isAuthenticated()">
			<!-- Nút thêm vào giỏ hàng -->
			<input type="number" id="quantity" name="quantity" min="1" value="1" class="form-control mb-2" />
            <button type="button" class="btn btn-danger btn-lg fw-bold w-100 mb-2" onclick="addToCart()">Thêm vào giỏ hàng</button>
            	
            <!-- Nút thanh toán -->
			<form action="/redirect/payment/" method="post">
				<input type="hidden" name="priceAfterDiscount"
					value="${itemdetail.price * (1 - itemdetail.discount / 100)}">
				<button type="submit" class="btn btn-danger btn-lg fw-bold w-100">Thanh toán</button>
			</form>
		</sec:authorize>
		<sec:authorize access="!isAuthenticated()">
			<!-- Nếu người dùng chưa đăng nhập -->
			<button type="button" class="btn btn-danger btn-lg fw-bold mb-2"
			onclick="showAlertLoginPopup()">Thêm vào giỏ hàng</button>
			
			<button type="button" class="btn btn-danger btn-lg fw-bold"
			onclick="showAlertLoginPopup()">Thanh toán</button>
		</sec:authorize>
	</div>
</div>

<div id="alertLoginPopup" class="custom-popup">
	<div class="custom-popup-content">
		<h5>Có vẻ bạn chưa đăng nhập?</h5>
		<p>Bạn cần đăng nhập để tiếp tục.</p>
		<div class="popup-buttons">
			<a href="/redirect/login" class="btn-login">Đăng nhập</a>
			<button class="btn-cancel" onclick="closeAlertLoginPopup()">Hủy</button>
		</div>
	</div>
</div>

<script>
function showAlertLoginPopup() {
	document.getElementById('alertLoginPopup').style.display = 'flex';
}

function closeAlertLoginPopup() {
	document.getElementById('alertLoginPopup').style.display = 'none';
}

function addToCart() {
    const quantity = document.getElementById("quantity").value;
    const itemId = ${itemdetail.id};

    // Gửi request đến API
    const data = {
        itemId: itemId,
        quantity: quantity
    };

    fetch('/user/cart/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            alert("Đã thêm vào giỏ hàng!");
        } else {
            alert("Có lỗi xảy ra, vui lòng thử lại.");
        }
    })
    .catch(error => {
        console.error('Error adding to cart:', error);
        alert("Có lỗi xảy ra, vui lòng thử lại.");
    });
}
</script>