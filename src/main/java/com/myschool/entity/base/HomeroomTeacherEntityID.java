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
public class HomeroomTeacherEntityID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5657428502783935565L;
	
	private Long giaoVien;
    private Long hocSinh;
    private Long hocKy;
    private Long lopHoc;
    private Long nienKhoa;
}
