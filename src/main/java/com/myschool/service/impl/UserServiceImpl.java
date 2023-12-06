package com.myschool.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myschool.entity.RoleEntity;
import com.myschool.entity.UserEntity;
import com.myschool.repository.IRoleRepository;
import com.myschool.repository.IUserRepository;
import com.myschool.service.IUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

	private final IUserRepository userRepository;
	private final IRoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	@Override
	public void save(UserEntity user) {
		// TODO Auto-generated method stub
		save(user);
	}

	@Override
	public UserEntity findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean registerUser(UserEntity user) {

		// Nếu user name đã tồn tại
		if (userRepository.findByUsername(user.getUsername()) != null) {
			return false;
		}

		RoleEntity userRoleEntity = roleRepository.findByTenQuyen("ROLE_hocsinh");
		if (userRoleEntity == null) {
			RoleEntity roleEntity = new RoleEntity();
			roleEntity.setTenQuyen("ROLE_hocsinh");
			roleRepository.save(roleEntity);
		}

		user.getPHAN_QUYEN().add(userRoleEntity);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);

		return true;
	}
	
	@Override
	public boolean registerTeacherUser(UserEntity user) {

		// Nếu user name đã tồn tại
		if (userRepository.findByUsername(user.getUsername()) != null) {
			return false;
		}

		RoleEntity userRoleEntity = roleRepository.findByTenQuyen("ROLE_giaovien");
		if (userRoleEntity == null) {
			RoleEntity roleEntity = new RoleEntity();
			roleEntity.setTenQuyen("ROLE_giaovien");
			roleRepository.save(roleEntity);
		}

		user.getPHAN_QUYEN().add(userRoleEntity);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);

		return true;
	}

	@Override
	public List<UserEntity> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity findById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public void updateGeneral(Long id, String userName, String email, String sdt, String diaChi) {
		// TODO Auto-generated method stub
		UserEntity user = userRepository.findById(id).orElse(null);
		if (user != null) {
			user.setUsername(userName);
			user.setEmail(email);
			user.setSdt(sdt);
			user.setDiaChi(diaChi);
			userRepository.save(user);
		}
	}

	@Override
	public List<UserEntity> findUsersByHoTenContaining(String search) {
		// TODO Auto-generated method stub
		return userRepository.findByHoTenContaining(search);
	}

	@Override
	public void updateStudent(Long id, String userName, String email, String diaChi) {
		// TODO Auto-generated method stub
		UserEntity user = userRepository.findById(id).orElse(null);
		if (user != null) {
			user.setUsername(userName);
			user.setEmail(email);
			user.setDiaChi(diaChi);
			userRepository.save(user);
		}
	}

	@Override
	public void updatePassword(Long id, String password) {
		// TODO Auto-generated method stub
		UserEntity user = userRepository.findById(id).orElse(null);
		if (user != null) {
			user.setPassword(passwordEncoder.encode(password));
			userRepository.save(user);
		}
	}

}
