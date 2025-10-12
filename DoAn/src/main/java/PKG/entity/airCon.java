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
@Table(name="aircon")
public class airCon extends Item {
	@Column( name  = "loaimay", columnDefinition = "varchar(100)")
	private String loaiMay;
	@Column(name = "inverter", columnDefinition = "bit")
	private boolean inverter;
	@Column (name = "congsuatlamlanh", columnDefinition = "float")
	private float congSuatLamLanh;
}
