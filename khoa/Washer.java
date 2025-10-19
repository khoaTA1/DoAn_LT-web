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
@Table(name = "washer")
public class Washer extends Item {
	@Column(name="loaimaygiat", columnDefinition = "nvarchar(100)")
	private String loaiMayGiat;
	@Column(name="hieusuat", columnDefinition = "nvarchar(100)")
    private String hieuSuat;
	@Column(name="khoiluonggiat", columnDefinition = "float")
    private float khoiLuongGiat;
    
   

}
