package com.myschool.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myschool.entity.SubjectEntity;
import com.myschool.repository.ISubjectRepository;
import com.myschool.service.ISubjectService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SubjectService implements ISubjectService {

	private ISubjectRepository subjectRepository;

	@Override
	public List<SubjectEntity> getAllSubject() {
		return subjectRepository.findAll(Sort.by(Sort.Direction.ASC, "tenMonHoc"));
	}
	

	@Override
	public SubjectEntity getSubjectById(Long subjectId) {
		// TODO Auto-generated method stub
		return subjectRepository.findById(subjectId).orElse(null);
	}

	@Override
	public String getSubjectNameById(Long subjectId) {
		// TODO Auto-generated method stub
		return subjectRepository.findSubjectNameById(subjectId);
	}


//	@Override
//	public List<SubjectEntity> findAllSubjectOrderByACS() {
//		return subjectRepository.findAllOrderedByName();
//	}
}
