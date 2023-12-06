package com.myschool.service;

import java.util.List;

import com.myschool.entity.GradeEntity;

public interface IGradeService {

	List<GradeEntity> findAllGrades();
}
