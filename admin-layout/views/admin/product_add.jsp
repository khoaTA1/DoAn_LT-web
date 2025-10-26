<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="container mt-4">
    <h3 class="fw-bold mb-3">Thêm sản phẩm mới</h3>
	<form action="/admin/item/add" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label>Danh mục</label>
            <select name="cateId" class="form-select">
                <c:forEach var="c" items="${categories}">
                    <option value="${c.id}">${c.categoryName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label>Tên sản phẩm</label>
            <input type="text" name="name" class="form-control" required />
        </div>

        <div class="mb-3">
            <label>Giá</label>
            <input type="number" name="price" step="0.01" class="form-control" required />
        </div>

        <div class="mb-3">
            <label>Giảm giá (%)</label>
            <input type="number" name="discount" step="0.01" class="form-control" />
        </div>

        <div class="mb-3">
            <label>Thương hiệu</label>
            <input type="text" name="brand" class="form-control" />
        </div>

        <div class="mb-3">
            <label>Xuất xứ</label>
            <input type="text" name="origin" class="form-control" />
        </div>

        <div class="mb-3">
            <label>Ảnh</label>
            <input type="file" name="imageFile" class="form-control" />
        </div>

        <div class="mb-3">
            <label>Mô tả</label>
            <textarea name="description" class="form-control"></textarea>
        </div>

        <button type="submit" class="btn btn-primary">Thêm mới</button>
        <a href="/admin/item" class="btn btn-secondary">Hủy</a>
    </form>
</div>
