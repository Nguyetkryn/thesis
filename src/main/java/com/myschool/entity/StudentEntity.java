package com.myschool.entity;

import java.util.HashSet;
import java.util.Set;

import com.myschool.entity.base.AbstractBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HOC_SINH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity extends AbstractBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7252149034988623916L;

	@Column(name = "sdt_phu_huynh")
	@Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 characters")
	@NotEmpty
	private String sdtPhuHuynh;
	
	@Column(name = "ten_phu_huynh")
	@NotEmpty
	private String tenPhuHuynh;
	
	@Column(name = "ghi_chu", columnDefinition = "TEXT")
	private String ghiChu;
	
	@ManyToMany(mappedBy="hocSinh")
	private Set<UserEntity> nguoiDung = new HashSet<>();
	
	@OneToMany(mappedBy="hocSinh")
	private Set<ScoreEntity> scoreEntities = new HashSet<>(); 
}
