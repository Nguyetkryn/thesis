package com.myschool.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myschool.controller.common.BaseController;
import com.myschool.entity.HomeroomTeacherEntity;
import com.myschool.entity.SchoolYearEntity;
import com.myschool.entity.ScoreEntity;
import com.myschool.entity.SemesterEntity;
import com.myschool.entity.StudentEntity;
import com.myschool.entity.SubjectEntity;
import com.myschool.service.IHomeroomTeacherService;
import com.myschool.service.ISchoolYearService;
import com.myschool.service.IScoreService;
import com.myschool.service.ISemesterService;
import com.myschool.service.IStudentService;
import com.myschool.service.ISubjectService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController extends BaseController {

	private final ISchoolYearService schoolYearService;
	private final ISemesterService semesterService;
	private IStudentService studentService;
	private IScoreService scoreService;
	private IHomeroomTeacherService homeroomTeacherService;
	private ISubjectService subjectService;

	@GetMapping("/home")
	public String showScores(Model model,
							@RequestParam(value = "nienKhoaId", required = false) Long nienKhoaId,
							@RequestParam(value = "hocKyId", required = false) Long hocKyId) {
		// Lấy id học sinh
		StudentEntity studentEntity = getCurrentUser(model).getOrCreateStudent();
		Long studentId = studentEntity.getId();
		
		List<SchoolYearEntity> schoolYears = schoolYearService.getAllSchoolYears();
		
		List<StudentEntity> studentEntities = studentService.findAllStudents();
		
		List<SubjectEntity> subjects = subjectService.getAllSubject();
		
		List<ScoreEntity> scoreEntities = new ArrayList<>();
		
		List<SemesterEntity> semesters;
		
		Double diemTrungBinhHKI = 0d, diemTrungBinhHKII = 0d, diemCaNam = 0d;
		String xepLoai = "", xepLoaiHKI = "", xepLoaiHKII = "";
		Long hanhKiemHKI = (long)0, hanhKiemHKII = (long)0; 
		String hanhKiemCaNam = "";
		
		
		
		
		if (nienKhoaId==null && schoolYears.size()>0) {
			semesters = semesterService.findSemesterEntitiesBySchoolYearId(schoolYears.get(0).getId());
			for (SemesterEntity semesterEntity : semesters) {
				Long semesterId = semesterEntity.getId();
				if (semesterEntity.getTenHk().equals("Học Kỳ I")) {
					diemTrungBinhHKI = scoreService.semesterGPA(semesterId, studentId);
					hanhKiemHKI = homeroomTeacherService.getConductId(semesterId, studentId);
					
				} else if (semesterEntity.getTenHk().equals("Học Kỳ II")) {
					diemTrungBinhHKII = scoreService.semesterGPA(semesterId, studentId) * 2;
					hanhKiemHKII = homeroomTeacherService.getConductId(semesterId, studentId);
				}
				
				hanhKiemCaNam = homeroomTeacherService.conductAllYear(hanhKiemHKI, hanhKiemHKII);
				diemCaNam = (diemTrungBinhHKI + diemTrungBinhHKII)/3;
				xepLoai = scoreService.classification(semesterId, studentId, hanhKiemCaNam);
			}
		}	
		else semesters = semesterService.findSemesterEntitiesBySchoolYearId(nienKhoaId);
		
		System.out.println(semesters.size());
		
		if (hocKyId!=null)
			scoreEntities = scoreService.getScoreBySemesterStudentId(hocKyId, studentId);
		else scoreEntities = scoreService.getScoreBySemesterStudentId(semesters.get(0).getId(), studentId);
		
		if (hocKyId!=null) {
			for (SemesterEntity semesterEntity : semesters) {
				Long semesterId = semesterEntity.getId();
				if (semesterEntity.getTenHk().equals("Học Kỳ I")) {
					diemTrungBinhHKI = scoreService.semesterGPA(semesterId, studentId);
					hanhKiemHKI = homeroomTeacherService.getConductId(semesterId, studentId);
					
				} else if (semesterEntity.getTenHk().equals("Học Kỳ II")) {
					diemTrungBinhHKII = scoreService.semesterGPA(semesterId, studentId) * 2;
					hanhKiemHKII = homeroomTeacherService.getConductId(semesterId, studentId);
				}
				
				hanhKiemCaNam = homeroomTeacherService.conductAllYear(hanhKiemHKI, hanhKiemHKII);
				diemCaNam = (diemTrungBinhHKI + diemTrungBinhHKII)/3;
				xepLoai = scoreService.classification(semesterId, studentId, hanhKiemCaNam);
			}
		}
		
		
		// Làm tròn giá trị đến 2 chữ số thập phân
		DecimalFormat df = new DecimalFormat("#.##");
		diemCaNam = Double.parseDouble(df.format(diemCaNam));
		diemTrungBinhHKI = Double.parseDouble(df.format(diemTrungBinhHKI));
		diemTrungBinhHKII = Double.parseDouble(df.format(diemTrungBinhHKII/2));
		
		model.addAttribute("schoolYears", schoolYears);
		model.addAttribute("semesters", semesters);
		model.addAttribute("students", studentEntities);
		model.addAttribute("scoreEntities", scoreEntities);
		model.addAttribute("diemTrungBinhHKI", diemTrungBinhHKI);
		model.addAttribute("diemTrungBinhHKII", diemTrungBinhHKII);
		model.addAttribute("hanhKiem", hanhKiemCaNam);
		model.addAttribute("xepLoai", xepLoai);
		model.addAttribute("diemCaNam", diemCaNam);
		model.addAttribute("nienKhoaId", nienKhoaId);
		model.addAttribute("hocKyId", hocKyId);
			    
		return "hocsinh/student-details";
	}
}
