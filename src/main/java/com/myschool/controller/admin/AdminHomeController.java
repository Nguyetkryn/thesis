package com.myschool.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myschool.controller.common.BaseController;
import com.myschool.entity.UserEntity;
import com.myschool.service.IUserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/home")
public class AdminHomeController extends BaseController {

	private IUserService userService;
	
	@GetMapping()
	public String showAccountInfor(Model model,
			@RequestParam(name = "search", required = false) String search) {
		List<UserEntity> users;
		if (search != null && !search.isEmpty()) {
			users = userService.findUsersByHoTenContaining(search);
		} else {
			users = userService.findAllUsers();
		}
		
		model.addAttribute("users", users);
		return "bangiamhieu/account-information";
	}
	
}
