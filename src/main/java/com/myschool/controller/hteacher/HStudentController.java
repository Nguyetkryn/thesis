package com.myschool.controller.hteacher;

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
import com.myschool.entity.HomeroomTeacherEntity;
import com.myschool.entity.SchoolYearEntity;
import com.myschool.entity.ScoreEntity;
import com.myschool.entity.SemesterEntity;
import com.myschool.entity.StudentEntity;
import com.myschool.entity.SubjectEntity;
import com.myschool.entity.TeacherClassSemesterEntity;
import com.myschool.entity.TeacherEntity;
import com.myschool.service.IClassService;
import com.myschool.service.IHomeroomTeacherService;
import com.myschool.service.ISchoolYearService;
import com.myschool.service.ISemesterService;
import com.myschool.service.IStudentService;
import com.myschool.service.ITeacherClassSemesterService;
import com.myschool.service.ITeacherService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/hteacher/home/students")
public class HStudentController extends BaseController {
	
	private ISchoolYearService schoolYearService;
	private ISemesterService semesterService;
	private IStudentService studentService;
	private ITeacherService teacherService;
	private ITeacherClassSemesterService teacherClassSemesterService;
	private IHomeroomTeacherService homeroomTeacherService;
	
	@GetMapping
	public String showLearningOutcomes(Model model, 
			@RequestParam(value = "nienkhoaId", required = false) Long nienkhoaId,
			@RequestParam(value = "hockyId", required = false) Long hockyId) {
		
		TeacherEntity teacherEntity = getCurrentUser(model).getOrCreateTeacher();
		Long giaovienId = teacherEntity.getId();
		
		List<SchoolYearEntity> schoolYears = schoolYearService.getAllSchoolYears();
		
		List<SemesterEntity> semesters = new ArrayList<>();
		if (nienkhoaId==null && schoolYears.size()>0)
			semesters = semesterService.findSemesterEntitiesBySchoolYearId(schoolYears.get(0).getId());
		else semesters = semesterService.findSemesterEntitiesBySchoolYearId(nienkhoaId);
		
		//Bảng điểm
		List<HomeroomTeacherEntity> students = new ArrayList<>();
		if (hockyId==null && nienkhoaId==null && semesters.size() >0) {
			students = homeroomTeacherService.getStudentByTeacherAndSemester(giaovienId, semesters.get(0).getId(), schoolYears.get(0).getId());
		}
		else {
			students = homeroomTeacherService.getStudentByTeacherAndSemester(giaovienId, hockyId, nienkhoaId);
		}

		model.addAttribute("schoolYears", schoolYears);
		model.addAttribute("semesters", semesters);
		model.addAttribute("hocKyId", hockyId);
		model.addAttribute("nienKhoaId", nienkhoaId);
		model.addAttribute("students", students);
		
		return "giaoviencn/students-information";
	}
}
