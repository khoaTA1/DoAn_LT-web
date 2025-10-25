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
@Table(name="stove")
public class Stove extends Item {
	@Column(name="loaibep",columnDefinition = "nvarchar(100)")
	private String loaiBep;
	@Column(name="tongcongsuat", columnDefinition = "float")
	private float tongCongSuat;
	@Column(name="kichthuocvungnau", columnDefinition = "nvarchar(100)")
	private String kichThuocVungNau;
	

}
