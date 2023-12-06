package com.myschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myschool.entity.SchoolYearEntity;

public interface ISchoolYearRepository extends JpaRepository<SchoolYearEntity, Long> {
	
	List<SchoolYearEntity> findAll();

}
