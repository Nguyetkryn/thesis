package com.myschool.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SemesterDTO {

	private String tenHk;
	private Date ngayBatDau;
	private Date ngayKetThuc;
	private String nienKhoa;
}
