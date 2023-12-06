package com.myschool.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myschool.entity.ClassEntity;
import com.myschool.repository.IClassRepository;
import com.myschool.service.IClassService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClassService implements IClassService {
	
	private IClassRepository classRepository;
	
	@Override
	public List<ClassEntity> getAllClass() {
		return classRepository.findAll();
	}

	@Override
	public ClassEntity getClassById(Long classId) {
		return classRepository.findById(classId).orElse(null);
	}

}
