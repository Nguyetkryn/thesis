package com.myschool.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myschool.entity.SubjectEntity;
import com.myschool.entity.TeacherEntity;

public interface ITeacherService {

	List<TeacherEntity> findAllTeacher();

	TeacherEntity getTeacherById(Long teacherId);
	
	void save (TeacherEntity teacherEntity);
	
	void createTeacher(Long giaoVienId, Long monHocId);
	
	List<SubjectEntity> getSubjectByTeacher (Long teacherId);
}
