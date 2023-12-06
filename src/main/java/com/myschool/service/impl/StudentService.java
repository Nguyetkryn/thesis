package com.myschool.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myschool.entity.StudentEntity;
import com.myschool.repository.IStudentRepository;
import com.myschool.service.IStudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentService implements IStudentService {
	
	private IStudentRepository studentRepository;

	@Override
	public List<StudentEntity> findAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public StudentEntity getStudentById(Long studentId) {
	    return studentRepository.findById(studentId).orElse(null);
	}

	@Override
	public void save(StudentEntity studentEntity) {
		studentRepository.save(studentEntity);
	}

	@Override
	public void updateNote(Long studentId, String note) {
		// TODO Auto-generated method stub
		studentRepository.updateNote(note, studentId);
	}

	@Override
	public String getEmailByStudentId(Long studentId) {
		// TODO Auto-generated method stub
		return studentRepository.findEmailByStudentId(studentId);
	}

	@Override
	public String getNameByStudentId(Long studentId) {
		// TODO Auto-generated method stub
		return studentRepository.findNameByStudentId(studentId);
	}

	@Override
	public StudentEntity updateStudent(StudentEntity student) {
		// TODO Auto-generated method stub
		return studentRepository.save(student);
	}
	public void updatePhone(Long studentId, String phone) {
		studentRepository.updatePhone(phone, studentId);
	}

}
