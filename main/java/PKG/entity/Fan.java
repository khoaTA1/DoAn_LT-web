package PKG.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name="fan")
public class Fan extends Item {
	@Column(name = "loaiquat", columnDefinition = "nvarchar(100)")
	private String loaiQuat;
	@Column(name = "congsuatmucgio", columnDefinition = "nvarchar(100)")
	private String congSuatMucGio;
	@Column(name = "canhquat", columnDefinition = "nvarchar(100)")
	private String canhQuat;

}
