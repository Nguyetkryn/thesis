package com.myschool.entity;

import com.myschool.entity.base.ScoreEntityID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "KET_QUA_HOC_TAP")
@IdClass(ScoreEntityID.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreEntity{
	
	@Column(name = "diem_tra_bai")
	private Double diemTraBai = 0.0d;
	
	@Column(name = "diem_giua_ky")
	private Double diemGiuaKy = 0.0d;

	@Column(name = "diem_cuoi_ky")
	private Double diemCuoiKy = 0.0d;

	@Column(name = "diem_trung_binh")
	private Double diemTrungBinh = 0.0d;
	
	@Id
	@ManyToOne
	@JoinColumn(name="monhoc_id", referencedColumnName = "id", nullable = false)
	@OrderBy("tenMonHoc ASC")
	private SubjectEntity monHoc;
	
	@Id
	@ManyToOne
	@JoinColumn(name="hocsinh_id", referencedColumnName = "id", nullable = false)
	private StudentEntity hocSinh;
	
	@Id
	@OneToOne
	@JoinColumn(name="hocky_id", referencedColumnName = "id", nullable = false)
	private SemesterEntity hocKy;

	public void setId(Long id) {
	}	
}
