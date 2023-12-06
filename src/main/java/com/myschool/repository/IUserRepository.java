package com.myschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myschool.entity.UserEntity;

import jakarta.transaction.Transactional;


public interface IUserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);
	
	List<UserEntity> findByHoTenContaining(String search);
	
//	@Transactional
//	@Modifying
//	@Query("UPDATE UserEntity u SET s.password = :password WHERE s.id = :id")
//	void updatePassword(@Param("password") String password,
//						@Param("id") Long id);
	
}
