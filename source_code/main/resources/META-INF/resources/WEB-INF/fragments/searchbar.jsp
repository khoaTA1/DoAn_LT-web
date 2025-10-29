<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fm" uri="jakarta.tags.fmt"%>

<div class="container mt-4">
	<div class="row justify-content-center">
		<div class="col-md-7">
			<form method="get" action="/item/search-bar" class="input-group">
				<input type="text" name="keyword" class="form-control"
					placeholder="Nhập loại sản phẩm cần tìm ..."
					value="${param.keyword != null ? param.keyword : ''}" required />
				<button type="submit" class="btn btn-danger">
					<i class="bi bi-search"></i>
				</button>
			</form>
		</div>
	</div>
	<div class="row justify-content-center">
		<div class="col-md-3">
			<label for="sortPrice" class="form-label fw-bold">Sắp xếp
				theo giá:</label> <select id="sortPrice" class="form-select">
				<option value="true" ${isAsc == 'true' ? 'selected' : ''}>Tăng
					dần</option>
				<option value="false" ${isAsc == 'false' ? 'selected' : ''}>Giảm
					dần</option>
			</select>
		</div>
	</div>
</div>

<!-- xử lí sự kiện thay đổi hành vi sắp xếp -->
<script>
document.addEventListener("DOMContentLoaded", () => {
    const sortSelect = document.getElementById("sortPrice");

    sortSelect.addEventListener("change", () => {
        const sortOrder = sortSelect.value;
        
        // nếu hiện tại có danh mục được chọn
        let CategoryId = "${selectedCategoryId != null ? selectedCategoryId : ''}";
        
        // nếu có từ khóa được nhập
        let keyword = "${param.keyword != null ? param.keyword : ''}";
        
        let uri = '';
        if (CategoryId) {
        	uri = '/item/getbycateid/' + CategoryId + '?page=1&isAsc=' + sortOrder;
        } else if (keyword) {
        	uri = '/item/search-bar?keyword=' + keyword + '&page=1&isAsc=' + sortOrder;
        } else {
        	uri = '/item/getall?page=1&isAsc=' + sortOrder;
        }

        window.location.href = uri;
        
    });
});
</script>

