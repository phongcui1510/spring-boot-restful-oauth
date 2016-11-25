package home.mechanixlab.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserModel {

	private Integer uuid;

	private String username;

	private String password;
	
	private String role;

	private String email;
	
	private String mobileno;
	
	private String city;
	
	private String district;
	
	private String age;
	
	private String profession;
	
	private String status;
	
	private String errorMsg;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date registrationdatetime;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getRegistrationdatetime() {
		return registrationdatetime;
	}

	public void setRegistrationdatetime(Date registrationdatetime) {
		this.registrationdatetime = registrationdatetime;
	}
	
	
}
