package com.myschool.service;

import java.util.List;

import com.myschool.entity.SchoolYearEntity;
import com.myschool.entity.SemesterEntity;

public interface ISemesterService {

	List<SemesterEntity> getAllSemesters();

	List<SemesterEntity> findSemesterEntitiesBySchoolYearId(Long schoolYearId);
	
	SemesterEntity getSemesterById(Long semesterId);
	
	List<Long> getSemesterIdBySchoolYearId(Long schoolYearId);

	Long getNienKhoaIdByHocKyId(Long hocKyId);
}
