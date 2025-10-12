package PKG.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="fan")
public class Fan {
	@Column(name = "loaiquat", columnDefinition = "nvarchar(100)")
	private String loaiQuat;
	@Column(name = "congsuatmucgio", columnDefinition = "float")
	private float congSuatMucGio;
	@Column(name = "canhquat", columnDefinition = "int")
	private int canhQuat;

}
