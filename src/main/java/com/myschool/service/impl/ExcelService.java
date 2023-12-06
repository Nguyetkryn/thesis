package com.myschool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.entity.HomeroomTeacherEntity;
import com.myschool.entity.TeacherEntity;
import com.myschool.repository.IHomeroomTeacherRepository;
import com.myschool.service.IExcelService;

@Service
public class ExcelService implements IExcelService {

	@Autowired
	private IHomeroomTeacherRepository homeroomTeacherRepository;
	
	@Override
	public List<HomeroomTeacherEntity> findAllGiaoVien(TeacherEntity teacherEntity){
		
		return homeroomTeacherRepository.findByGiaoVien(teacherEntity);
	}
}
