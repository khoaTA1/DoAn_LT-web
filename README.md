# CONTROLLER

* Được phân công cho: Trương Anh Khoa

## Cấu trúc gói contoller
PKG
 |
 |-----controller
 |         |------ CategoryController.java
 |         |------ UserController.java
 |         |------ UploadImageController.java
 |         |------ ItemController.java
 |
 |-----Application.java

 ## Chi tiết các thành phần controller
 ### 1> UserController
 * Xử lý các request từ front end liên quan đến người dùng.
 * Chi tiết các phương thức (chưa đầy đủ):
  * GET(general/login) register() => chuyển tiếp đến trang login.jsp
  * POST(general/login) register(@RequestParam("username"), @RequestParam("password") => nếu để trống 1 trong 2 trường dữ liệu thì trả lại trang login.jsp với "msg" = "Tên đăng nhập hoặc mật khẩu không thể để trống."
                                                                                      => nếu không tìm thấy user hoặc sai mật khẩu thì trả lại trang login.jsp với "msg" = "Sai tên đăng nhập hoặc mật khẩu."
                                                                                      => nếu đăng nhập thành công, nếu user có roleid là 1 thì chuyển đến trang home.jsp của user, nếu roleid là 3 thì chuyển đến home của admin 
 ### 2> CategoryController
 * Xử lý các request từ front end liên quan đến các phân loại vật phẩm (phân loại laptop, phân loại điện thoại thông minh, phân loại tivi, phân loại máy giặt,...).
 * Chi tiết các phương thức (chưa đầy đủ):
   * 
