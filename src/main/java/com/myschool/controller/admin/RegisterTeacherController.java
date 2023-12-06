package com.myschool.controller.admin;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myschool.controller.common.BaseController;
import com.myschool.entity.ClassEntity;
import com.myschool.entity.SchoolYearEntity;
import com.myschool.entity.SubjectEntity;
import com.myschool.entity.TeacherEntity;
import com.myschool.entity.UserEntity;
import com.myschool.service.IClassService;
import com.myschool.service.ISchoolYearService;
import com.myschool.service.ISemesterService;
import com.myschool.service.ISubjectService;
import com.myschool.service.ITeacherClassSemesterService;
import com.myschool.service.ITeacherService;
import com.myschool.service.IUserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/account/teacher/save")
public class RegisterTeacherController extends BaseController {

	private final IUserService userService;
	private IClassService classService;
	private ISubjectService subjectService;
	private ISchoolYearService yearService;
	private ITeacherService teacherService;
	private ITeacherClassSemesterService tcsService;
	private ISemesterService semesterService;

	@GetMapping
	private String showRegistrationForm(Model model) {

//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication != null && authentication.getPrincipal() instanceof UserEntity) {
//			return "redirect:/admin/home";
//		}
		
		List<ClassEntity> classEntities = classService.getAllClass();
		List<SubjectEntity> subjectEntities = subjectService.getAllSubject();
		List<SchoolYearEntity> yearEntities = yearService.getAllSchoolYears();
		
		model.addAttribute("classEntities", classEntities);
		model.addAttribute("subjectEntities", subjectEntities);
		model.addAttribute("yearEntities", yearEntities);
		
		model.addAttribute("user", new UserEntity());
		return "bangiamhieu/add-teacher";
	}

	@PostMapping
	private String registerTeacherUser(@ModelAttribute("user") UserEntity user,
										@RequestParam("trinhDo") String trinhDo,
										@RequestParam("thamNien") String thamNien,
										@RequestParam("lopHocId") Long lopHocId,
										@RequestParam("monHocId") Long monHocId,
										@RequestParam("nienKhoaId") Long nienKhoaId,
										@RequestParam(name = "gvcn", defaultValue = "false") Boolean gvcn ,
										Model model,
										RedirectAttributes redirectAttributes) {
		if (userService.registerTeacherUser(user)) {
			model.addAttribute("user", new UserEntity());
			
			//thêm vào bảng người dùng giáo viên
			TeacherEntity teacherEntity = user.getOrCreateTeacher();
			teacherEntity.setTrinhDo(trinhDo);
			teacherEntity.setThamNien(thamNien);
			teacherEntity.setGvcn(gvcn);
			teacherEntity.setNguoiDung(new HashSet<>());
			teacherService.save(teacherEntity);
			
			//thêm môn giảng dạy (dựa vào gvcn)
			Long teacherId = teacherEntity.getId();
			if (teacherEntity.getGvcn() == false) {
				teacherService.createTeacher(teacherId, monHocId);
			} else {
				if (lopHocId < 3) {
					teacherService.createTeacher(teacherId, (long)0);
				} else {
					teacherService.createTeacher(teacherId, (long)999);
				}
			}
			
			//thêm giáo viên dạy lớp
			List<Long> semesterIds =  semesterService.getSemesterIdBySchoolYearId(nienKhoaId);
			for (Long semesterId : semesterIds) {
				tcsService.createTCS(teacherId, lopHocId, semesterId);
			}
			return "redirect:/admin/home";
		} else {
			redirectAttributes.addFlashAttribute("message", "Có vẻ như username hoặc email này đã được dùng trước đó, vui lòng thử lại!");
			return "redirect:/admin/account/student/save";
		}
	}
}
