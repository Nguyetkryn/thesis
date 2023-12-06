package com.myschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myschool.entity.ClassEntity;
import com.myschool.entity.ConductEntity;

@Repository
public interface IConductRepository extends JpaRepository<ConductEntity, Long> {


}
