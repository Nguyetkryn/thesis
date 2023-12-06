package com.myschool.service;

import java.util.List;

import com.myschool.entity.ScoreEntity;
import com.myschool.entity.SemesterEntity;
import com.myschool.entity.StudentEntity;
import com.myschool.entity.SubjectEntity;

public interface IScoreService {
	List<ScoreEntity> getAllScores();
	
	List<ScoreEntity> getScoreById(Long semesterId, Long subjectId, Long studentId);
	
	List<ScoreEntity> getScoreBySemesterStudentId(Long hocKyId,Long studentId);
	
	List<ScoreEntity> getScoreBySemesterId(Long semesterId);
	
	void updateScore(ScoreEntity scoreUpdates);
	
	ScoreEntity mapToDTO(ScoreEntity score);
	
	void createScore(Double diemTraBai, Double diemGiuaKy, Double diemCuoiKy, Double diemTrungBinh, Long hocKyId, Long monHocId, Long hocSinhId);
	
	void createScores(Long hocKyId, Long hocSinhId, Long lopHocId);
	
	Double semesterGPA(Long semesterId, Long studentId);
	
	String classification(Long semesterId, Long studentId, String conduct);
	
	int[] getAverageScoresByStudentAndSemester(List<Long> studentIds, Long semesterId);
	
	List<ScoreEntity> getScoreBySemesterAndSubject(Long semesterId, Long subjectId );

	int[] countAacademicAbility(Long nienkhoaId, List<Long> studentIds);
	
}
