package com.myschool.entity;

import com.myschool.entity.base.AbstractBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LOP_HOC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassEntity extends AbstractBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8825710025740057849L;

	@Column(name = "ten_lop")
	@NotEmpty
	private String tenLop;
	
	@Column(name = "si_so")
	@NotEmpty
	private int siSo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="khoihoc_id", referencedColumnName = "id", nullable = false)
	private GradeEntity khoiHoc;
	
	/*
	 * @ManyToMany(mappedBy="lopHoc") private Set<TeacherEntity> giaoVien = new
	 * HashSet<>();
	 * 
	 * @ManyToMany(mappedBy="lopHoc") private Set<StudentEntity> hocSinh = new
	 * HashSet<>();
	 */
	
}
