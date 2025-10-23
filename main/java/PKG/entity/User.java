package PKG.entity;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Email không được để trống!")
	@Email(message = "Email không hợp lệ")
	@Column(name = "email", columnDefinition = "varchar(50)")
	private String email;
	
	@NotBlank(message = "Tên đăng nhập không được để trống!")
	@Column(name = "uname", columnDefinition = "varchar(50)")
	private String userName;
	
	@Column(name = "fullname", columnDefinition = "nvarchar(max)")
	private String fullName;
	
	@Column(name = "avatar", columnDefinition = "varchar(50)")
	private String avatar;
	
	@Column(name = "role", columnDefinition = "varchar(100)")
	private String role;
	
	@Column(name = "phone", columnDefinition = "varchar(50)")
	private String phone;
	
	@Column(name = "createddate", columnDefinition = "Date")
	private Date createdDate;

}
