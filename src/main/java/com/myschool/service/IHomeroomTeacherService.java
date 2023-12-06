package com.myschool.service;

import java.util.List;
import java.util.Map;

import com.myschool.entity.HomeroomTeacherEntity;
import com.myschool.entity.SemesterEntity;

public interface IHomeroomTeacherService {

	List<HomeroomTeacherEntity> findAllStHTeacher();

	List<HomeroomTeacherEntity> getHTeacherById(Long hockyId,Long lophocId,Long hocsinhId,Long giaovienId, Long nienkhoaId);
	
	List<HomeroomTeacherEntity> getHTeacherById(Long giaovienId);
	
	List<HomeroomTeacherEntity> getHTeacherById(Long giaovienId, Long hocKyId);
	
	List<HomeroomTeacherEntity> getStudentById(Long hocsinhId);
	
	void updateHomeroomTeacher (HomeroomTeacherEntity homeroomTeacherUpdates);
	
	void createHomeroomTeacher (Long hockyId,Long lophocId,Long hocsinhId,Long giaovienId, Long hanhkiemId, Long nienkhoaId);
	
	int updateConduct (Long hockyId,Long hocsinhId,Long hanhkiemId, Long giaovienId, Long lophocId);
	
	Long getConductId(Long semesterId, Long studentId);
	
	String conductAllYear(Long hocKyI, Long hocKyII);
	
	HomeroomTeacherEntity getConduct(Long semesterId, Long studentId);
	
	List<HomeroomTeacherEntity> getStudentByTeacherAndSemester (Long teacherId, Long semesterId, Long yearId);
	
	List<HomeroomTeacherEntity> getStudentBySemesterAndYear (Long semesterId, Long yearId);
	
	Long getClassIdBySemesterAndStudent (Long semesterId, Long studentId);
	
	int[] countConductLevelsForSemesterAndYear(Long nienKhoaId);
	
	List<HomeroomTeacherEntity> getStudentByNienKhoaId(Long nienkhoaId);
	
}
