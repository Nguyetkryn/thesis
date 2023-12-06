package com.myschool.service;

import java.util.List;

import com.myschool.entity.ClassEntity;
import com.myschool.entity.TeacherClassSemesterEntity;

public interface ITeacherClassSemesterService {
	
	List<TeacherClassSemesterEntity> findTCSAll();

	List<TeacherClassSemesterEntity> getTCSById(Long semesterId, Long teacherId, Long classId);
	
//	TeacherClassSemesterEntity getTeacherClassSemesterById(Long semesterId, Long teacherId, Long classId);
	
	void createClassSemester(TeacherClassSemesterEntity teacherClassSemesterCreate);
	
	List<TeacherClassSemesterEntity> getTeacherIdBySCId(Long semesterId,Long classId);
	
	void createTCS(Long teacherId, Long classId, Long semesterId);
	
	List<ClassEntity> getClassesByTeacherAndSemester(Long teacherId, Long semesterId);
	List<ClassEntity> getClassesBySemester(Long hocKyId);
	List<TeacherClassSemesterEntity> getTeacherByClass(Long lophocId);
}
