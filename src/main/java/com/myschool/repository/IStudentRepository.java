package com.myschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myschool.entity.StudentEntity;

import jakarta.transaction.Transactional;


public interface IStudentRepository extends JpaRepository<StudentEntity, Long> {
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE StudentEntity s SET s.ghiChu = :ghiChu "
			+ "WHERE s.id = :studentId ")
	int updateNote(@Param("ghiChu") String ghiChu, 
					@Param("studentId") Long studentId);
	
	
	@Query("SELECT u.email FROM UserEntity u JOIN u.hocSinh s "
			+ "WHERE s.id = :hocsinhId")
	String findEmailByStudentId(@Param("hocsinhId") Long hocsinhId);
	
	@Query("SELECT u.hoTen FROM UserEntity u JOIN u.hocSinh s "
			+ "WHERE s.id = :hocsinhId")
	String findNameByStudentId(@Param("hocsinhId") Long hocsinhId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE StudentEntity s SET s.sdtPhuHuynh = :sdtPhuHuynh "
			+ "WHERE s.id = :studentId ")
	int updatePhone(@Param("sdtPhuHuynh") String sdtPhuHuynh, 
					@Param("studentId") Long studentId);
}
