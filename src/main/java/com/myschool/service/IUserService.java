package com.myschool.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.myschool.dto.UserDTO;
import com.myschool.entity.UserEntity;

public interface IUserService {

	void save(UserEntity user);

	UserEntity findByUsername(String username);

	// Nên để hàm này để check tài khoản
	boolean registerUser(UserEntity user);
	
	boolean registerTeacherUser(UserEntity user);

	List<UserEntity> findAllUsers();
	
	UserEntity findById(Long id);
	
	void updateGeneral(Long id, String userName, String email, String sdt, String diaChi);
	
	void updatePassword(Long id, String password);
	
	void updateStudent(Long id, String userName, String email, String diaChi);
	
	List<UserEntity> findUsersByHoTenContaining(String search);
}
