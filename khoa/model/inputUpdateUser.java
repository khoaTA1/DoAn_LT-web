package PKG.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class inputUpdateUser {

	@NotBlank(message = "Email không được để trống!")
	@Email(message = "Email không hợp lệ")
	private String email;

	@NotBlank(message = "Tên đăng nhập không được để trống!")
	private String userName;

	private String fullName;

	private String phone;
}
