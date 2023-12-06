package com.myschool.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.entity.ConductEntity;
import com.myschool.entity.HomeroomTeacherEntity;
import com.myschool.entity.ScoreEntity;
import com.myschool.entity.SemesterEntity;
import com.myschool.entity.StudentEntity;
import com.myschool.entity.SubjectEntity;
import com.myschool.repository.IHomeroomTeacherRepository;
import com.myschool.repository.IScoreRepository;
import com.myschool.repository.ISemesterRepository;
import com.myschool.service.IHomeroomTeacherService;
import com.myschool.service.IScoreService;
import com.myschool.service.ISemesterService;
import com.myschool.service.ISubjectService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScoreService implements IScoreService {

	private IScoreRepository scoreRepository;
	private ISubjectService subjectService;
	private ISemesterService semesterService;
	private IHomeroomTeacherService homeroomTeacherService;

	public List<ScoreEntity> getAllScores() {
		List<ScoreEntity> scores = scoreRepository.findAll();
		return scores;
	}

	public List<ScoreEntity> getScoreById(Long semesterId, Long subjectId, Long studentId) {
		List<ScoreEntity> score = scoreRepository.findById(semesterId, studentId, subjectId);
		if (score != null) {
			return score;
		}
		return null;
	}

	public ScoreEntity mapToDTO(ScoreEntity score) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(score, ScoreEntity.class);
	}

	public void updateScore(ScoreEntity scoreUpdates) {
		scoreRepository.save(scoreUpdates);
	}

	public void createScore(Double diemTraBai, Double diemGiuaKy, Double diemCuoiKy, Double diemTrungBinh, Long hocKyId,
			Long monHocId, Long hocSinhId) {
		scoreRepository.updateScore(diemTraBai, diemGiuaKy, diemCuoiKy, diemTrungBinh, hocKyId, hocSinhId, monHocId);
	}

	public void createScores(Long hocKyId, Long hocSinhId, Long lopHocId) {
		List<SubjectEntity> subjects = subjectService.getAllSubject();
		for (SubjectEntity subject : subjects) {
			Long monHocId = subject.getId();
			if (lopHocId >= 3 && monHocId == 4) {
				scoreRepository.insertScore(-1d, -1d, -1d, -1d, hocKyId, hocSinhId, monHocId);
			} else if (lopHocId < 3 && monHocId == 5) {
				scoreRepository.insertScore(-1d, -1d, -1d, -1d, hocKyId, hocSinhId, monHocId);
			} else {
				scoreRepository.insertScore(0d, 0d, 0d, 0d, hocKyId, hocSinhId, monHocId);
			}
		}
	}

	public List<ScoreEntity> getScoreBySemesterId(Long semesterId) {
		List<ScoreEntity> scores = scoreRepository.findBySemesterId(semesterId);
		if (scores != null) {
			return scores;
		}
		return null;
	}

	public Double semesterGPA(Long semesterId, Long studentId) {
		Double semesterGPA = 0.0d;
		int soMonHoc = 0;
		List<ScoreEntity> scoreEntities = scoreRepository.findByDiemTrungBinh(semesterId, studentId);
		for (ScoreEntity scoreEntity : scoreEntities) {
			if (scoreEntity.getDiemTrungBinh() >= 0 && !scoreEntity.getMonHoc().equals("Thể Dục")) {
				semesterGPA += scoreEntity.getDiemTrungBinh();
				soMonHoc++;
			}
		}
		if (soMonHoc > 0) {
			return semesterGPA / soMonHoc;
		} else {
			return 0.0;
		}
	}

	public String classification(Long semesterId, Long studentId, String conduct) {
		String classification = null;
		List<ScoreEntity> scoreEntities = scoreRepository.findByDiemTrungBinh(semesterId, studentId);
		for (ScoreEntity scoreEntity : scoreEntities) {
			if (scoreEntity.getDiemTrungBinh() >= 0 && !scoreEntity.getMonHoc().equals("Thể Dục")) {
				if (scoreEntity.getDiemTrungBinh() >= 9 && conduct.equals("Tốt")) {
					classification = "Hoàn Thành Xuất Sắc";
				} else if (scoreEntity.getDiemTrungBinh() >= 7 && conduct.equals("Tốt")) {
					classification = "Hoàn Thành Tốt";
				} else if (scoreEntity.getDiemTrungBinh() >= 5 && (conduct.equals("Tốt") || conduct.equals("Đạt"))) {
					classification = "Hoàn Thành";
				} else {
					classification = "Chưa Hoàn Thành";
				}
			}
			if (scoreEntity.getMonHoc().equals("Thể Dục")) {
				if (scoreEntity.getDiemTrungBinh() == 0) {
					classification = "Chưa Hoàn Thành";
				} else {
					classification = "Hoàn Thành Xuất Sắc";
				}
			}
		}
		return classification;
	}

	@Override
	public List<ScoreEntity> getScoreBySemesterStudentId(Long hocKyId, Long studentId) {
		return scoreRepository.findBySemesterStudetnId(hocKyId, studentId);
	}

	public int[] getAverageScoresByStudentAndSemester(List<Long> studentIds, Long semesterId) {
		int[] diemTrungBinhCount = new int[11]; // lưu trữ số lần xuất hiện của từng điểm, từ 0 đến 10
		for (Long studentId : studentIds) {
			List<ScoreEntity> scoreEntities = scoreRepository.findByDiemTrungBinh(semesterId, studentId);
			for (ScoreEntity scoreEntity : scoreEntities) {
				if (!scoreEntity.getMonHoc().getTenMonHoc().equals("Thể Dục")) {
					if (scoreEntity.getDiemTrungBinh() <= 10 && scoreEntity.getDiemTrungBinh() > 9) {
						diemTrungBinhCount[9]++;
					}
					else if (scoreEntity.getDiemTrungBinh() <= 9 && scoreEntity.getDiemTrungBinh() > 8) {
						diemTrungBinhCount[8]++;
					}
					else if (scoreEntity.getDiemTrungBinh() <= 8 && scoreEntity.getDiemTrungBinh() > 7) {
						diemTrungBinhCount[7]++;
					}
					else if (scoreEntity.getDiemTrungBinh() <= 7 && scoreEntity.getDiemTrungBinh() > 6) {
						diemTrungBinhCount[6]++;
					}
					else if (scoreEntity.getDiemTrungBinh() <= 6 && scoreEntity.getDiemTrungBinh() > 5) {
						diemTrungBinhCount[5]++;
					}
					else if (scoreEntity.getDiemTrungBinh() <= 5 && scoreEntity.getDiemTrungBinh() > 4) {
						diemTrungBinhCount[4]++;
					}
					else if (scoreEntity.getDiemTrungBinh() <= 4 && scoreEntity.getDiemTrungBinh() > 3) {
						diemTrungBinhCount[3]++;
					}
					else if (scoreEntity.getDiemTrungBinh() <= 3 && scoreEntity.getDiemTrungBinh() > 2) {
						diemTrungBinhCount[2]++;
					}
					else if (scoreEntity.getDiemTrungBinh() <= 2 && scoreEntity.getDiemTrungBinh() > 1) {
						diemTrungBinhCount[1]++;
					}
					else if (scoreEntity.getDiemTrungBinh() <= 1 && scoreEntity.getDiemTrungBinh() > 0){
						diemTrungBinhCount[0]++;
					}
				}
			}
		}
		return diemTrungBinhCount;
	}

	@Override
	public List<ScoreEntity> getScoreBySemesterAndSubject(Long semesterId, Long subjectId) {
		// TODO Auto-generated method stub
		return scoreRepository.getScoreBySemesterAndSubject(semesterId, subjectId );
	}

	public int[] countAacademicAbility (Long nienKhoa, List<Long> studentIds) {
		int[] countAacademicAbilities = new int[4];
		Long semesterId;
		String conduct, hocluc;
		
		List<SemesterEntity> semesterEntities = semesterService.findSemesterEntitiesBySchoolYearId(nienKhoa);
		for (SemesterEntity semesterEntity : semesterEntities) {
			for (Long studentId : studentIds) {
				semesterId = semesterEntity.getId();
				conduct = homeroomTeacherService.getConduct(semesterId, studentId).getHanhKiem().getTenHanhKiem();
				hocluc = classification(semesterId, studentId, conduct);
				if (hocluc.equals("Hoàn Thành Xuất Sắc"))
					countAacademicAbilities[0]++;
				else if (hocluc.equals("Hoàn Thành Tốt"))
					countAacademicAbilities[1]++;
				else if (hocluc.equals("Hoàn Thành"))
					countAacademicAbilities[2]++;
				else if (hocluc.equals("Hoàn Thành Chưa Hoàn Thành"))
					countAacademicAbilities[3]++;
			}
		}
		return countAacademicAbilities;
	}
}
