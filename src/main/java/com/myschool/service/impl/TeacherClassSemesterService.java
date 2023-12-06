package com.myschool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myschool.entity.ClassEntity;
import com.myschool.entity.TeacherClassSemesterEntity;
import com.myschool.repository.ITeacherClassSemesterRepository;
import com.myschool.service.ITeacherClassSemesterService;

@Service
public class TeacherClassSemesterService implements ITeacherClassSemesterService {

	@Autowired
	private ITeacherClassSemesterRepository tcsRepository;

	@Override
	public List<TeacherClassSemesterEntity> getTCSById(Long semesterId, Long teacherId, Long classId) {
		List<TeacherClassSemesterEntity> tcsEntities = tcsRepository.findById(semesterId, teacherId, classId);
		if (tcsEntities != null) {
			return tcsEntities;
		}
		return null;
	}

	@Override
	public List<TeacherClassSemesterEntity> findTCSAll() {
		return tcsRepository.findAll();
	}

//	@Override
//	public TeacherClassSemesterEntity getTeacherClassSemesterById(Long semesterId, Long teacherId, Long classId) {
//		return tcsRepository.findTeacherClassSemesterById(semesterId, teacherId, classId);
//	}

	@Override
	public void createClassSemester(TeacherClassSemesterEntity teacherClassSemesterCreate) {
		tcsRepository.save(teacherClassSemesterCreate);		
	}

	@Override
	public List<TeacherClassSemesterEntity> getTeacherIdBySCId(Long semesterId, Long classId) {
		return tcsRepository.findTeacherIdBySCId(semesterId, classId);
	}

	@Override
	public void createTCS(Long teacherId, Long classId, Long semesterId) {
		tcsRepository.insertTCS(teacherId, classId, semesterId);
	}

	@Override
	public List<ClassEntity> getClassesByTeacherAndSemester(Long teacherId, Long semesterId) {
		// TODO Auto-generated method stub
		return tcsRepository.getClassesByTeacherAndSemester(teacherId, semesterId);
	}

	@Override
	public List<ClassEntity> getClassesBySemester(Long hocKyId) {
		// TODO Auto-generated method stub
		return tcsRepository.getClassesBySemester(hocKyId);
	}

	@Override
	public List<TeacherClassSemesterEntity> getTeacherByClass(Long lophocId) {
		// TODO Auto-generated method stub
		return tcsRepository.getTeacherByClass(lophocId);
	}

}
