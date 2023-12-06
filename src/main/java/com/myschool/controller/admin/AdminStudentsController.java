package com.myschool.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myschool.controller.common.BaseController;
import com.myschool.entity.HomeroomTeacherEntity;
import com.myschool.entity.SchoolYearEntity;
import com.myschool.entity.SemesterEntity;
import com.myschool.entity.StudentEntity;
import com.myschool.entity.TeacherClassSemesterEntity;
import com.myschool.entity.TeacherEntity;
import com.myschool.service.IHomeroomTeacherService;
import com.myschool.service.ISchoolYearService;
import com.myschool.service.ISemesterService;
import com.myschool.service.IStudentService;
import com.myschool.service.ITeacherClassSemesterService;
import com.myschool.service.ITeacherService;

@Controller
@RequestMapping("/admin/home/students")
public class AdminStudentsController extends BaseController{

	@Autowired
	private ISchoolYearService schoolYearService;
	
	@Autowired
	private ISemesterService semesterService;
	
	@Autowired
	private IStudentService studentService;

	@Autowired
	private ITeacherService teacherService;
	
	@Autowired
	private ITeacherClassSemesterService teacherClassSemesterService;
	
	@Autowired
	private IHomeroomTeacherService homeroomTeacherService;
	
	@GetMapping
	public String showLearningOutcomes(Model model) {
	
		List<SemesterEntity> semesters = semesterService.getAllSemesters();
		List<SchoolYearEntity> schoolYears = schoolYearService.getAllSchoolYears();
		List<StudentEntity> studentEntities = studentService.findAllStudents();
		List<TeacherEntity> teacherEntities = teacherService.findAllTeacher();
		List<TeacherClassSemesterEntity> tcsEntities = teacherClassSemesterService.findTCSAll();
		List<HomeroomTeacherEntity> hTeacherEntities = homeroomTeacherService.findAllStHTeacher();
		

		model.addAttribute("schoolYears", schoolYears);
		model.addAttribute("semesters", semesters);
		model.addAttribute("students", studentEntities); //không phải gvcn thì ko được nhập điểm đâu
		model.addAttribute("teachers", teacherEntities); //không phải gvcn ko thể xem được danh sách điểm hs
		model.addAttribute("tcsEntities", tcsEntities);
		model.addAttribute("hTeacherEntities", hTeacherEntities);
		
		return "bangiamhieu/class-manager";
	}
}
