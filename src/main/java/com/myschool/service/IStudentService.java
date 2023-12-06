package com.myschool.service;

import java.util.List;

import com.myschool.entity.StudentEntity;
import com.myschool.entity.UserEntity;

public interface IStudentService {

	List<StudentEntity> findAllStudents();
	StudentEntity getStudentById(Long studentId);
	void save (StudentEntity studentEntity);
	
	void updateNote (Long studentId, String note);
	
	StudentEntity updateStudent(StudentEntity student);
	
	String getEmailByStudentId(Long studentId);
	String getNameByStudentId(Long studentId);
	
	void updatePhone(Long studentId, String phone);
}
