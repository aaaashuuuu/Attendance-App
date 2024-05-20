package com.attendance.application.attendance_application.repository;

import org.springframework.data.repository.CrudRepository;

import com.attendance.application.attendance_application.dto.User;
import com.attendance.application.attendance_application.dto.UserAttendance;

public interface UserAttendanceRepository extends CrudRepository<UserAttendance, Long> {

	UserAttendance findByUserAndDate(User user, String date);
}
