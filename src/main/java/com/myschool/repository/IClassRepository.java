package com.myschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myschool.entity.ClassEntity;

@Repository
public interface IClassRepository extends JpaRepository<ClassEntity, Long> {


}
