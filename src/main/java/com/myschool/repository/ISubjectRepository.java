package com.myschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myschool.entity.SubjectEntity;

public interface ISubjectRepository extends JpaRepository<SubjectEntity, Long> {

	List<SubjectEntity> findByOrderByTenMonHocAsc();
	
	@Query("SELECT s.tenMonHoc FROM SubjectEntity s WHERE s.id = :subjectId")
	String findSubjectNameById(Long subjectId);
	
//	@Query("SELECT s FROM SubjectEntity s ORDER BY s.tenMonHoc ASC")
//    List<SubjectEntity> findAllOrderedByName();
	
}
