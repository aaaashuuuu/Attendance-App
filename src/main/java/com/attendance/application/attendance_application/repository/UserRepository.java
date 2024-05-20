package com.attendance.application.attendance_application.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.attendance.application.attendance_application.dto.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User getByUserName(String name);
 
	List<User> findByIsAdmin(boolean b);

}
