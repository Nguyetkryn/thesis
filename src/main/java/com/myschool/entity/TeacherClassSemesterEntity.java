package com.myschool.entity;

import com.myschool.entity.base.TeacherClassSemesterId;

import jakarta.persistence.CascadeType;
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
@Table(name = "GIAO_VIEN_DAY_LOP")
@IdClass(TeacherClassSemesterId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherClassSemesterEntity {

	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "giaovien_id", referencedColumnName = "id")
	private TeacherEntity giaoVien;

	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "lophoc_id", referencedColumnName = "id")
	private ClassEntity lopHoc;

	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hocky_id", referencedColumnName = "id")
	private SemesterEntity hocKy;

}
