<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Điện máy đỏ</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
	
<fmt:setLocale value="vi-VN"/>
<style>
body {
	background-color: #f8f9fa;
}

.payment-box {
	max-width: 500px;
	margin: 80px auto;
	padding: 30px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	background-color: #ffffff;
}

.brand-text {
	color: #d90429;
	font-weight: bold;
	font-size: 28px;
}

.btn-red {
	background-color: #d90429;
	border-color: #d90429;
}

.btn-red:hover {
	background-color: #a90321;
	border-color: #a90321;
}
</style>
</head>
<body>
	<div class="container">
		<div class="payment-box">
			<div class="text-center mb-4">
				<div class="brand-text">ĐIỆN MÁY ĐỎ</div>
				<p class="text-muted">Thanh toán đơn hàng của bạn</p>
			</div>

			<form id="payment-form" method="POST" action="">
				<!-- Trường ẩn chứa thông tin userId -->
				<input type="hidden" id="userId" name="userId" value="${userid }">

				<div class="mb-3">
					<label for="amount" class="form-label">Số tiền (VND)</label> <input
						type="hidden" id="amount" name="amount" class="form-control"
						value="${price }">
					<fmt:formatNumber type="number" value="${price }" var="formattedPrice" />
					<input type="text" value="${formattedPrice }" class="form-control"
						readonly>
				</div>

				<div class="mb-3">
					<label for="currency" class="form-label">Loại tiền tệ</label> <input
						type="text" id="currency" name="currency" class="form-control"
						value="vnd" readonly>
				</div>

				<div id="card-element" class="mb-3"></div>

				<button id="submit" class="btn btn-red btn-lg mt-3 w-100">Thanh
					toán</button>
			</form>

			<!-- Thông báo lỗi hoặc thành công -->
			<div id="payment-result" class="mt-3"></div>
		</div>
	</div>

	<script src="https://js.stripe.com/v3/"></script>
	<script>
	
    
    document.addEventListener("DOMContentLoaded", function() {
        // Stripe public key
        const stripe = Stripe('pk_test_51SMg3aKQuP6iqjXYrddTRc4h7P0eTQ6jhR5Wu1MiwWLMruKNP536R3DZbldgt17hI5662sHtN71nRQTDTJdwF5cq00MRS2ibjk');
        const elements = stripe.elements();
        
        const card = elements.create('card');
        card.mount('#card-element');

        const form = document.getElementById('payment-form');
        form.addEventListener('submit', async function(event) {
            event.preventDefault();

            const amount = document.getElementById('amount') ? document.getElementById('amount').value : '';
            const userId = document.getElementById('userId') ? document.getElementById('userId').value : '';

            console.log(amount, userId)
            if (!amount || !userId) {
                alert("Lỗi cung cấp thông tin");
                return;
            }

            
            const JWTToken = sessionStorage.getItem("jwtToken");
       	 	if (!JWTToken) {
            	alert("Lỗi xác thực, vui lòng thử lại!");
            	return;
        	}
       	 	const headervalue = "Bearer " + JWTToken;
     		
       	 	// Gửi yêu cầu tạo PaymentIntent từ backend
       	 	// 'Authorization': JWTToken,
            const response = await fetch('/payment', {
                method: 'POST',
                headers: {
                	'Authorization': headervalue,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    amount: amount,  // Số tiền thanh toán
                    currency: 'vnd',  // Loại tiền tệ VNĐ
                    userId: userId    // ID người dùng
                })
            });

            const paymentIntent = await response.json();  // Nhận PaymentIntent từ backend
            console.log(paymentIntent);
            
            // Kiểm tra xem PaymentIntent đã được tạo thành công chưa
            if (paymentIntent && paymentIntent.client_secret) {
                // Xác nhận thanh toán với Stripe
                const {error, paymentIntent: confirmedPaymentIntent} = await stripe.confirmCardPayment(paymentIntent.client_secret, {
                    payment_method: {
                        card: card,
                        billing_details: {
                            name: 'Người Thanh Toán',  // Tên người thanh toán, bạn có thể thay đổi theo yêu cầu
                        },
                    },
                });

                // Xử lý kết quả thanh toán
                const paymentResultDiv = document.getElementById('payment-result');
                if (error) {
                    paymentResultDiv.innerHTML = '<div class="alert alert-danger">Lỗi thanh toán: ' + error.message + '</div>';
                } else {
                    if (confirmedPaymentIntent.status === 'succeeded') {
                        paymentResultDiv.innerHTML = '<div class="alert alert-success">Thanh toán thành công! mã giao dịch: ' + confirmedPaymentIntent.id + '<br>Bạn sẽ được quay lại sau 2 giây nữa.</div>';
                        setTimeout(() => {
                            window.history.back(); 
                        }, 2000);
                    } else {
                        paymentResultDiv.innerHTML = '<div class="alert alert-warning">Thanh toán không thành công, trạng thái: ' + confirmedPaymentIntent.status + '</div>';
                    }
                }
            } else {
                alert("Không thể thanh toán.");
            }
        });
    });
</script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
		crossorigin="anonymous"></script>
</body>
</html>