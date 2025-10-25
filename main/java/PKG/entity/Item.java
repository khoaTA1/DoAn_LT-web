package PKG.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Item {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name="categoryid", columnDefinition = "int")
    private int categoryId;
	
	@Column(name  = "name", columnDefinition = "nvarchar(100)")
    private String name;
	
	@Column(name = "price", columnDefinition = "float")
    private double price;
	
	@Column(name  = "image", columnDefinition = "varchar(255)")
    private String image;
	
	@Column(name = "discount", columnDefinition = "float")
    private double discount;
	
	@Column(name = "origin", columnDefinition = "nvarchar(100)")
    private String origin;
	
	@Column(name = "brand", columnDefinition = "varchar(255)")
    private String brand;
    
	@Column(name = "description", columnDefinition = "nvarchar(MAX)")
	private String description;
}
