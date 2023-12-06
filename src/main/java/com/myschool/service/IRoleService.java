package com.myschool.service;

import java.util.List;

import com.myschool.entity.RoleEntity;

public interface IRoleService {

	RoleEntity createRole(RoleEntity role);

	RoleEntity getRoleById(Long roleId);

	List<RoleEntity> getAllRoles();

	void deleteRole(Long roleId);

}
