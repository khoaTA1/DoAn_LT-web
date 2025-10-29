package PKG.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class inputEditUser {

	private long id;

	@NotBlank(message = "Email không được để trống!")
	@Email(message = "Email không hợp lệ")
	private String email;

	@NotBlank(message = "Tên đăng nhập không được để trống!")
	private String userName;

	private String fullName;

	private String phone;

	@NotBlank(message = "Mật khẩu không được để trống!")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?\\\":{}|<>]).*$", message = "Mật khẩu cần có độ dài từ 8 kí tự trở lên, bao gồm ít nhất 1 kí tự chữ cái thường, 1 kí tự chữ cái viết hoa, 1 kí tự số và 1 kí tự đặc biệt.")
	private String newPassw;

	private String role;

	private boolean emailVerify;
}
