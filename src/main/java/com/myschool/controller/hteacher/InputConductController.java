package com.myschool.controller.hteacher;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myschool.controller.common.BaseController;
import com.myschool.entity.ClassEntity;
import com.myschool.entity.ConductEntity;
import com.myschool.entity.HomeroomTeacherEntity;
import com.myschool.entity.SchoolYearEntity;
import com.myschool.entity.SemesterEntity;
import com.myschool.entity.StudentEntity;
import com.myschool.service.IClassService;
import com.myschool.service.IConductService;
import com.myschool.service.IHomeroomTeacherService;
import com.myschool.service.ISchoolYearService;
import com.myschool.service.IScoreService;
import com.myschool.service.ISemesterService;
import com.myschool.service.IStudentService;
import com.myschool.service.ISubjectService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Controller
@AllArgsConstructor
@Setter
@Getter
public class InputConductController extends BaseController {
	

	private IScoreService scoreService;
	private IStudentService studentService;
	private ISemesterService semesterService;
	private ISubjectService subjectService;
	private ISchoolYearService yearService;
	private IConductService conductService;
	private IHomeroomTeacherService homeroomTeacherService;
	private IClassService classService;

	@GetMapping("/hteacher/conduct/edit")
	private String showConductForm(Model model, 
			@RequestParam(value = "hockyId", required = false) Long hockyId,
			@RequestParam(value = "hocsinhId", required = false) Long hocsinhId,
			@RequestParam(value = "nienkhoaId", required = false) Long nienkhoaId,
			@RequestParam(value = "lophocId", required = false) Long lophocId,
			@RequestParam(value = "hanhkiemId", required = false) Long hanhkiemId) {
		
		Long giaovienId = getCurrentUser(model).getOrCreateTeacher().getId();
		
		List<StudentEntity> studentEntities = studentService.findAllStudents();
		List<SchoolYearEntity> yearEntities = yearService.getAllSchoolYears();
		List<SemesterEntity> semesterEntities = semesterService.getAllSemesters();
		List<ClassEntity> classEntities = classService.getAllClass();
		List<ConductEntity> conductEntities = conductService.getAllConduct();
		
		model.addAttribute("studentEntities", studentEntities);
		model.addAttribute("yearEntities", yearEntities);
		model.addAttribute("semesterEntities", semesterEntities);
		model.addAttribute("classEntities", classEntities);
		model.addAttribute("conductEntities", conductEntities);
		
		StudentEntity student = studentService.getStudentById(hocsinhId);
		model.addAttribute("student", student);
		SchoolYearEntity year = yearService.getYearById(nienkhoaId);
		model.addAttribute("year", year);
		SemesterEntity semester = semesterService.getSemesterById(hockyId);
		model.addAttribute("semester", semester);
		ConductEntity conduct = conductService.getConductById(hanhkiemId);
		model.addAttribute("conduct", conduct);
		
		List<HomeroomTeacherEntity> hteachers = homeroomTeacherService.getHTeacherById(hockyId, lophocId, hocsinhId, giaovienId, nienkhoaId);
		if (hteachers != null && !hteachers.isEmpty()) {
			model.addAttribute("hteacher", hteachers.get(0));
		}
		
		return "giaoviencn/conduct-edit";
	}
	
	@PostMapping("/hteacher/conduct/edit")
	public String updateConduct(Model model,
							  @RequestParam(value = "hocsinhId", required = false) Long hocsinhId,
							  @RequestParam(value = "hockyId", required = false) Long hockyId,
							  @RequestParam(value = "lophocId", required = false) Long lophocId,
							  @RequestParam(value = "hanhkiemId", required = false) Long hanhkiemId,
							  RedirectAttributes redirectAttributes) {
		
		Long giaovienId = getCurrentUser(model).getOrCreateTeacher().getId();
		
		Long nienkhoaId = semesterService.getNienKhoaIdByHocKyId(hockyId);
		
	    HomeroomTeacherEntity existingHteacher = homeroomTeacherService.getConduct(hockyId, hocsinhId);
	    
	    if (existingHteacher!=null) {
	        homeroomTeacherService.updateConduct(hockyId, hocsinhId, hanhkiemId, giaovienId, lophocId);
	    }
	    return "redirect:/hteacher/home/students/scores?nienkhoaId=" + nienkhoaId + "&hockyId=" + hockyId + "&lophocId=" + lophocId;
	}
}
