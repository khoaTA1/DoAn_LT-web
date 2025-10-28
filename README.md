# ĐỒ ÁN LẬP TRÌNH WEB NHÓM 20
----------------------------------
## Thông tin chung
* Đề tài: xây dựng website bán đồ điện tử, công nghệ điện máy đỏ sử dụng công nghệ spring boot + JSP/JSTL + bootstrap + JPA + SQLserver + decorator sitemesh + JWT + WebSocket
* Thành viên nhóm:
  * Cao Minh Đạt
  * Nguyễn Hương Giang
  * Trương Anh Khoa
  * Nguyễn Tấn Phát
 
## Phát triển
* Phân chia công việc theo tầng:
  * Front end (Giang): cấu trúc thư mục chứa các file jsp, trang trí giao diện web, làm việc với controller.
  * Controller (Khoa): xử lí yêu cầu từ người dùng (front end), làm việc với front end và service (implement).
  * Service, implement (Đạt): xử lí dữ liệu thông qua các phương thức đã được phát triển ở tầng DAO, làm việc với controller và DAO.
  * Data access object (Phát): làm việc với cơ sở dữ liệu và service. implement, cấu hình kết nối cơ sở dữ liệu và JPA spring boot trên file application.properties.
* Phân chia công việc theo tính năng:
  * Hương Giang: tính năng tạo, cập nhật, đọc, xóa sản phẩm cho admin; tính năng thêm, xóa, đọc giỏ hàng cho user; giao diện web admin và giỏ hàng của người dùng.
  * Anh Khoa: Tính năng đăng nhập và đăng ký, khôi phục mật khẩu, tính năng đọc và tìm kiếm sản phẩm, tính năng cập nhật hồ sơ người dùng; giao diện web trang chủ và hồ sơ người dùng.
  * Tấn Phát: Tính năng đánh giá sản phẩm, tính năng kênh tin nhắn chung và giao diện web kênh tin nhắn chung.
  * Minh Đạt: Tính năng thanh toán sản phẩm bằng thẻ điện tử và giao diện thanh toán.
