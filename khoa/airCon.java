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
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name="aircon")
public class airCon extends Item {
	@Column( name  = "loaimay", columnDefinition = "nvarchar(100)")
	private String loaiMay;
	
	@Column(name = "inverter", columnDefinition = "bit")
	private boolean inverter;
	
	@Column (name = "congsuatlamlanh", columnDefinition = "float")
	private float congSuatLamLanh;
}
