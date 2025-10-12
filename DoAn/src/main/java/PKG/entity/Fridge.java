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
@Table(name="fridge")
public class Fridge extends Item {
	@Column(name="kieutu", columnDefinition = "nvarchar(100)")
	private String kieuTu;
	@Column(name="dungtich", columnDefinition = "float")
	private float dungTich;
	@Column(name="congsuat", columnDefinition = "float")
	private Double congSuat;

}
