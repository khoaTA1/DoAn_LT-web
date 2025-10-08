# CONTROLLER

* Được phân công cho: Trương Anh Khoa

## Cấu trúc gói contoller
```
PKG  
 |  
 | ----- controller
 |           | ------ RedirectController.java
 |           | ------ CategoryController.java  
 |           | ------ UserController.java  
 |           | ------ UploadImageController.java  
 |           | ------ ItemController.java  
 |  
 | ----- Application.java
```

## Chi tiết các thành phần controller
### 1> RedirectController
* Xử lý các request chuyển tiếp trang.
* Chi tiết các phương thức (chưa đầy đủ):
  * GET(redirect/login) rlogin()
  ```
  Tham số truyền vào: không
  
  Trả về: chuyển tiếp đến trang login.jsp
  ```
  
  * GET(redirect/register) register()  
  ```
  Tham số truyền vào: không

  Trả về: chuyển tiếp đến trang register.jsp
  ```
### 2> UserController
* Xử lý các request từ front end liên quan đến người dùng.
* Chi tiết các phương thức (chưa đầy đủ):  
  * POST(user/login) login()  
  ```
  Tham số truyền vào:  
        @RequestParam("username"),
        @RequestParam("password")  

  Trả về:
        => nếu để trống 1 trong 2 trường dữ liệu thì trả lại trang login.jsp với attribute("msg", "Tên đăng nhập hoặc mật khẩu không thể để trống.").
        => nếu không tìm thấy user hoặc sai mật khẩu thì trả lại trang login.jsp với attribute("msg", "Sai tên đăng nhập hoặc mật khẩu.").
        => nếu đăng nhập thành công, nếu user có roleid là 1 thì chuyển đến trang home.jsp của user, nếu roleid là 3 thì chuyển đến home của admin.
  ```
  
  * POST(user/register) register()
  ```
  Tham số truyền vào:
        @RequestParam(name = "username", required = true),  
        @RequestParam(name = "fullname", required = false),  
        @RequestParam(name = "email", required = true),  
        @RequestParam(name = "phone", required = true),  
        @RequestParam(name = "password", required = true),  
        @RequestParam(name = "avatar", required = false) MultipartFile)

  Trả về: (từ từ update)
  ```
  * quên mật khẩu
  * cập nhật thông tin tài khoản
  * 
 ### 3> CategoryController
 * Xử lý các request từ front end liên quan đến các phân loại vật phẩm (phân loại laptop, phân loại điện thoại thông minh, phân loại tivi, phân loại máy giặt,...).
 * Chi tiết các phương thức (chưa đầy đủ):
   * Tìm kiếm phân trang

 ### 4> ItemController
 * Xử lý các request từ front end liên quan đến các sản phẳm chi tiết.
 * Chi tiết các phương thức (chưa đầy đủ):
   * Tìm kiếm tên (list có phân trang)
   * Tìm kiếm theo phân loại (category) (list có phân trang)
