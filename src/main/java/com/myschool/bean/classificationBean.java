package com.myschool.bean;

import org.springframework.stereotype.Component;

@Component
public class classificationBean {

	public String classificationStudent(Double diem) {
		if (diem >= 9 && diem <=10) {
			return "Gioi";
		}
		return "";
	}
}
