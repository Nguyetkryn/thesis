package com.myschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
	
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreFilterDTO {
    private String monHoc;  // Sử dụng ID thay vì trực tiếp tham chiếu đến entity MonHoc
    private Long hocSinhId;  // Sử dụng ID thay vì trực tiếp tham chiếu đến entity HocSinh
    private Long hocKyId; // Sử dụng ID thay vì trực tiếp tham chiếu đến entity HocKy
}

