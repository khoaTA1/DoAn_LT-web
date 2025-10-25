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
@Table(name="cooker")
public class Cooker extends Item {
	@Column(name="dungtich", columnDefinition = "float")
	private float dungTich;
	@Column(name="congsuat", columnDefinition = "float")
	private float congSuat;
	

}
