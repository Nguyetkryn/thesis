package com.myschool.entity;

import java.util.HashSet;
import java.util.Set;

import com.myschool.entity.base.AbstractBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GIAO_VIEN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherEntity extends AbstractBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6668234289514678809L;

	@Column(name = "trinh_do")
	@NotEmpty
	private String trinhDo;

	@Column(name = "tham_nien")
	private String thamNien;

	@Column(name = "chu_nhiem_flag")
	private Boolean gvcn;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="mon_giang_day", joinColumns = @JoinColumn(name="giaovien_id", referencedColumnName = "id", nullable = false), 
	 								  inverseJoinColumns = @JoinColumn(name="monhoc_id", referencedColumnName = "id", nullable = false)) 
	@OrderBy("tenMonHoc ASC")
	private Set<SubjectEntity>	monHoc = new HashSet<>();

	@ManyToMany(mappedBy="giaoVien")
	private Set<UserEntity> nguoiDung = new HashSet<>();

}
