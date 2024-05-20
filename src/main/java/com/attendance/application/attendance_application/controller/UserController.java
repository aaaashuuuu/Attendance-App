package com.attendance.application.attendance_application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.attendance.application.attendance_application.dto.User;
import com.attendance.application.attendance_application.dto.UserAttendance;
import com.attendance.application.attendance_application.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//It will redirect to home page
	@GetMapping("/")
	public String login() {
		return "signIn";
	}
	
	//It will redirect to  Sign Up Page
	@GetMapping("/signUp")
	public String signup() {
		return "signUp";
	}
	
	//It will add a user through signup and redirect to home page ie. /
	@PostMapping("/addUser")
	public String addUser(@ModelAttribute("user") User user ) {
		userService.addUser(user);
		return "redirect:/";
	}
	
	// This runs every day at 11:59 PM
	// @Scheduled(cron = "sec min hr dayofmonth month dayofweek")               
	@Scheduled(cron = "0 59 23 * * ?") 
    public void markAbsentees() {
        userService.markAbsentees();
    }
	
	//It will validate the data in database and login to the user or admin
	@PostMapping("/signIn")
    public String loginValid(@RequestParam("userName") String userName,
                                  @RequestParam("password") String password,
                                  Model model) {
        User user = userService.getByUserName(userName);
        
        if (user != null && user.getUserName().equals("admin") && user.getPassword().equals("admin")) {
        	user.setAdmin(true);
        	boolean isAdmin=user.isAdmin();
        	userService.addUser(user);
        	List<User> findAll = isAdmin ? userService.getInactiveUser() : userService.getActiveUser();
    	    model.addAttribute("findAll", findAll);
    	    //It will Show All the user
            return "allUser";
        }
        if (user != null && user.getUserName().equals(userName) && user.getPassword().equals(password)) {
        	long id=user.getId();
        	//It will redirect to an end point with user id
            return "redirect:/homeUserPage/"+id;
        }
        //If login is failed it will show the home page
        return "signIn";

	}
	
	
	 // It will show the current signInDate signInTime and signOutTime and view report of current user
	 @GetMapping("/homeUserPage/{id}")
	 public String homeUserPage(@PathVariable("id") long id, Model model) {
		 User user=userService.getUserById(id);
		 List <UserAttendance> attendances= userService.getAllUserAttendance();
		 int index=attendances.size()-1;
		 if (attendances.size()-1==-1 ||attendances.get(index).isLogged()==false) {
			 attendances=userService.logIn(user);
		}
		 model.addAttribute("id",id);
		 model.addAttribute("user", user);
		 model.addAttribute("attendances", attendances.get(attendances.size()-1));
		 //It will show homeAttendancePage
		 return "homeAttendancePage";
	 }
	
	
	 
	 //Coming after view report
	 @GetMapping("/allAttendances/{id}")
	 public String userAllAttendancePage(@PathVariable("id") long id, Model model) {
		 UserAttendance attendance= userService.getUserAttendanceById(id);
		 User user=attendance.getUser();
		 List<UserAttendance> attendances = user.getUserAttendances();
		 model.addAttribute("id",id);
		 model.addAttribute("user", user);
		 model.addAttribute("attendances", attendances);
//		 it will redirect to all attendance of current user
		 return "allAttendances";
	 }
	 
	 //It will signOut the user for current day 
	 @GetMapping("/signOut/{id}")
	 public String page1(@PathVariable("id") long id, Model model) {
		 UserAttendance userAttendance=userService.getUserAttendanceById(id);
		 if (userAttendance.getSignInTime() !=null ) {
			 User user = userService.logOut(userAttendance);
			 userService.addUser(user);
			 id=user.getId();
			 model.addAttribute("id",id);
			 model.addAttribute("user", user);
			 model.addAttribute("attendances", userAttendance);
			 //It will show homeAttendancePage
			 return "homeAttendancePage";
		}
		 //For future use
		 return "redirect:/";
	 }
	 
	 //It will Sign In user for the current day
	 @GetMapping("/signIn/{id}")
	 public String page2(@PathVariable("id") long id, Model model) {
		 UserAttendance userAttendance=userService.getUserAttendanceById(id);
		 if (userAttendance.getId()==id) {
			 User user=userAttendance.getUser();
			 long uid=user.getId();
			 userAttendance.setLogged(false);
			 System.out.println(uid);
			 model.addAttribute("id",uid);
			 model.addAttribute("user", user);
			 model.addAttribute("attendances", userAttendance);
			 //It will redirect to end point with user id;
			 return "redirect:/homeUserPage/"+uid;
		}
		 return "homeAttendancePage";
		 
	 }
	 
	 
	 
}
