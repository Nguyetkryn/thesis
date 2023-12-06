package com.myschool.controller.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myschool.entity.UserEntity;
import com.myschool.service.IScoreService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/logout")
public class LogoutController extends BaseController {
	
	@GetMapping
	public String getLogOutPage() {
		return "login";
	}

}
