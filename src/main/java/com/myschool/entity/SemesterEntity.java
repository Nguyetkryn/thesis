package com.myschool.entity;

import java.util.Date;

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
@Table(name = "HOC_KY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemesterEntity extends AbstractBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7371046792233161495L;

	@Column(name = "ten_hk")
	@NotEmpty
	private String tenHk;
	
	@Column(name = "ngay_bat_dau")
	private Date ngayBatDau;
	
	@Column(name = "ngay_ket_thuc")
	private Date ngayKetThuc;
	
	@ManyToOne
	@JoinColumn(name="nienkhoa_id", nullable = false)
	private SchoolYearEntity nienKhoa;
}
