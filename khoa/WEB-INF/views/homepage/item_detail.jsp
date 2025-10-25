<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fm" uri="jakarta.tags.fmt"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*"%>

<style>
.item-description {
    text-align: justify;
    white-space: pre-line;
    line-height: 1.6;
    font-size: 15px;
}
</style>
<div>
	<%
	Object item = request.getAttribute("itemdetail");
	java.lang.reflect.Field[] fields = item.getClass().getDeclaredFields();
	java.lang.reflect.Field[] parentFields = item.getClass().getSuperclass().getDeclaredFields();

	Map<String, String> fieldNames = (Map<String, String>) request.getAttribute("fieldNames");
	Map<String, String> trueOrfalseRewrite = (Map<String, String>) request.getAttribute("trueOrFalseRewrite");

	java.util.List<String> hiddenFields = java.util.Arrays.asList("id", "categoryId", "price", "image", "discount", "description");
	%>

	<div class="card shadow">
		<!-- Ảnh minh họa -->
		<img src="/image?fname=${itemdetail.image}" class="card-img-top" />

		<div class="card-body">
			<!-- Tên sản phẩm -->
			<h3 class="card-title text-danger mb-3">
				<%=item.getClass().getMethod("getName").invoke(item)%>
			</h3>
			
			<h5>Mô tả sản phẩm</h5>
			<p class="item-description" >${itemdetail.description}</p>

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
						<th><%= fieldNames.containsKey(field.getName()) ? fieldNames.get(field.getName()) : field.getName() %></th>
						<td><%= trueOrfalseRewrite.containsKey(value.toString()) ? trueOrfalseRewrite.get(value.toString()) : value.toString() %></td>
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
