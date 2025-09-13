package khoa.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "categories")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "categoryname", columnDefinition = "nvarchar(255)")
	private String categoryName;

	@Column(name = "images", columnDefinition = "nvarchar(max)")
	private String images;
	
	@Column(name = "userid", columnDefinition = "int")
	private int uid;

	/*
	// Constructors
	public Category() {
		super();
	}

	public Category(int id, String categoryName, String images, int uid) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.images = images;
		this.uid = uid;
	}

	// getters và setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}	
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}	*/
}
