package com.myschool.service;

import java.util.List;

import com.myschool.entity.HomeroomTeacherEntity;
import com.myschool.entity.TeacherEntity;

public interface IExcelService {

	List<HomeroomTeacherEntity>  findAllGiaoVien(TeacherEntity teacherEntity);
}
