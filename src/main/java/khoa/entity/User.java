package khoa.entity;

import java.io.Serializable;

import java.sql.Date;

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
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "email", columnDefinition = "varchar(50)")
	private String email;
	
	@Column(name = "uname", columnDefinition = "varchar(50)")
	private String userName;
	
	@Column(name = "fullname", columnDefinition = "nvarchar(max)")
	private String fullName;
	
	@Column(name = "password", columnDefinition = "varchar(50)")
	private String passWord;
	
	@Column(name = "avatar", columnDefinition = "varchar(50)")
	private String avatar;
	
	@Column(name = "roleid", columnDefinition = "int")
	private int roleid;
	
	@Column(name = "phone", columnDefinition = "varchar(50)")
	private String phone;
	
	@Column(name = "createddate", columnDefinition = "Date")
	private Date createdDate;

	/*
	// constructors
	public User(String email, String userName, String fullName, String passWord, String avatar, int roleid,
			String phone, Date createdDate) {
		super();
		this.email = email;
		this.userName = userName;
		this.fullName = fullName;
		this.passWord = passWord;
		this.avatar = avatar;
		this.roleid = roleid;
		this.phone = phone;
		this.createdDate = createdDate;
	}

	public User() {
		super();
	}

	// getters, setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	*/
}
