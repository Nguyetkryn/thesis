package com.myschool.entity;

import java.util.HashSet;
import java.util.Set;

import com.myschool.entity.base.AbstractBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "NIEN_KHOA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolYearEntity extends AbstractBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4544936775930387584L;

	@Column(name = "ten_nien_khoa")
	@NotEmpty
	private String tenNk;
	
	@OneToMany(mappedBy = "nienKhoa")
	private Set<SemesterEntity> hocKy = new HashSet<>();
}
