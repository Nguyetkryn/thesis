package com.myschool.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.myschool.entity.TeacherEntity;
import com.myschool.entity.UserEntity;
import com.myschool.exporter.HomeroomTeacherExcelExporter;
import com.myschool.service.IClassService;
import com.myschool.service.IHomeroomTeacherService;
import com.myschool.service.ISchoolYearService;
import com.myschool.service.IScoreService;
import com.myschool.service.ISemesterService;
import com.myschool.service.IStudentService;
import com.myschool.service.ISubjectService;
import com.myschool.service.ITeacherService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/dashboard")
public class AdminChartController extends BaseController {

	private ISchoolYearService schoolYearService;
	private ISemesterService semesterService;
	private IHomeroomTeacherService homeroomTeacherService;
	private IScoreService scoreService;
	private ISubjectService subjectService;
	private IClassService classService;
	
	private ITeacherService teacherService;
	private IStudentService studentService;

	@GetMapping()
	public String showLearningOutcomes(Model model, 
			@RequestParam(value = "nienkhoaId", required = false) Long nienkhoaId,
			@RequestParam(value = "hockyId", required = false) Long hockyId){
		
		//Khai bao
		List<SemesterEntity> semesters = new ArrayList<>();
		List<HomeroomTeacherEntity> students;
		int numberOfAllTeachers = 0, numberOfHomeroomteachers = 0, numberOfTeachers = 0;
		int numberOfStudents;
		int hanhKiemTot = 0, hanhKiemDat = 0, hanhKiemChuaDat = 0, xuatsac=0, tot=0, hoanthanh=0, chuahoanthanh=0;
		int[] count;
		
		//Select
		List<SchoolYearEntity> schoolYears = schoolYearService.getAllSchoolYears();	
		
		if (nienkhoaId==null && schoolYears.size()>0)
			semesters = semesterService.findSemesterEntitiesBySchoolYearId(schoolYears.get(0).getId());
		else semesters = semesterService.findSemesterEntitiesBySchoolYearId(nienkhoaId);
		
		
		//Tổng số giáo viên
		List<TeacherEntity> teachers = teacherService.findAllTeacher();
		numberOfAllTeachers = teachers.size(); 
		for (TeacherEntity teacher : teachers) {
			if (teacher.getGvcn() == true)
				numberOfHomeroomteachers ++;//Tổng số giáo viên chủ nhiệm
			else numberOfTeachers++; //Tổng số giáo viên bộ môn
		}
		
		//Tổng số học sinh
		numberOfStudents = studentService.findAllStudents().size();
		
		//Danh sách học sinh theo năm
		if (nienkhoaId==null && schoolYears.size()>0) {
			students = homeroomTeacherService.getStudentByNienKhoaId(schoolYears.get(0).getId());
			List<Long> studentIds = new ArrayList<>();
			for (HomeroomTeacherEntity student : students) {
				 Long studentId = student.getHocSinh().getId(); // Thay vì sử dụng studentIds, sử dụng trực tiếp student
				 studentIds.add(studentId); // Thêm studentId vào danh sách studentIds
			}
			count = scoreService.countAacademicAbility(schoolYears.get(0).getId(), studentIds);
		}
		else {
			students = homeroomTeacherService.getStudentByNienKhoaId(nienkhoaId);
			List<Long> studentIds = new ArrayList<>();
			for (HomeroomTeacherEntity student : students) {
				 Long studentId = student.getHocSinh().getId(); // Thay vì sử dụng studentIds, sử dụng trực tiếp student
				 studentIds.add(studentId); // Thêm studentId vào danh sách studentIds
			}
			count = scoreService.countAacademicAbility(nienkhoaId, studentIds);
		}
		
		if(count!=null && count.length==4) {
			xuatsac = count[0]; //Tỉ lệ học sinh hoàn thành xuất sắc
			tot = count[1]; //Tỉ lệ học sinh hoàn thành tốt
			hoanthanh = count[2]; //Tỉ lệ học sinh hoàn thành
			chuahoanthanh = count[3]; //Tỉ lệ học sinh chưa hoàn thành
		}
		
		
		//Conduct
		int[] conductCounts = null;
		if (nienkhoaId==null && schoolYears.size()>0)
			conductCounts = homeroomTeacherService.countConductLevelsForSemesterAndYear(schoolYears.get(0).getId());
		else conductCounts = homeroomTeacherService.countConductLevelsForSemesterAndYear(nienkhoaId);
		
		if (conductCounts != null && conductCounts.length == 3) {
			hanhKiemTot = conductCounts[0];  //Tỉ lệ học sinh hạnh kiểm tốt
			hanhKiemDat = conductCounts[1];  //Tỉ lệ học sinh hạnh kiểm đạt
			hanhKiemChuaDat = conductCounts[2];  //Tỉ lệ học sinh hạnh kiểm chưa đạt
		}

		model.addAttribute("schoolYears", schoolYears);
		model.addAttribute("semesters", semesters);
		model.addAttribute("hocKyId", hockyId);
		model.addAttribute("nienKhoaId", nienkhoaId);
		
		model.addAttribute("numberOfAllTeachers", numberOfAllTeachers);
		model.addAttribute("numberOfHomeroomteachers", numberOfHomeroomteachers);
		model.addAttribute("numberOfTeachers", numberOfTeachers);
		model.addAttribute("numberOfStudents", numberOfStudents);
		model.addAttribute("hanhKiemTot", hanhKiemTot);
		model.addAttribute("hanhKiemDat", hanhKiemDat);
		model.addAttribute("hanhKiemChuaDat", hanhKiemChuaDat);
		
		model.addAttribute("xuatsac", xuatsac);
		model.addAttribute("tot", tot);
		model.addAttribute("hoanthanh", hoanthanh);
		model.addAttribute("chuahoanthanh", chuahoanthanh);
		
		return "bangiamhieu/dashboard";
	}

}
