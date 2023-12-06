package com.myschool.service;

public interface IMailService {
	void sendMail(String to, String studentName, String subjectName, Integer test);
}
