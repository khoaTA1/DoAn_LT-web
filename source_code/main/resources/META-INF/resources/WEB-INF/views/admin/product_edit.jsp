<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page import="java.util.*"%>

<div class="container mt-4">
	<h3 class="fw-bold mb-3">Chỉnh sửa sản phẩm</h3>
	<form id="itemForm" action="" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="itemId" value="${item.id}">

		<div class="mb-3">
			<label>Tên sản phẩm</label> <input type="text" name="name"
				class="form-control" value="${item.name}" required />
		</div>

		<div class="mb-3">
			<label>Định danh danh mục</label> <input type="text" name="cateId"
				class="form-control" value="${item.categoryId}" required />
		</div>

		<div class="mb-3">
			<label>Thương hiệu</label> <input type="text" name="brand"
				class="form-control" value="${item.brand}" required />
		</div>

		<div class="mb-3">
			<label>Nguồn gốc xuất xứ</label> <input type="text" name="origin"
				class="form-control" value="${item.origin}" required />
		</div>

		<div class="mb-3">
			<label>Mô tả</label>
			<textarea name="description" id="description" class="form-control"
				rows="5" required>${item.description}</textarea>
		</div>

		<div class="mb-3">
			<label>Giá</label> <input type="number" name="price" step="0.01"
				class="form-control" value="${item.price}" />
		</div>

		<div class="mb-3">
			<label>Giảm giá (%)</label> <input type="number" name="discount"
				step="0.01" class="form-control" value="${item.discount}" />
		</div>

		<!-- Duyệt qua danh sách privateFields -->
		<c:forEach var="field" items="${privateFields}">
			<div class="mb-3">
				<label for="${field}">${fieldNames[field]}</label>
				<c:if test="${field == 'inverter'}">
					<!-- Nếu là inverter, sử dụng radio button -->
					<input type="radio" name="inverter" value="true" id="inverterTrue"
						${item.inverter == 'true' ? 'checked' : ''} /> True
            			<input type="radio" name="inverter" value="false"
						id="inverterFalse" ${item.inverter == 'false' ? 'checked' : ''} /> False
        		</c:if>

				<!-- Nếu không phải inverter, sử dụng input text -->
				<c:if test="${field != 'inverter'}">
					<input type="text" name="${field}" id="${field}"
						class="form-control" value="${item[field]}" />
				</c:if>
			</div>
		</c:forEach>

		<div class="mb-3">
			<label>Ảnh</label><br> <img
				src="${pageContext.request.contextPath}/image?fname=${item.image}"
				width="100" height="100"> <input type="file" name="imageFile"
				class="form-control mt-2" />
		</div>

		<button type="submit" class="btn btn-success">Lưu thay đổi</button>
		<a href="/admin/item/list?cateId=${item.categoryId}"
			class="btn btn-secondary">Quay lại</a>
	</form>
</div>

<script>
    document.getElementById('itemForm').addEventListener('submit', function(event) {
        event.preventDefault();

        // Thu thập tụ động tất cả các input trong form
        var formData = new FormData(this);

        // Chuyển đổi dữ liệu từ form thành JSON
        var dataObject = {};
        formData.forEach(function(value, key) {
        	if (key !== 'imageFile') {
                dataObject[key] = value;
            }
        });

        // Chuyển đối tượng thành JSON string
        var dataChain = JSON.stringify(dataObject);

        // Tạo đối tượng FormData mới để gửi lên server
        var formDataToSend = new FormData();
        formDataToSend.append("data", dataChain);
        formDataToSend.append("image", formData.get('imageFile'));
        formDataToSend.append("isupdate", true);
        console.log(dataChain);
        // Gửi dữ liệu lên server
        fetch('/admin/item/addoredit', {
            method: 'POST',
            body: formDataToSend
        })
        .then(res => res.json())  // giả sử bạn sẽ nhận dữ liệu JSON từ server
        .then(data => {
        	console.log(data);
            alert(data.msg);
            // Xử lý sau khi gửi thành công nếu cần
        })
        .catch(error => {
            console.error('Error:', error);
        });
    });
</script>