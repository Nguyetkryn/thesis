package com.myschool.controller.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.myschool.entity.UserEntity;

import lombok.AllArgsConstructor;

@Controller
public abstract class BaseController {
	
	
	// Lấy người dùng hiện tại, tất cả các Controller phải extend lại BaseController này
	@ModelAttribute("currentUser")
	public UserEntity getCurrentUser(Model model) {
		model.getAttribute("");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.getPrincipal() instanceof UserEntity) {
			return (UserEntity) authentication.getPrincipal();
		}
		return new UserEntity();
	}

}