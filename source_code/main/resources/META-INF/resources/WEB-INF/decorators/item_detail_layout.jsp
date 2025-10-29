<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fm" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ĐIỆN MÁY ĐỎ</title>
<fm:setLocale value="vi_VN" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<style>
html, body {
        height: 100%;
        margin: 0;
    }
body {
        display: flex;
        flex-direction: column;
    }

    .container-fluid {
        flex-grow: 1;
    }

    footer {
        margin-top: auto;
    }
</style>
<body>
	<!-- thanh điều hướng -->
	<jsp:include page="/WEB-INF/fragments/navigation.jsp" />
	
	<!-- banner quảng cáo -->
	<jsp:include page="/WEB-INF/fragments/banner.jsp" />
	
	<!-- thanh tìm kiếm -->
	<jsp:include page="/WEB-INF/fragments/searchbar.jsp" />

	<div class="container-fluid mt-4">
		<div class="row">

			<!-- danh mục sản phẩm (side bar)  -->
			<div class="col-md-2">
				<jsp:include page="/WEB-INF/fragments/side_bar.jsp" />
			</div>

			<!-- hiển thị chi tiết sản phẩm (cột trung tâm) -->
			<div class="col-md-7">
				<sitemesh:write property="body" />
			</div>
			
			<!-- thông tin thanh toán -->
			<div class="col-md-3 px-0">
				<jsp:include page="/WEB-INF/fragments/price_inf.jsp" />
			</div>
		</div>
	</div>
	<footer class="bg-danger text-white text-center py-3 mt-5">
		Công ty Điện Máy Đỏ. Giấy phép đăng ký kinh doanh (ví dụ cho
		mục đích học thuật): 0123456789 do ... cấp ngày ... <br> Địa chỉ:
		Số 1, đường Số 1, P.Số 1, TP. Thành phố. Số điện thoại liên hệ:
		0987612345. Địa chỉ email hỗ trợ: dmd.spdep@gmail.com<br> Chịu
		trách nhiệm nội dung: Nguyễn Hương Giang, Trương Anh Khoa, Nguyễn Tấn
		Phát, Cao Minh Đạt.
	</footer>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
		crossorigin="anonymous"></script>
</body>
</html>