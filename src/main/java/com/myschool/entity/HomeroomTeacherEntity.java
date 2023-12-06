package com.myschool.entity;

import com.myschool.entity.base.HomeroomTeacherEntityID;
import com.myschool.service.IConductService;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GIAO_VIEN_CHU_NHIEM")
@IdClass(HomeroomTeacherEntityID.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeroomTeacherEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7252149034988623916L;

	@Id
	@ManyToOne
	@JoinColumn(name="giaovien_id", referencedColumnName = "id", nullable = false)
	private TeacherEntity giaoVien;
	
	@Id
	@ManyToOne
	@JoinColumn(name="hocsinh_id", referencedColumnName = "id", nullable = false)
	private StudentEntity hocSinh;
	
	@Id
	@ManyToOne
	@JoinColumn(name="hocky_id", referencedColumnName = "id", nullable = false)
	private SemesterEntity hocKy;
	
	@Id
	@ManyToOne
	@JoinColumn(name="nienkhoa_id", referencedColumnName = "id", nullable = false)
	private SchoolYearEntity nienKhoa;
	
	@Id
	@ManyToOne
	@JoinColumn(name="lophoc_id", referencedColumnName = "id", nullable = false)
	private ClassEntity lopHoc;
	
	@ManyToOne
	@JoinColumn(name="hanhkiem_id", referencedColumnName = "id", nullable = false)
	private ConductEntity hanhKiem;

	public void setGiaoVien(Long giaovienId) {}
	public void setHocKy(Long hockyId) {}
	public void setHocSinh(Long hocsinhId) {}
	public void setLopHoc(Long lophocId) {}
	public void setHanhKiem(Long hanhkiemId) {}
	
	public void setGiaoVien(String giaovienId) {}
	public void setHocKy(String hockyId) {}
	public void setHocSinh(String hocsinhId) {}
	public void setLopHoc(String lophocId) {}
	

}
