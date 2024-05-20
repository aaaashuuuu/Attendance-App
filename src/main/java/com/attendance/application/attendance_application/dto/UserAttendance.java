package com.attendance.application.attendance_application.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UserAttendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String date;
	private String signInTime;
	private String signOutTime;
	private boolean isLogged;
	
	@ManyToOne
	private User user;
	
	public UserAttendance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserAttendance(long id, String date, String signInTime, String signOutTime, boolean isLogged, User user) {
		super();
		this.id = id;
		this.date = date;
		this.signInTime = signInTime;
		this.signOutTime = signOutTime;
		this.isLogged = isLogged;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(String signInTime) {
		this.signInTime = signInTime;
	}

	public String getSignOutTime() {
		return signOutTime;
	}

	public void setSignOutTime(String signOutTime) {
		this.signOutTime = signOutTime;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
