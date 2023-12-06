package com.myschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myschool.entity.SemesterEntity;

public interface ISemesterRepository extends JpaRepository<SemesterEntity, Long> {

	List<SemesterEntity> findByNienKhoaId(Long schoolYearid);

}
