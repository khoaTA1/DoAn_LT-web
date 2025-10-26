<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<div class="container mt-4">
    <h3 class="fw-bold mb-3">Chỉnh sửa sản phẩm</h3>
	<form action="/admin/item/edit" method="post" enctype="multipart/form-data">
        <input type="hidden" name="itemId" value="${item.id}">
        <input type="hidden" name="cateId" value="${item.categoryId}">

        <div class="mb-3">
            <label>Tên sản phẩm</label>
            <input type="text" name="name" class="form-control" value="${item.name}" required />
        </div>

        <div class="mb-3">
            <label>Giá</label>
            <input type="number" name="price" step="0.01" class="form-control" value="${item.price}" />
        </div>

        <div class="mb-3">
            <label>Giảm giá (%)</label>
            <input type="number" name="discount" step="0.01" class="form-control" value="${item.discount}" />
        </div>

        <div class="mb-3">
            <label>Ảnh</label><br>
            <img src="${pageContext.request.contextPath}${item.image}" width="100" height="100">
            <input type="file" name="imageFile" class="form-control mt-2" />
        </div>

        <button type="submit" class="btn btn-success">Lưu thay đổi</button>
        <a href="/admin/item?cateId=${item.categoryId}" class="btn btn-secondary">Quay lại</a>
    </form>
</div>
