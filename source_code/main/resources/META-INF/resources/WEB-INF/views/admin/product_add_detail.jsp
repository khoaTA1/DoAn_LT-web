<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="container mt-4">
	<h3 class="fw-bold mb-3">Thêm sản phẩm mới</h3>
	<form id="itemForm" action="" method="post"
		enctype="multipart/form-data">
		<div class="mb-3">
			<label>Tên sản phẩm</label> <input type="text" name="name"
				class="form-control" required />
		</div>

		<div class="mb-3">
			<label>Định danh danh mục</label> <input type="text" name="cateId"
				class="form-control" value="${cate.id }" required />
		</div>

		<div class="mb-3">
			<label>Thương hiệu</label> <input type="text" name="brand"
				class="form-control" required />
		</div>

		<div class="mb-3">
			<label>Nguồn gốc xuất xứ</label> <input type="text" name="origin"
				class="form-control" required />
		</div>

		<div class="mb-3">
			<label>Mô tả</label>
			<textarea name="description" id="description" class="form-control"
				rows="5" required></textarea>
		</div>

		<div class="mb-3">
			<label>Giá</label> <input type="number" name="price" step="0.01"
				class="form-control" />
		</div>

		<div class="mb-3">
			<label>Giảm giá (%)</label> <input type="number" name="discount"
				step="0.01" class="form-control" />
		</div>

		<!-- Duyệt qua danh sách privateFields -->
		<c:forEach var="field" items="${privateFields}">
			<div class="mb-3">
				<label for="${field}">${fieldNames[field]}</label>
				<c:if test="${field == 'inverter'}">
					<!-- Nếu là inverter, sử dụng radio button -->
					<input type="radio" name="inverter" value="true" id="inverterTrue" /> True
            		<input type="radio" name="inverter" value="false"
						id="inverterFalse" /> False
        		</c:if>

				<!-- Nếu không phải inverter, sử dụng input text -->
				<c:if test="${field != 'inverter'}">
					<input type="text" name="${field}" id="${field}"
						class="form-control" />
				</c:if>
			</div>
		</c:forEach>

		<div class="mb-3">
			<input type="file" name="imageFile" class="form-control mt-2" />
		</div>

		<button type="submit" class="btn btn-success">Thêm mới sản
			phẩm</button>
		<a href="/admin/item/add" class="btn btn-secondary">Quay lại</a>
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
        formDataToSend.append("isupdate", false);
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