package com.myschool.dto;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	private String hoTen;
	@NotEmpty
	private String gioiTinh;
	
	private String sdt;
	@NotEmpty
	private Date namSinh;
	@NotEmpty
	private String diaChi;	
}
