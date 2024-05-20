package com.attendance.application.attendance_application.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.application.attendance_application.dto.User;
import com.attendance.application.attendance_application.dto.UserAttendance;
import com.attendance.application.attendance_application.repository.UserAttendanceRepository;
import com.attendance.application.attendance_application.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserAttendanceRepository attendanceRepository;
	
	//To Convert time in String
	public String timeFormater() {
		Date date=new Date();
		SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm:ss aa");
	    return formatTime.format(date);
		
	}
	
	//to convert the date in String
	public String dateFormater() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	
	
	//	to add a user
	public void addUser(User user) {
		if (user.getSignUpDate()==null) {
			user.setSignUpDate(dateFormater());
		}
		userRepository.save(user);
	}

	//to get all the users
	public Iterable<User> getAllUser() {
		return userRepository.findAll();
	}
	
	//to get all the users attendances
	public List<UserAttendance> getAllUserAttendance() {
		return (List<UserAttendance>) attendanceRepository.findAll();
	}
	
	//to get user by id
	public User getUserById(long id) {
		Optional<User>  user= userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}
	
	//to get user by name
	 public User getByUserName(String name) {
	        return userRepository.getByUserName(name);
	    }
	 
	 //to make admin
	 public List<User> getActiveUser() {
	        return userRepository.findByIsAdmin(true);
	    }
	 //to get all other user
	 public List<User> getInactiveUser() {
	        return userRepository.findByIsAdmin(false);
	    }
	 
	 //to login for current day
	 public List<UserAttendance> logIn(User user) {
		 UserAttendance attendance=new UserAttendance();
		 attendance.setUser(user);
		 attendance.setLogged(true);
		 attendance.setDate(dateFormater());
		 attendance.setSignInTime(timeFormater());
		 List<UserAttendance> attendances=user.getUserAttendances();
		 attendances.add(attendance);
		 user.setUserAttendances(attendances);
		 addUser(user);
		 return attendances;
	 }
	 
	 //to signOut for current date
	 public User logOut(UserAttendance attendance) {
		 attendance.setLogged(false);
		 attendance.setSignOutTime(timeFormater());
		 User user = attendance.getUser();
		 attendance.setUser(user);
		 addUser(user);
		 return user;
	 }
	 
	 //To get attendance by id
	 public UserAttendance getUserAttendanceById(long id) {
			Optional<UserAttendance>  userAttendance= attendanceRepository.findById(id);
			if (userAttendance.isPresent()) {
				return userAttendance.get();
			}
			return null;
		}
	 
	 
	 //For marking user absent
	 public void markAbsentees() {
		    String today = dateFormater();
		    Iterable<User> users = userRepository.findAll();

		    for (User user : users) {
		        if (attendanceRepository.findByUserAndDate(user, today) == null) {
		            UserAttendance absence = new UserAttendance();
		            absence.setUser(user);
		            absence.setDate(today);
		            absence.setLogged(false); // Mark as absent
		            absence.setSignInTime(null);
		            absence.setSignOutTime(null);
		            attendanceRepository.save(absence);
		        }
		    }
		}


	

}
