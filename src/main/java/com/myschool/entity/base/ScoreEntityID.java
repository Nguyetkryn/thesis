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
public class ScoreEntityID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5657428502783935565L;
	
	private Long monHoc;
    private Long hocSinh;
    private Long hocKy;
}
