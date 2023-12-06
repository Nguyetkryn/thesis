package com.myschool.controller.hteacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HStudentScoreController extends BaseController {

	private ISchoolYearService schoolYearService;
	private ISemesterService semesterService;
	private IHomeroomTeacherService homeroomTeacherService;
	private IScoreService scoreService;
	private ISubjectService subjectService;
	private IClassService classService;

	@GetMapping("/hteacher/home/students/scores")
	public String showLearningOutcomes(Model model, 
			@RequestParam(value = "nienkhoaId", required = false) Long nienkhoaId,
			@RequestParam(value = "hockyId", required = false) Long hockyId,
			@RequestParam(value = "lophocId", required = false) Long lophocId){
		
		//Id giáo viên chủ nhiệm
		TeacherEntity teacherEntity = getCurrentUser(model).getOrCreateTeacher();
		Long giaovienId = teacherEntity.getId();
		
		List<SchoolYearEntity> schoolYears = schoolYearService.getAllSchoolYears();
		
		List<SemesterEntity> semesters = new ArrayList<>();
		if (nienkhoaId==null && schoolYears.size()>0)
			semesters = semesterService.findSemesterEntitiesBySchoolYearId(schoolYears.get(0).getId());
		else semesters = semesterService.findSemesterEntitiesBySchoolYearId(nienkhoaId);
		
		List<HomeroomTeacherEntity> homeroomTeacherAlls = homeroomTeacherService.findAllStHTeacher();
		List<ScoreEntity> scoreEntities = scoreService.getAllScores();
		List<SubjectEntity> subjectEntities = subjectService.getAllSubject();
		List<ClassEntity> classEntities = classService.getAllClass();
		
		//Bảng điểm
		int[] point = null;
		List<HomeroomTeacherEntity> students = new ArrayList<>();
		if (hockyId==null && nienkhoaId==null && semesters.size() >0) {
			students = homeroomTeacherService.getStudentByTeacherAndSemester(giaovienId, semesters.get(0).getId(), schoolYears.get(0).getId());
			
			List<Long> studentIds = new ArrayList<>();
			for (HomeroomTeacherEntity homeroomTeacherEntity : students) {
			    studentIds.add(homeroomTeacherEntity.getHocSinh().getId());
			}
			point = scoreService.getAverageScoresByStudentAndSemester(studentIds, semesters.get(0).getId());
		}
		else {
			students = homeroomTeacherService.getStudentByTeacherAndSemester(giaovienId, hockyId, nienkhoaId);
			
			List<Long> studentIds = new ArrayList<>();
			for (HomeroomTeacherEntity homeroomTeacherEntity : students) {
			    studentIds.add(homeroomTeacherEntity.getHocSinh().getId());
			}
			point = scoreService.getAverageScoresByStudentAndSemester(studentIds, hockyId);
		}
		
		
		
		
		//lay ten
		
		//Chart		
		String labels[] = {"0-1", "1-2", "2-3", "3-3", "4-5", "5-6", "6-7", "7-8", "8-9", "9-10"};
		
		
		model.addAttribute("schoolYears", schoolYears);
		model.addAttribute("semesters", semesters);
		model.addAttribute("hocKyId", hockyId);
		model.addAttribute("nienKhoaId", nienkhoaId);
		model.addAttribute("lophocId", lophocId);
		model.addAttribute("homeroomTeacherAlls", homeroomTeacherAlls);
		model.addAttribute("subjects", subjectEntities);
		model.addAttribute("classes", classEntities);
		model.addAttribute("scores", scoreEntities);
		model.addAttribute("labels", labels);
		model.addAttribute("students", students);
		model.addAttribute("point", point);
	
		

		return "giaoviencn/students-scores";
	}

//	@GetMapping("/hteacher/home/students/scores/excel")
//	public void exportToExcel(Model model,
//			@RequestParam(value ="hockyId", required = false) Long hockyId,
//			HttpServletResponse response, 
//			@AuthenticationPrincipal UserEntity currentUser) // Sử dụng @AuthenticationPrincipal để lấy người dùng hiện tại
//			throws IOException {
//
//		response.setContentType("application/vnd.ms-excel");
//		
//		String fileName = "score.xlsx";
//
//		String headerKey = "Content-Disposition";
//		String headerValue = "attachment; filename=" + fileName;
//
//		response.setHeader(headerKey, headerValue);
//		
//		TeacherEntity teacherEntities = getCurrentUser(model).getOrCreateTeacher();
//		Long giaoVienId = teacherEntities.getId();
//		
//		List<HomeroomTeacherEntity> homeroomTeacherEntities = homeroomTeacherService.getHTeacherById(giaoVienId, hockyId);
//		List<SubjectEntity> subjectEntities = subjectService.getAllSubject();
//		
//		HomeroomTeacherExcelExporter excelExporter = new HomeroomTeacherExcelExporter(homeroomTeacherEntities, subjectEntities);
//
//		excelExporter.export(response);
//	}
}
