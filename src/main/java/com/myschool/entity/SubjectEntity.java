package com.myschool.entity;

import java.util.HashSet;
import java.util.Set;

import com.myschool.entity.base.AbstractBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MON_HOC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectEntity extends AbstractBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5480883590276300678L;

	@Column(name = "ten_mon_hoc")
	@NotEmpty
	@OrderBy("tenMonHoc ASC")
	private String tenMonHoc;
	
	@ManyToMany(mappedBy="monHoc", cascade = CascadeType.ALL)
	private Set<TeacherEntity> giaoVien = new HashSet<>();
	
	@ManyToMany(mappedBy="monHoc", cascade = CascadeType.ALL)
	private Set<GradeEntity> khoiHoc = new HashSet<>();
	
}
