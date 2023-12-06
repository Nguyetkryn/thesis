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
public class UserEntityRoleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2615896851753156618L;
	private Long NGUOI_DUNG;
	private Long PHAN_QUYEN;

}
