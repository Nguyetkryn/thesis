package com.myschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myschool.entity.RoleEntity;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

	RoleEntity findByTenQuyen(String tenQuyen);

}
