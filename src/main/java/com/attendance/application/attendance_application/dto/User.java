package com.attendance.application.attendance_application.dto;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String userName;
	private String email;
	private String password;
	private long phoneNo;
	private String signUpDate;
	private boolean isAdmin;
	private boolean userActivated;
	

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<UserAttendance> userAttendances;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(long id, String userName, String email, String password, long phoneNo, String signUpDate,
			boolean isAdmin, boolean userActivated, List<UserAttendance> userAttendances) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.phoneNo = phoneNo;
		this.signUpDate = signUpDate;
		this.isAdmin = isAdmin;
		this.userActivated = userActivated;
		this.userAttendances = userAttendances;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(String signUpDate) {
		this.signUpDate = signUpDate;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<UserAttendance> getUserAttendances() {
		return userAttendances;
	}

	public void setUserAttendances(List<UserAttendance> userAttendances) {
		this.userAttendances = userAttendances;
	}

	
	public boolean isUserActivated() {
		return userActivated;
	}

	public void setUserActivated(boolean userActivated) {
		this.userActivated = userActivated;
	}

	
	
	
	
	

}
