package com.myschool.controller.hteacher;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myschool.controller.common.BaseController;
import com.myschool.entity.ScoreEntity;
import com.myschool.entity.SemesterEntity;
import com.myschool.entity.StudentEntity;
import com.myschool.entity.SubjectEntity;
import com.myschool.service.IHomeroomTeacherService;
import com.myschool.service.IMailService;
import com.myschool.service.IScoreService;
import com.myschool.service.ISemesterService;
import com.myschool.service.IStudentService;
import com.myschool.service.ISubjectService;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Controller
@AllArgsConstructor
@Setter
@Getter
public class InputScoreController extends BaseController {
	

	private IScoreService scoreService;
	private IStudentService studentService;
	private ISemesterService semesterService;
	private ISubjectService subjectService;
	private IMailService mailService;
	private IHomeroomTeacherService homeroomTeacherService;

	@GetMapping("/hteacher/score")
	private String showScoreForm(Model model, 
			@RequestParam(value = "hockyId", required = false) Long hockyId,
			@RequestParam(value = "monhocId", required = false) Long monhocId,
			@RequestParam(value = "hocsinhId", required = false) Long hocsinhId) {
		
		StudentEntity student = studentService.getStudentById(hocsinhId);
		model.addAttribute("student", student);
		SubjectEntity subject = subjectService.getSubjectById(monhocId);
		model.addAttribute("subject", subject);
		SemesterEntity semester = semesterService.getSemesterById(hockyId);
		model.addAttribute("semester", semester);

		List<ScoreEntity> scores = scoreService.getScoreById(hockyId, monhocId, hocsinhId);
		if (scores != null && !scores.isEmpty()) {
			model.addAttribute("score", scores.get(0));
		}
		return "giaoviencn/enter-score";
	}
	
	@PostMapping("/hteacher/score/edit")
	public String updateScore(@RequestParam("hocSinhId") Long hocSinhId,
							  @RequestParam("hocKyId") Long hocKyId,
							  @RequestParam("monHocId") Long monHocId,
							  @RequestParam("diemTraBai") Double diemTraBai,
							  @RequestParam("diemGiuaKy") Double diemGiuaKy,
							  @RequestParam("diemCuoiKy") Double diemCuoiKy,
							  @RequestParam("diemTrungBinh") Double diemTrungBinh,
							  @RequestParam(name = "sendMail", defaultValue = "false") Boolean sendMail,
							  RedirectAttributes redirectAttributes){
	    
	    // Kiểm tra xem điểm đã tồn tại cho học sinh, học kỳ và môn học cụ thể hay chưa
	    List<ScoreEntity> existingScores = scoreService.getScoreById(hocKyId, monHocId, hocSinhId);
	    
	    String email = studentService.getEmailByStudentId(hocSinhId);
	    String studentName = studentService.getNameByStudentId(hocSinhId);	    
	    String subjectName = subjectService.getSubjectNameById(monHocId);
	    
	    Long nienkhoaId = semesterService.getNienKhoaIdByHocKyId(hocKyId);
	    
	    Long lophocId = homeroomTeacherService.getClassIdBySemesterAndStudent(hocKyId, hocSinhId);
	    
	    Double min=0.00d, max=10.0d;
	    
	    if ((diemTraBai >= min && diemTraBai <= max)
	    		&& (diemGiuaKy >= min && diemGiuaKy <= max)
	    		&& (diemCuoiKy >= min && diemCuoiKy <= max)
	    		&& (diemTrungBinh >= min && diemTrungBinh <= max)) {
		    if (existingScores != null && !existingScores.isEmpty()) {
		        // Điểm đã tồn tại, bạn có thể cập nhật nó ở đây nếu cần
		        ScoreEntity existingScore = existingScores.get(0);
		        existingScore.setDiemTraBai(diemTraBai);
		        existingScore.setDiemGiuaKy(diemGiuaKy);
		        existingScore.setDiemCuoiKy(diemCuoiKy);
		        existingScore.setDiemTrungBinh(diemTrungBinh);
		        scoreService.updateScore(existingScore);
		        if (sendMail == true) { 
		    	   if (diemCuoiKy != 0.0d) {
		    		   mailService.sendMail(email, studentName, subjectName, 2);
		    	   } else if (diemGiuaKy != 0.0d && diemCuoiKy == 0.0d) {
			    	   mailService.sendMail(email, studentName, subjectName, 1);
			       } else if (diemGiuaKy != 0.0d && diemCuoiKy != 0.0d) {
			    	   mailService.sendMail(email, studentName, subjectName, 3);
			       }
		        }
		    } else {
		        // Điểm chưa tồn tại, bạn có thể tạo mới nó
		        scoreService.createScore(diemTraBai, diemGiuaKy, diemCuoiKy, 
		        						 diemTrungBinh, hocKyId, monHocId, hocSinhId);
		    }
		    return "redirect:/hteacher/home?nienkhoaId=" + nienkhoaId + "&hockyId=" + hocKyId + "&lophocId=" + lophocId + "&monhocId=" + monHocId;
	    }
	    else {
	    	return "redirect:/error";
	    }
	    
	}
	
}
