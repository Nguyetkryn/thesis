package com.myschool.controller.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myschool.entity.UserEntity;

@Controller
@RequestMapping("/login")
public class LogInController extends BaseController {

	@GetMapping
	public String getLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserEntity) {
			return "redirect:/home";
		}
		return "login";
	}

}
