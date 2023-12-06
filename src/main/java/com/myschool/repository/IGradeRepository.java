package com.myschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myschool.entity.GradeEntity;

public interface IGradeRepository extends JpaRepository<GradeEntity, Long> {


}
