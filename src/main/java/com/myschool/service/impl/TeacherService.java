package com.myschool.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myschool.entity.SubjectEntity;
import com.myschool.entity.TeacherEntity;
import com.myschool.repository.ITeacherRepository;
import com.myschool.service.ITeacherService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TeacherService implements ITeacherService {

	private ITeacherRepository teacherRepository;

	@Override
	public List<TeacherEntity> findAllTeacher() {
		return teacherRepository.findAll();
	}

	@Override
	public TeacherEntity getTeacherById(Long teacherId) {
		return teacherRepository.findById(teacherId).orElse(null);
	}

	@Override
	public void save(TeacherEntity teacherEntity) {
		teacherRepository.save(teacherEntity);
		
	}

	@Override
	public void createTeacher(Long giaoVienId, Long monHocId) {
		List<Long> subjectIds = Arrays.asList(1L, 2L, 3L, 4L, 6L, 7L);
		List<Long> subjectIds3 = Arrays.asList(1L, 2L, 3L, 5L, 6L, 7L);
		if (monHocId == 0) {
			for (Long subject: subjectIds) {
				teacherRepository.insertTeacher(giaoVienId, subject);
			}
		}else if (monHocId == 999) {
			for (Long subject: subjectIds3) {
				teacherRepository.insertTeacher(giaoVienId, subject);
			}
		}else {
			teacherRepository.insertTeacher(giaoVienId, monHocId);
		}
	}

	@Override
	public List<SubjectEntity> getSubjectByTeacher(Long teacherId) {
		// TODO Auto-generated method stub
		return teacherRepository.findMonHocById(teacherId);
	}

	
}
