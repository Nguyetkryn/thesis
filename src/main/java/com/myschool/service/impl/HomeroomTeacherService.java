package com.myschool.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.entity.ConductEntity;
import com.myschool.entity.HomeroomTeacherEntity;
import com.myschool.repository.IHomeroomTeacherRepository;
import com.myschool.repository.IScoreRepository;
import com.myschool.service.IHomeroomTeacherService;
import com.myschool.service.IScoreService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HomeroomTeacherService implements IHomeroomTeacherService {

	private IHomeroomTeacherRepository homeroomTeacherRepository;

	@Override
	public List<HomeroomTeacherEntity> findAllStHTeacher() {
		return homeroomTeacherRepository.findAll();
	}

	@Override
	public List<HomeroomTeacherEntity> getHTeacherById(Long hockyId, Long lophocId, Long hocsinhId, Long giaovienId,
			Long nienkhoaId) {
		List<HomeroomTeacherEntity> hteachers = homeroomTeacherRepository.findById(hockyId, lophocId, hocsinhId,
				giaovienId, nienkhoaId);
		if (hteachers != null) {
			return hteachers;
		}
		return null;
	}

	@Override
	public List<HomeroomTeacherEntity> getHTeacherById(Long giaovienId) {
		return homeroomTeacherRepository.findByGiaoVienId(giaovienId);
	}

	@Override
	public List<HomeroomTeacherEntity> getStudentById(Long hocsinhId) {
		return homeroomTeacherRepository.findByHocSinhId(hocsinhId);
	}

	@Override
	public void updateHomeroomTeacher(HomeroomTeacherEntity homeroomTeacherUpdates) {
		homeroomTeacherRepository.save(homeroomTeacherUpdates);
	}

	@Override
	public List<HomeroomTeacherEntity> getHTeacherById(Long giaovienId, Long hocKyId) {
		return homeroomTeacherRepository.findByGiaoVienId(giaovienId, hocKyId);
	}

	@Override
	public void createHomeroomTeacher(Long hockyId, Long lophocId, Long hocsinhId, Long giaovienId, Long hanhkiemId,
			Long nienkhoaId) {
		homeroomTeacherRepository.insertHomeroomTeacher(giaovienId, hocsinhId, hockyId, lophocId, hanhkiemId,
				nienkhoaId);
	}

	@Override
	public Long getConductId(Long semesterId, Long studentId) {
		Long hanhKiem = homeroomTeacherRepository.findByHanhKiemId(studentId, semesterId);
		return hanhKiem;
	}

	@Override
	public String conductAllYear(Long hocKyI, Long hocKyII) {
		String hanhKiem = "";
		if (hocKyI == 1 && hocKyII == 1) {
			hanhKiem = "Tốt";
		} else if (hocKyI == 3 || hocKyII == 3) {
			hanhKiem = "Chưa Đạt";
		} else {
			hanhKiem = "Đạt";
		}
		return hanhKiem;
	}

	@Override
	public HomeroomTeacherEntity getConduct(Long semesterId, Long studentId) {
		// TODO Auto-generated method stub
		return homeroomTeacherRepository.findByHanhKiem(studentId, semesterId);
	}

	@Override
	public int updateConduct(Long hockyId, Long hocsinhId, Long hanhkiemId, Long giaovienId, Long lophocId) {
		return homeroomTeacherRepository.updateConductId(hanhkiemId, hockyId, hocsinhId, giaovienId, lophocId);
	}

	@Override
	public List<HomeroomTeacherEntity> getStudentByTeacherAndSemester(Long teacherId, Long semesterId, Long yearId) {
		// TODO Auto-generated method stub
		return homeroomTeacherRepository.findByStudentByTeacherAndSemester(teacherId, semesterId, yearId);
		// return null;
	}

	@Override
	public Long getClassIdBySemesterAndStudent(Long semesterId, Long studentId) {
		// TODO Auto-generated method stub
		return homeroomTeacherRepository.getClassIdBySemesterAndStudent(semesterId, studentId);
	}

	public int[] countConductLevelsForSemesterAndYear(Long nienKhoaId) {
		int[] conductCount = new int[3];
		List<HomeroomTeacherEntity> conducts = homeroomTeacherRepository.getConductByNienKhoaId(nienKhoaId);
        for (HomeroomTeacherEntity conduct : conducts) {
        	Long conductId = conduct.getHanhKiem().getId();
            if (conductId == 1)
            	conductCount[0]++;
            else if (conductId == 2)
            	conductCount[1]++;
            else if (conductId == 3)
            	conductCount[2]++;
        }
        return conductCount;
	}

	@Override
	public List<HomeroomTeacherEntity> getStudentByNienKhoaId(Long nienkhoaId) {
		// TODO Auto-generated method stub
		return homeroomTeacherRepository.getConductByNienKhoaId(nienkhoaId);
	}

	@Override
	public List<HomeroomTeacherEntity> getStudentBySemesterAndYear(Long semesterId, Long yearId) {
		// TODO Auto-generated method stub
		return homeroomTeacherRepository.findByStudentBySemesterAndYear(semesterId, yearId);
	}
	
	

}
