package com.myschool.controller.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myschool.controller.common.BaseController;
import com.myschool.entity.ClassEntity;
import com.myschool.entity.GradeEntity;
import com.myschool.entity.SchoolYearEntity;
import com.myschool.entity.StudentEntity;
import com.myschool.entity.TeacherClassSemesterEntity;
import com.myschool.entity.UserEntity;
import com.myschool.service.IClassService;
import com.myschool.service.IGradeService;
import com.myschool.service.IHomeroomTeacherService;
import com.myschool.service.ISchoolYearService;
import com.myschool.service.IScoreService;
import com.myschool.service.ISemesterService;
import com.myschool.service.IStudentService;
import com.myschool.service.ITeacherClassSemesterService;
import com.myschool.service.IUserService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Controller
@AllArgsConstructor
@Setter
@Getter
public class RegisterController extends BaseController {

	private final IUserService userService;
	private IGradeService gradeService;
	private IClassService classService;
	private ISchoolYearService yearService;
	private IStudentService studentService;
	private IHomeroomTeacherService homeroomTeacherService;
	private IScoreService scoreService;
	private ISemesterService semesterService;
	private ITeacherClassSemesterService teacherClassSemesterService;

	@GetMapping("/admin/account/student/save")
	private String showRegistrationForm(Model model) {

//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication != null && authentication.getPrincipal() instanceof UserEntity) {
//			return "redirect:/admin/home";
//		}
		
		List<GradeEntity> grades = gradeService.findAllGrades();
		List<ClassEntity> classEntities = classService.getAllClass();
		List<SchoolYearEntity> yearEntities = yearService.getAllSchoolYears();
		
		model.addAttribute("classEntities", classEntities);
		model.addAttribute("grades", grades);
		model.addAttribute("yearEntities", yearEntities);
		
		model.addAttribute("user", new UserEntity());
		return "bangiamhieu/add-student";
	}

	@PostMapping("/admin/account/student/save")
	private String registerUser(@ModelAttribute("user") UserEntity user,
								@RequestParam("tenPhuHuynh") String tenPhuHuynh,
								@RequestParam("sdtPhuHuynh") String sdtPhuHuynh,
								@RequestParam("lophoc") Long lophocId,
								@RequestParam("nienkhoa") Long nienkhoaId,
								Model model,
								RedirectAttributes redirectAttributes) {
		
		Long hanhkiemId = (long)4;
		
		if (userService.registerUser(user)) {
			model.addAttribute("user", new UserEntity());
			
			StudentEntity studentEntity = user.getOrCreateStudent();
			studentEntity.setTenPhuHuynh(tenPhuHuynh);
			studentEntity.setSdtPhuHuynh(sdtPhuHuynh);
			studentEntity.setNguoiDung(new HashSet<>());
			studentService.save(studentEntity);
			
			/* Create Scores and HomeroomTeacher */
			List<Long> semesterIds =  semesterService.getSemesterIdBySchoolYearId(nienkhoaId);
			Long studentId = studentEntity.getId();
			for (Long semesterId : semesterIds) {
				//Create Scores
				scoreService.createScores(semesterId, studentId, lophocId);
				
				List<TeacherClassSemesterEntity> teacherClassSemesterEntities = teacherClassSemesterService.getTeacherIdBySCId(semesterId, lophocId);
				for (TeacherClassSemesterEntity teacherClassSemesterEntity : teacherClassSemesterEntities) {
					if (teacherClassSemesterEntity.getGiaoVien().getGvcn() == true) {
						Long teacherId = teacherClassSemesterEntity.getGiaoVien().getId();
						//Create HomeroomTeacher
						homeroomTeacherService.createHomeroomTeacher(semesterId, lophocId, studentId, teacherId, hanhkiemId, nienkhoaId);
					}
				}
			}
			return "redirect:/admin/home";
		} else {
			//model.addAttribute("error", true);
			redirectAttributes.addFlashAttribute("message", "Có vẻ như username hoặc email này đã được dùng trước đó, vui lòng thử lại!");
			return "redirect:/admin/account/student/save";
		}
		
	}
}
