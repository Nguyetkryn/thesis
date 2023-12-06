package com.myschool.controller.admin;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myschool.controller.common.BaseController;
import com.myschool.entity.HomeroomTeacherEntity;
import com.myschool.entity.SchoolYearEntity;
import com.myschool.entity.SemesterEntity;
import com.myschool.entity.TeacherEntity;
import com.myschool.repository.IHomeroomTeacherRepository;
import com.myschool.service.IHomeroomTeacherService;
import com.myschool.service.ISchoolYearService;
import com.myschool.service.ISemesterService;
import com.myschool.service.ITeacherService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/home/teachers")
public class AdminTeachersController extends BaseController {

	private ITeacherService teacherService;
	private ISchoolYearService schoolYearService;
	private ISemesterService semesterService;
	private IHomeroomTeacherService homeroomTeacherService;
	
	@GetMapping()
	public String showTeachers(Model model) {
		List<TeacherEntity> teachers = teacherService.findAllTeacher();
		List<SchoolYearEntity> schoolYearEntities = schoolYearService.getAllSchoolYears();
		List<SemesterEntity> semesterEntities = semesterService.getAllSemesters();
		List<HomeroomTeacherEntity> homeroomTeacherEntities = homeroomTeacherService.findAllStHTeacher();
		
		model.addAttribute("teachers", teachers);
		model.addAttribute("schoolYears", schoolYearEntities);
		model.addAttribute("semesters", semesterEntities);
		model.addAttribute("hTeacherEntities", homeroomTeacherEntities);
		return "bangiamhieu/teacher-information";
	}
	
}
