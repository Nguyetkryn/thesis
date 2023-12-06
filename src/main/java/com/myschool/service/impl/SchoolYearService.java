package com.myschool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.entity.SchoolYearEntity;
import com.myschool.repository.ISchoolYearRepository;
import com.myschool.service.ISchoolYearService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SchoolYearService implements ISchoolYearService{

	@Autowired
    private ISchoolYearRepository schoolYearRepository;

	@Override
	public List<SchoolYearEntity> getAllSchoolYears() {
		return schoolYearRepository.findAll();
	}

	@Override
	public SchoolYearEntity getYearById(Long yearId) {
		return schoolYearRepository.findById(yearId).orElse(null);
	}

}
