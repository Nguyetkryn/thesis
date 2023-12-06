package com.myschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myschool.entity.ClassEntity;
import com.myschool.entity.TeacherClassSemesterEntity;
import com.myschool.entity.base.TeacherClassSemesterId;

import jakarta.transaction.Transactional;

@Repository
public interface ITeacherClassSemesterRepository extends JpaRepository<TeacherClassSemesterEntity, TeacherClassSemesterId>{

	@Query("SELECT s FROM TeacherClassSemesterEntity s WHERE s.hocKy.id = :semesterId "
			+ "AND s.giaoVien.id = :teacherId "
			+ "AND s.lopHoc.id = :classId")
	List<TeacherClassSemesterEntity> findById(@Param("semesterId") Long semesterId, 
											  @Param("teacherId") Long teacherId, 
											  @Param("classId") Long classId);
	
	
	@Query("SELECT t, g, m FROM TeacherClassSemesterEntity t "
			+ "JOIN t.giaoVien g "
			+ "JOIN g.monHoc m "
			+ "ORDER BY m.tenMonHoc ASC")
	List<TeacherClassSemesterEntity> findAllSortedByTenMonHoc();
	
	@Query("SELECT s FROM TeacherClassSemesterEntity s WHERE s.hocKy.id = :semesterId "
			+ "AND s.lopHoc.id = :classId")
	List<TeacherClassSemesterEntity> findTeacherIdBySCId(@Param("semesterId") Long semesterId, 
												   @Param("classId") Long classId);
	
	@Transactional
	@Modifying
	@Query(value = "insert into GIAO_VIEN_DAY_LOP (giaovien_id, lophoc_id, hocky_id) "
			+ "values ( :giaoVienId, :lopHocId, :hocKyId)", 
			nativeQuery=true)
	void insertTCS (@Param("giaoVienId") Long giaoVienId, 
					@Param("lopHocId") Long lopHocId, 
					@Param("hocKyId") Long hocKyId);
	
	@Query("SELECT s.lopHoc FROM TeacherClassSemesterEntity s "
			+ "WHERE s.giaoVien.id =:teacherId "
			+ "AND s.hocKy.id = :semesterId")
	List<ClassEntity> getClassesByTeacherAndSemester(@Param("teacherId") Long teacherId,
			@Param("semesterId") Long semesterId);
	
	@Query("SELECT s.lopHoc FROM TeacherClassSemesterEntity s "
			+ "WHERE s.hocKy.id = :hocKyId")
	List<ClassEntity> getClassesBySemester(@Param("hocKyId") Long hocKyId);
	
	@Query("SELECT s FROM TeacherClassSemesterEntity s "
			+ "WHERE s.lopHoc.id = :lophocId")
	List<TeacherClassSemesterEntity> getTeacherByClass(@Param("lophocId") Long lophocId);

}
