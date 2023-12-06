package com.myschool.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.myschool.entity.UserEntity;
import com.myschool.service.IClassService;
import com.myschool.service.IHomeroomTeacherService;
import com.myschool.service.ISchoolYearService;
import com.myschool.service.IScoreService;
import com.myschool.service.ISemesterService;
import com.myschool.service.IStudentService;
import com.myschool.service.ISubjectService;
import com.myschool.service.ITeacherClassSemesterService;
import com.myschool.service.ITeacherService;
import com.myschool.service.IUserService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Controller
@AllArgsConstructor
@Setter
@Getter
public class AdEditStudentController extends BaseController {
	
	private IScoreService scoreService;
	private IStudentService studentService;
	private ISemesterService semesterService;
	private ISubjectService subjectService;
	private ISchoolYearService yearService;
	private IClassService classService;
	private ITeacherService teacherService;
	private IHomeroomTeacherService hTeacherService;
	private ITeacherClassSemesterService teacherClassSemesterService;
	private IUserService userService;

	@GetMapping("/admin/home/students/edit")
	private String showStudentForm(Model model, 
			@RequestParam(value = "hockyId", required = false) Long hockyId,
			@RequestParam(value = "nienkhoaId", required = false) Long nienkhoaId,
			@RequestParam(value = "lophocId", required = false) Long lophocId,
			@RequestParam(value = "hocsinhId", required = false) Long hocsinhId,
			@RequestParam(value = "giaovienId", required = false) Long giaovienId) {
		
		List<TeacherEntity> teacherEntities = teacherService.findAllTeacher();
		model.addAttribute("teacherEntities", teacherEntities);
		List<ClassEntity> classEntities = classService.getAllClass();
		model.addAttribute("classEntities", classEntities);
		List<SchoolYearEntity> schoolYearEntities = yearService.getAllSchoolYears();
		model.addAttribute("schoolYearEntities", schoolYearEntities);
		List<SemesterEntity> semesterEntities = semesterService.getAllSemesters();
		model.addAttribute("semesterEntities", semesterEntities);
		List<HomeroomTeacherEntity> homeroomTeacherEntities = hTeacherService.findAllStHTeacher();
		model.addAttribute("homeroomTeacherEntities", homeroomTeacherEntities);
		List<TeacherClassSemesterEntity> teacherClassSemesterEntities = teacherClassSemesterService.findTCSAll();
		model.addAttribute("teacherClassSemesterEntities", teacherClassSemesterEntities);
		
		List<SemesterEntity> semesters = new ArrayList<>();
		List<ClassEntity> classes = new ArrayList<>();
		
		List<SchoolYearEntity> schoolYears = yearService.getAllSchoolYears();
		if (nienkhoaId==null && schoolYears.size()>0)
			semesters = semesterService.findSemesterEntitiesBySchoolYearId(schoolYears.get(0).getId());
		else semesters = semesterService.findSemesterEntitiesBySchoolYearId(nienkhoaId);
		
		if (hockyId!=null && nienkhoaId!=null && semesters.size()>0)
			classes = teacherClassSemesterService.getClassesByTeacherAndSemester(giaovienId, hockyId);
		else classes = teacherClassSemesterService.getClassesByTeacherAndSemester(giaovienId, semesters.get(0).getId());
		
		
		SemesterEntity semester = semesterService.getSemesterById(hockyId);
		model.addAttribute("semester", semester);
		SchoolYearEntity year = yearService.getYearById(nienkhoaId);
		model.addAttribute("year", year);
		ClassEntity classEntity = classService.getClassById(lophocId);
		model.addAttribute("classEntity", classEntity);
		StudentEntity student = studentService.getStudentById(hocsinhId);
		model.addAttribute("student", student);
		TeacherEntity teacher = teacherService.getTeacherById(giaovienId);
		model.addAttribute("teacher", teacher);

		List<HomeroomTeacherEntity> hteachers = hTeacherService.getHTeacherById(hockyId, lophocId, hocsinhId, giaovienId, nienkhoaId);
		if (hteachers != null && !hteachers.isEmpty()) {
			model.addAttribute("hteacher", hteachers.get(0));
		}
		return "bangiamhieu/student-edit";
	}
	
	//Lên lớp, ở lại lớp của học sinh
	@PostMapping("/admin/home/students/edit")
	public String updateStudent(@RequestParam(value = "nienkhoaId", required = false) Long nienkhoaId,
							  @RequestParam(value = "lophocId", required = false) Long lophocId,
							  @RequestParam(value = "hocsinhId", required = false) Long hocsinhId,
							  @RequestParam(value = "giaovienId", required = false) Long giaovienId,
							  RedirectAttributes redirectAttributes) {
	    
		List<Long> semesterIds =  semesterService.getSemesterIdBySchoolYearId(nienkhoaId);
		for (Long semesterId : semesterIds) {
			List<HomeroomTeacherEntity> existingHteachers = hTeacherService.getHTeacherById(semesterId, lophocId, hocsinhId, giaovienId, nienkhoaId);
	    
			if (existingHteachers == null || existingHteachers.isEmpty()) {
		    	hTeacherService.createHomeroomTeacher(semesterId, lophocId, hocsinhId, giaovienId, (long)4, nienkhoaId);
		    	scoreService.createScores(semesterId, hocsinhId, lophocId);
		    }else {
		    	redirectAttributes.addFlashAttribute("message", "Đã tồn tại!");
		    }
		}	    
	    redirectAttributes.addFlashAttribute("message", "Cập nhật thành công!");
	    return "redirect:/admin/home/students";
	}
}
