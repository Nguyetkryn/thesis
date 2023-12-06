package com.myschool.entity.base;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherClassSemesterId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -778834312688773999L;
	private Long giaoVien;
	private Long lopHoc;
	private Long hocKy;
}
