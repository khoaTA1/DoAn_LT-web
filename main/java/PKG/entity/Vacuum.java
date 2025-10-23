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
@Table(name="vacuum")
public class Vacuum extends Item {
	@Column(name="loaimay", columnDefinition = "nvarchar(100)")
	private String loaiMay;
	@Column(name="congsuathut", columnDefinition = "float")
	private float congSuatHut;
	@Column(name="dooncaonhat", columnDefinition = "float")
	private float doOnCaoNhat;
	

}
