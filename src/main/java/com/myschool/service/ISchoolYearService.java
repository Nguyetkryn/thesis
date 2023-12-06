package com.myschool.service;

import java.util.List;

import com.myschool.entity.SchoolYearEntity;

public interface ISchoolYearService {
	
	List<SchoolYearEntity> getAllSchoolYears();
	
	SchoolYearEntity getYearById(Long yearId);
}
