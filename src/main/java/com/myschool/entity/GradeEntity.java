package com.myschool.entity;

import java.util.HashSet;
import java.util.Set;

import com.myschool.entity.base.AbstractBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "KHOI_HOC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeEntity extends AbstractBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7195304986182538768L;

	@Column(name = "ten_khoi")
	@NotEmpty
	private String tenKhoi;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="khoi_mon_day", joinColumns = @JoinColumn(name="khoihoc_id", referencedColumnName = "id", nullable = false), 
	 								  inverseJoinColumns = @JoinColumn(name="monhoc_id", referencedColumnName = "id", nullable = false)) 
	@OrderBy("tenMonHoc ASC")
	private Set<SubjectEntity> monHoc = new HashSet<>();
}
