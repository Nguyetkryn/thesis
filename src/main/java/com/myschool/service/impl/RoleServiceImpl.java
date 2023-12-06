package com.myschool.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myschool.entity.RoleEntity;
import com.myschool.repository.IRoleRepository;
import com.myschool.service.IRoleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements IRoleService {

	private final IRoleRepository roleRepository;

	@Override
	public RoleEntity createRole(RoleEntity role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	@Override
	public RoleEntity getRoleById(Long roleId) {
		// TODO Auto-generated method stub
		return roleRepository.findById(roleId).orElse(null);
	}

	@Override
	public List<RoleEntity> getAllRoles() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	@Override
	public void deleteRole(Long roleId) {
		// TODO Auto-generated method stub
		roleRepository.deleteById(roleId);

	}

}
