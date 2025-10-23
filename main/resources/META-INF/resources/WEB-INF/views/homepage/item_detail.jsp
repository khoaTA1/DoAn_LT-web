<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fm" uri="jakarta.tags.fmt"%>
<%@ page isELIgnored="false" %>

<div>
	<%
	Object item = request.getAttribute("itemdetail");
	java.lang.reflect.Field[] fields = item.getClass().getDeclaredFields();
	java.lang.reflect.Field[] parentFields = item.getClass().getSuperclass().getDeclaredFields();

	java.util.List<String> hiddenFields = java.util.Arrays.asList("id", "categoryId", "price", "image", "discount");

	// viết lại tên các trường dữ liệu dưới dạng tiếng Việt có dấu trước khi render ra giao diện
	java.util.Map<String, String> fieldNames = new java.util.HashMap<>();
	fieldNames.put("name", "Tên sản phẩm");
	fieldNames.put("price", "Giá bán");
	fieldNames.put("brand", "Thương hiệu");
	fieldNames.put("origin", "Xuất xứ");

	fieldNames.put("dungTich", "Dung tích (lít)");
	fieldNames.put("inverter", "Có Inverter?");
	fieldNames.put("congSuatLamLanh", "Công suất làm lạnh (HP)");
	fieldNames.put("congSuat", "Công suất (W)");
	fieldNames.put("loaiQuat", "Loại quạt");
	fieldNames.put("congSuatMucGio", "Công suất - mức gió");
	fieldNames.put("canhQuat", "Cánh quạt");
	fieldNames.put("kieuTu", "Kiểu tủ");
	fieldNames.put("loaiBep", "Loại bếp");
	fieldNames.put("tongCongSuat", "Tổng công suất (W)");
	fieldNames.put("kichThuocVungNau", "Kích thước vùng nấu");
	fieldNames.put("manHinh", "Màn hình");
	fieldNames.put("doPhanGiai", "Độ phân giải");
	fieldNames.put("tanSoQuet", "Tần số quét màn hình (Hz)");
	fieldNames.put("loaiMay", "Loại máy");
	fieldNames.put("congSuatHut", "Công suất hút (Pa)");
	fieldNames.put("doOnCaoNhat", "Độ ồn cao nhất (dB)");
	fieldNames.put("loaiMayGiat", "Loại máy giặt");
	fieldNames.put("hieuSuat", "Hiệu suất");
	fieldNames.put("khoiLuongGiat", "Khối lượng giặt (Kg)");

	// viết lại dữ liệu có - không dưới dạng tiếng Việt có dấu trước khi render ra giao diện
	java.util.Map<String, String> trueOrfalseRewrite = new java.util.HashMap<>();
	trueOrfalseRewrite.put("true", "Có");
	trueOrfalseRewrite.put("false", "Không");
	%>

	<div class="card shadow">
		<!-- Ảnh minh họa -->
		<img src="/image?fname=${itemdetail.image}" class="card-img-top" />

		<div class="card-body">
			<!-- Tên sản phẩm -->
			<h3 class="card-title text-danger mb-3">
				<%=item.getClass().getMethod("getName").invoke(item)%>
			</h3>

			<h5>Thông tin sản phẩm</h5>
			<table class="table table-striped">
				<tbody>
					<%
					// Hiển thị các thuộc tính của lớp cha trước (Item)
					for (java.lang.reflect.Field field : parentFields) {
						if (hiddenFields.contains(field.getName()))
							continue;
						field.setAccessible(true);
						Object value = field.get(item);
						if (value != null) {
					%>
					<tr>
						<!-- 
						<th><%=field%></th>
						<td><%=value%></td>
						 -->
						<th><%=fieldNames.getOrDefault(field.getName(), field.getName())%></th>
						<td><%=trueOrfalseRewrite.getOrDefault(value.toString(), value.toString())%></td>
					</tr>
					<%
					}
					}
					// Hiển thị thuộc tính của lớp con (TV, Fridge, ...)
					for (java.lang.reflect.Field field : fields) {
					field.setAccessible(true);
					Object value = field.get(item);
					if (value != null) {
					%>
					<tr>
						<th><%=fieldNames.getOrDefault(field.getName(), field.getName())%></th>
						<td><%=trueOrfalseRewrite.getOrDefault(value.toString(), value.toString())%></td>
					</tr>
					<%
					}
					}
					%>
				</tbody>
			</table>
		</div>
	</div>

</div>