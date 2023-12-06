package com.myschool.service;

import java.util.List;

import com.myschool.entity.SubjectEntity;

public interface ISubjectService {

	List<SubjectEntity> getAllSubject();
	
	//List<SubjectEntity> findAllSubjectOrderByACS();
	
	SubjectEntity getSubjectById (Long subjectId);
	
	String getSubjectNameById(Long subjectId);
}
