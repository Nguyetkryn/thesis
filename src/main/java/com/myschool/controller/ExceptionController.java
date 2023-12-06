package com.myschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/error")
public class ExceptionController {
	
	@GetMapping
	public String getErrorPage() {
		return "fragments/error-404";
	}

}
