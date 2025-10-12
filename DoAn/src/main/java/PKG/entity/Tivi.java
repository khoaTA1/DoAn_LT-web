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
@Table(name="tivi")
public class Tivi extends Item {
	@Column(name="manhinh",columnDefinition = "nvarchar(100)" )
	private String manHinh;
	@Column(name="dophangiai", columnDefinition = "float")
	private float doPhanGiai;
	@Column(name="tansoquet", columnDefinition = "float")

	private float tanSoQuet;
	
	

}
