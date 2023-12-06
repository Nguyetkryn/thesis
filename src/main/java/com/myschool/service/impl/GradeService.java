package com.myschool.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myschool.entity.GradeEntity;
import com.myschool.repository.IGradeRepository;
import com.myschool.service.IGradeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GradeService implements IGradeService {
	
	private IGradeRepository gradeRepository;
	
	@Override
	public List<GradeEntity> findAllGrades() {
		return gradeRepository.findAll();
	}

}
