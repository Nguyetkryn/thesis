package com.myschool.service;

import java.util.List;

import com.myschool.entity.ClassEntity;

public interface IClassService {

	List<ClassEntity> getAllClass();
	
	ClassEntity getClassById(Long classId);
	
	//List<ClassEntity> getClassById(Long classId)
}
