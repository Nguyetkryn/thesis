package com.myschool.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myschool.entity.SubjectEntity;
import com.myschool.entity.TeacherEntity;

import jakarta.transaction.Transactional;

public interface ITeacherRepository extends JpaRepository<TeacherEntity, Long> {
	
	@Transactional
	@Modifying
	@Query(value = "insert into mon_giang_day (giaovien_id, monhoc_id) "
			+ "values ( :giaovienId, :monhocId)", 
			nativeQuery=true)
	void insertTeacher (@Param("giaovienId") Long giaovienId,
						@Param("monhocId") Long monhocId);
	
	@Query("SELECT t.monHoc FROM TeacherEntity t WHERE t.id = :teacherId")
    List<SubjectEntity> findMonHocById(@Param("teacherId") Long teacherId);
	

}
