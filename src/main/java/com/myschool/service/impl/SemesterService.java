package com.myschool.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.entity.SchoolYearEntity;
import com.myschool.entity.SemesterEntity;
import com.myschool.repository.ISemesterRepository;
import com.myschool.service.ISemesterService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SemesterService implements ISemesterService {

	@Autowired
	private ISemesterRepository semesterRepository;

	@Override
	public List<SemesterEntity> getAllSemesters() {
		return semesterRepository.findAll();
	}

	@Override
	public List<SemesterEntity> findSemesterEntitiesBySchoolYearId(Long schoolYearId) {
		return semesterRepository.findByNienKhoaId(schoolYearId);
	}

	@Override
	public List<Long> getSemesterIdBySchoolYearId(Long schoolYearId) {
		// Get a list of semesterIds by school year
		List<Long> semesterIds = new ArrayList<>();
		
		List<SemesterEntity> semesterEntities = semesterRepository.findByNienKhoaId(schoolYearId);
		
		for (SemesterEntity semesterEntity : semesterEntities) {
			semesterIds.add(semesterEntity.getId());
		}
		return semesterIds;
	}

	@Override
	public SemesterEntity getSemesterById(Long semesterId) {
		return semesterRepository.findById(semesterId).orElse(null);
	}
	
	public Long getNienKhoaIdByHocKyId(Long hocKyId) {
        SemesterEntity semester = semesterRepository.findById(hocKyId).orElse(null);

        if (semester != null) {
            SchoolYearEntity schoolYear = semester.getNienKhoa();
            if (schoolYear != null) {
                return schoolYear.getId();
            }
        }

        return null;
    }

}
