package com.myschool.controller.teacher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myschool.controller.common.BaseController;
import com.myschool.entity.ClassEntity;
import com.myschool.entity.SchoolYearEntity;
import com.myschool.entity.ScoreEntity;
import com.myschool.entity.SemesterEntity;
import com.myschool.entity.SubjectEntity;
import com.myschool.entity.TeacherClassSemesterEntity;
import com.myschool.entity.TeacherEntity;
import com.myschool.service.IHomeroomTeacherService;
import com.myschool.service.ISchoolYearService;
import com.myschool.service.IScoreService;
import com.myschool.service.ISemesterService;
import com.myschool.service.ITeacherClassSemesterService;
import com.myschool.service.ITeacherService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/teacher/home")
public class TeacherController extends BaseController {
	private ISchoolYearService schoolYearService;
	private ISemesterService semesterService;
	private IScoreService scoreService;
	private ITeacherService teacherService;
	private ITeacherClassSemesterService teacherClassSemesterService;
	private IHomeroomTeacherService homeroomTeacherService;
	
	@GetMapping
	public String showLearningOutcomes(Model model,
			@RequestParam(value = "nienkhoaId", required = false) Long yearId,
			@RequestParam(value = "hockyId", required = false) Long semesterId,
			@RequestParam(value = "lophocId", required = false) Long classId,
			@RequestParam(value = "monhocId", required = false) Long subjectId) {
		
		//Long teacherId = getCurrentUser(model).getGiaoVien().getId();
		Long teacherId = getCurrentUser(model).getOrCreateTeacher().getId();
		
		List<SemesterEntity> semesters = new ArrayList<>();
		List<ClassEntity> classes = new ArrayList<>();
		List<ScoreEntity> scoreEntities = scoreService.getScoreBySemesterId(semesterId);
		
		//hiển thị select
		List<SchoolYearEntity> schoolYears = schoolYearService.getAllSchoolYears();
		if (yearId==null && schoolYears.size()>0)
			semesters = semesterService.findSemesterEntitiesBySchoolYearId(schoolYears.get(0).getId());
		else semesters = semesterService.findSemesterEntitiesBySchoolYearId(yearId);
		
		if (semesterId!=null && yearId!=null && semesters.size()>0)
			classes = teacherClassSemesterService.getClassesByTeacherAndSemester(teacherId, semesterId);
		else classes = teacherClassSemesterService.getClassesByTeacherAndSemester(teacherId, semesters.get(0).getId());
		
		List<SubjectEntity> subjects = teacherService.getSubjectByTeacher(teacherId);
		
		//hiển thị bảng điểm
		if (semesterId==null && subjectId==null && yearId==null)
			scoreEntities = scoreService.getScoreBySemesterAndSubject(semesters.get(0).getId(), subjects.get(0).getId());
		else scoreEntities = scoreService.getScoreBySemesterAndSubject(semesterId, subjectId); 
		
		
		List<TeacherEntity> teacherEntities = teacherService.findAllTeacher();
		
		
		model.addAttribute("schoolYears", schoolYears);
		model.addAttribute("semesters", semesters);
		model.addAttribute("classes", classes);
		model.addAttribute("subjects", subjects);
		model.addAttribute("scoreEntities", scoreEntities);
		model.addAttribute("teachers", teacherEntities);
		model.addAttribute("yearId", yearId);
		model.addAttribute("semesterId", semesterId);
		model.addAttribute("subjectId", subjectId);
		model.addAttribute("classId", classId);
		
		return "giaovien/learning-outcomes";
	}
}
