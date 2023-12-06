package com.myschool.service;

import java.util.List;

import com.myschool.entity.ClassEntity;
import com.myschool.entity.ConductEntity;

public interface IConductService {

	List<ConductEntity> getAllConduct();
	
	ConductEntity getConductById(Long conductId);
}
