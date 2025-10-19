package PKG.entity;

import jakarta.persistence.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "passwd")
public class Password {
	@Id
	@Column(name="userId")
	private Long userId;
	
	@Column(name="hashed_password")
	private String hashedPasswd;

}
