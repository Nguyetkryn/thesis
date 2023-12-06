package com.myschool.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myschool.entity.StudentEntity;
import com.myschool.entity.UserEntity;
import com.myschool.service.IStudentService;
import com.myschool.service.IUserService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Controller
@AllArgsConstructor
@Setter
@Getter
public class AdEditPassController {

	private IUserService userService;
	private IStudentService studentService;
	
	@GetMapping("/admin/home/account/edit")
	public String showAccountForm(Model model,
								@RequestParam("userId") Long userId,
								@RequestParam("userName") String userName,
								@RequestParam("userPasswd") String userPasswd,
								@RequestParam("email") String email,
								@RequestParam("sdt") String sdt,
								@RequestParam("diaChi") String diaChi) {
		
		List<UserEntity> userEntities = userService.findAllUsers();
		model.addAttribute("userEntities", userEntities);
		
		UserEntity user = userService.findById(userId);
		model.addAttribute("user", user);
		
		UserEntity userEntity = userService.findById(userId);
		if (userEntity!=null) {
			model.addAttribute("userEntity", userEntity);
		}
		
		return "bangiamhieu/account-edit";
	}
	
	@PostMapping("/admin/home/account/edit")
	public String updateAccount(@RequestParam(value ="userId", required = false ) Long userId,
								@RequestParam(name ="username", required = false) String userName,
								@RequestParam(name ="password", required = false) String userPassword,
								@RequestParam(name ="email", required = false) String email,
								@RequestParam(name ="sdt", required = false) String sdt,
								@RequestParam(name ="diaChi", required = false) String diaChi) {
		
		UserEntity userEntity = userService.findById(userId);
		
		boolean hasRoleHocsinh = false;
		if (userEntity.getPHAN_QUYEN() != null) {
			hasRoleHocsinh = userEntity.getPHAN_QUYEN().stream()
			        .anyMatch(role -> role.getTenQuyen().equals("ROLE_hocsinh"));
		}
		if (userEntity!=null) {
			if (hasRoleHocsinh) {
				StudentEntity studentEntity = userEntity.getHocSinh().stream().findFirst().orElse(null);
				Long studentId = studentEntity.getId();
				//studentEntity.setSdtPhuHuynh(sdt);
				studentService.updatePhone(studentId, sdt);
				userService.updateStudent(userId, userName, email, diaChi);
			}
			else {//cập nhật thông tin mới
				userService.updateGeneral(userId, userName, email, sdt, diaChi);
			}
	    }
		return "redirect:/admin/home";
	}
	
	@PostMapping("/admin/home/password/edit")
	public String updatePassword(@RequestParam(value ="userId", required = false ) Long userId,
								@RequestParam(name ="password", required = false) String userPassword) {
		
		UserEntity userEntity = userService.findById(userId);

		if (userEntity!=null) {//cập nhật thông tin mới
				userService.updatePassword(userId, userPassword);
	    }
		return "redirect:/admin/home";
	}
}
