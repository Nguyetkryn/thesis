package com.myschool.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myschool.entity.ClassEntity;
import com.myschool.entity.ConductEntity;
import com.myschool.repository.IClassRepository;
import com.myschool.repository.IConductRepository;
import com.myschool.service.IClassService;
import com.myschool.service.IConductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ConductService implements IConductService {
	
	private IConductRepository conductRepository;
	
	public List<ConductEntity> getAllConduct() {
		// TODO Auto-generated method stub
		return conductRepository.findAll();
	}

	@Override
	public ConductEntity getConductById(Long conductId) {
		// TODO Auto-generated method stub
		return conductRepository.findById(conductId).orElse(null);
	}


}
