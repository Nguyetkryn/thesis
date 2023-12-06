package com.myschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myschool.entity.ScoreEntity;

import jakarta.transaction.Transactional;

@Repository
public interface IScoreRepository extends JpaRepository<ScoreEntity, Long> {

	@Query("SELECT s FROM ScoreEntity s "
			+ "WHERE s.hocKy.id = :semesterId "
			+ "AND s.hocSinh.id = :studentId "
			+ "AND s.monHoc.id = :subjectId")
	List<ScoreEntity> findById(@Param("semesterId") Long semesterId, 
							   @Param("studentId") Long studentId, 
							   @Param("subjectId") Long subjectId);
	
	@Query("SELECT s FROM ScoreEntity s "
			+ "WHERE s.hocKy.id = :semesterId "
			+ "AND s.hocSinh.id = :studentId ")
	List<ScoreEntity> findBySemesterStudetnId(@Param("semesterId") Long semesterId, 
							   @Param("studentId") Long studentId);
	
	@Query("SELECT s FROM ScoreEntity s "
			+ "WHERE s.hocKy.id = :semesterId ")
	List<ScoreEntity> findBySemesterId(@Param("semesterId") Long semesterId);
	
	@Query("SELECT s FROM ScoreEntity s "
			+ "WHERE s.hocKy.id = :semesterId "
			+ "AND s.hocSinh.id = :studentId ")
	List<ScoreEntity> findByDiemTrungBinh(
			@Param("semesterId") Long semesterId,
			@Param("studentId") Long studentId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE ScoreEntity s SET s.diemTraBai = :diemTraBai "
			+ ", s.diemGiuaKy = :diemGiuaKy "
			+ ", s.diemCuoiKy = :diemCuoiKy "
			+ ", s.diemTrungBinh = :diemTrungBinh "
			+ "WHERE s.hocKy.id = :semesterId "
			+ "AND s.hocSinh.id = :studentId "
			+ "AND s.monHoc.id = :subjectId")
	int updateScore(@Param("diemTraBai") Double diemTraBai, 
					@Param("diemGiuaKy") Double diemGiuaKy, 
					@Param("diemCuoiKy") Double diemCuoiKy, 
					@Param("diemTrungBinh") Double diemTrungBinh, 
					@Param("semesterId") Long semesterId, 
					@Param("studentId") Long studentId, 
					@Param("subjectId") Long subjectId);
	
	@Transactional
	@Modifying
	@Query(value = "insert into KET_QUA_HOC_TAP (diem_tra_bai, diem_giua_ky, diem_cuoi_ky, diem_trung_binh, hocky_id, hocsinh_id, monhoc_id) "
			+ "values ( :diemTraBai, :diemGiuaKy, :diemCuoiKy, :diemTrungBinh, :hocKyId, :hocSinhId, :monHocId)", 
			nativeQuery=true)
	void insertScore (@Param("diemTraBai") Double diemTraBai, 
					@Param("diemGiuaKy") Double diemGiuaKy, 
					@Param("diemCuoiKy") Double diemCuoiKy, 
					@Param("diemTrungBinh") Double diemTrungBinh, 
					@Param("hocKyId") Long hocKyId, 
					@Param("hocSinhId") Long hocSinhId, 
					@Param("monHocId") Long monHocId);
	
	@Query("SELECT s.diemTrungBinh FROM ScoreEntity s " +
		       "WHERE s.hocSinh.id = :studentId AND s.hocKy.id = :semesterId")
	List<Double> findAverageScoresByStudentAndSemester(@Param("studentId") Long studentId,
		                                               @Param("semesterId") Long semesterId);
	
	@Query("SELECT s FROM ScoreEntity s " +
		       "WHERE s.hocKy.id = :semesterId AND s.monHoc.id = :subjectId")
	List<ScoreEntity> getScoreBySemesterAndSubject(@Param("semesterId") Long semesterId,
			@Param("subjectId") Long subjectId );

}
