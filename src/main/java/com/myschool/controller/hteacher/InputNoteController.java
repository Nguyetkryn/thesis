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
public class InputNoteController extends BaseController {
	

	private IScoreService scoreService;
	private IStudentService studentService;
	private ISemesterService semesterService;
	private ISubjectService subjectService;
	private ISchoolYearService yearService;
	private IConductService conductService;
	private IHomeroomTeacherService homeroomTeacherService;
	private IClassService classService;

	@GetMapping("/hteacher/note/edit")
	private String showConductForm(Model model,
			@RequestParam(value = "hocsinhId", required = false) Long hocsinhId) {
		
		List<StudentEntity> studentEntities = studentService.findAllStudents();
		model.addAttribute("studentEntities", studentEntities);
		
		StudentEntity student = studentService.getStudentById(hocsinhId);
		model.addAttribute("student", student);
		
		StudentEntity studentEntity = studentService.getStudentById(hocsinhId);
		if (studentEntity != null) {
			model.addAttribute("studentEntity", studentEntity);
		}
		
		return "giaoviencn/enter-note";
	}
	
	@PostMapping("/hteacher/note/edit")
	public String updateNote(Model model,
							  @RequestParam("hocSinhId") Long hocSinhId,
							  @RequestParam("ghiChu") String ghiChu,
							  RedirectAttributes redirectAttributes) {
		
		StudentEntity studentEntity = studentService.getStudentById(hocSinhId);
	    
	    if (studentEntity!=null) {
	        //cập nhật ghi chú
	    	studentService.updateNote(hocSinhId, ghiChu);
	    }
	    
	    redirectAttributes.addFlashAttribute("message", "Cập nhật thành công!");
	    return "redirect:/hteacher/home/students";
	}
}
