<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="container mt-4">
	<h3 class="text-danger mb-3">Cập nhật thông tin tài khoản</h3>

	<form id="updateForm" class="border p-4 rounded bg-light">
		<input type="hidden" id="userId" value="${account.id}" />

		<div class="mb-3">
			<label for="userName" class="form-label">Tên đăng nhập</label> <input
				type="text" class="form-control" id="userName" name="userName"
				value="${account.userName}"  />
			<div class="text-danger small" id="err_userName"></div>
		</div>

		<div class="mb-3">
			<label for="fullName" class="form-label">Họ và tên</label> <input
				type="text" class="form-control" id="fullName" name="fullName"
				value="${account.fullName}" required />
			<div class="text-danger small" id="err_fullName"></div>
		</div>

		<div class="mb-3">
			<label for="email" class="form-label">Email</label>
			<div class="input-group">
				<input type="email" class="form-control" id="email" name="email"
					value="${account.email}"  />
				<c:choose>
					<c:when test="${account.emailVerify}">
						<span class="input-group-text bg-success text-white fw-bold">
							Đã xác thực </span>
					</c:when>
					<c:otherwise>
						<button type="button" id="verifyEmailBtn" class="btn btn-outline-primary btn-sm">Xác thực email</button>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="text-danger small" id="err_email"></div>
			<div id="verifyStatus" class="mt-2"></div>
		</div>

		<div class="mb-3">
			<label for="phone" class="form-label">Số điện thoại</label> <input
				type="text" class="form-control" id="phone" name="phone"
				value="${account.phone}" required />
			<div class="text-danger small" id="err_phone"></div>
		</div>

		<button type="submit" class="btn btn-danger">Cập nhật</button>
		<div id="successMsg" class="text-success mt-3 fw-bold"></div>
	</form>
</div>

<div class="container mt-4">
	<div class="border p-4 rounded bg-light">
		<h3 class="text-danger mb-3">Cập nhật mật khẩu bằng mã OTP gửi về
			email</h3>

		<c:choose>
			<c:when test="${account.emailVerify}">
				<span class="input-group-text bg-success text-white fw-bold">
					Email đã được xác thực, bạn có thể đổi mật khẩu ngay:<a
					href="/redirect/gencode" class="text-white fw-bold ms-2">
						Cập nhật mật khẩu</a>
				</span>
			</c:when>
			<c:otherwise>
				<span class="input-group-text bg-warning text-dark fw-bold">
					Email chưa được xác thực. Vui lòng xác thực để có thể đổi mật khẩu.
				</span>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<!-- js xử lí xác thực email -->
<script>
document.addEventListener("DOMContentLoaded", () => {
    const verifyBtn = document.getElementById("verifyEmailBtn");
    const emailInput = document.getElementById("email");
    const verifyStatus = document.getElementById("verifyStatus");

    if (verifyBtn) {
        verifyBtn.addEventListener("click", async () => {
            const email = emailInput.value.trim();
            if (!email) {
                verifyStatus.innerHTML = "<span class='text-danger'>Vui lòng nhập email hợp lệ.</span>";
                return;
            }

            try {
                const res = await fetch("/user/emailverify", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: new URLSearchParams({ email })
                });

                if (res.redirected) {
                    window.location.href = res.url;
                } else if (res.ok) {
                    verifyStatus.innerHTML = "<span class='text-success'>Mã OTP đã được gửi đến email của bạn.</span>";
                } else {
                    verifyStatus.innerHTML = "<span class='text-danger'>Gửi mã xác thực thất bại.</span>";
                }
            } catch (err) {
                console.error(err);
                verifyStatus.innerHTML = "<span class='text-danger'>Lỗi kết nối đến máy chủ.</span>";
            }
        });
    }
});
</script>

<!-- js xử lí cập nhật thông tin -->
<script>
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("updateForm");
    const userId = document.getElementById("userId").value;

    form.addEventListener("submit", async (e) => {
    	// ngăn chặn hành vi mặc định của trình duyệt
        e.preventDefault();

        // Xóa các thông báo cũ
        document.querySelectorAll("[id^='err_']").forEach(errAlert => errAlert.textContent = "");
        document.getElementById("successMsg").textContent = "";

        const formData = new FormData(form);
        
        const fullName = formData.get("fullName");
        const phone = formData.get("phone");
        const email = formData.get("email");
        const userName = formData.get("userName");
        
     	// Lấy token từ sessionStorage
        const JWTToken = sessionStorage.getItem("jwtToken");
        if (!JWTToken) {
            alert("Lỗi xác thực, vui lòng thử lại!");
            return;
        }

        try {
        	// Nạp JWT token vào header request
        	const headervalue = "Bearer " + JWTToken;
            const res = await fetch('/api/userupdate/' + userId, {
                method: "POST",
                headers: {
                	"Authorization": headervalue,
                	"Content-Type": "application/json"
                },
                body: JSON.stringify({
                	fullName,
                	phone,
                	email,
                	userName
                  	})
            });
            if (res.status === 400) {
                const errors = await res.json();
                
                for (const [field, msg] of Object.entries(errors)) {
                	console.log(field, ": ", msg);
                    const errAlert = document.getElementById(field);
                    if (errAlert) errAlert.textContent = msg;
                }
            } else if (res.ok) {
                const data = await res.json();
                document.getElementById("successMsg").textContent = "Cập nhật thành công!";
                setTimeout(() => {document.getElementById("successMsg").textContent = "";}, 5000);
                //console.log("Updated user:", data);
            } else if (res.status === 404) {
                alert("Không tìm thấy người dùng.");
            } else if (res.status === 409) {
            	alert("Trùng tên đăng nhập hoặc email.");
            } else if (res.status === 401) {
            	alert("Lỗi xác thực! Vui lòng đăng nhập lại!");
            } else {
                alert("Đã có lỗi xảy ra, vui lòng thử lại.");
            }
        } catch (err) {
            console.error(err);
            alert("Lỗi kết nối đến server.");
        }
    });
});
</script>
