package com.myschool.entity;

import com.myschool.entity.base.AbstractBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HANH_KIEM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConductEntity extends AbstractBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7252149034988623916L;

	
	@Column(name = "ten_hanh_kiem")
	@NotEmpty
	private String tenHanhKiem;
}
