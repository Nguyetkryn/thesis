package com.myschool.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.myschool.entity.base.AbstractBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "NGUOI_DUNG", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), 
												 @UniqueConstraint(columnNames = "email")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends AbstractBase implements Serializable, UserDetails {

	@Column(name = "username", unique = true)
	@NotEmpty
	private String username;

	@Column(name = "password")
	@NotEmpty
	private String password;

	@Column(name = "ho_ten")
	@NotEmpty
	private String hoTen;

	@Column(name = "gioi_tinh")
	@NotEmpty
	private String gioiTinh;

	@Column(name = "sdt")
	@Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 characters")
	private String sdt;

	@Column(name = "nam_sinh")
	private Date namSinh;

	@Column(name = "dia_chi")
	@NotEmpty
	private String diaChi;
	
	@Column(name = "email", unique = true)
	@NotEmpty
	private String email;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "NGUOIDUNG_PHANQUYEN", joinColumns = @JoinColumn(name = "NGUOI_DUNG_id"), inverseJoinColumns = @JoinColumn(name = "PHAN_QUYEN_id"))
	private Collection<RoleEntity> PHAN_QUYEN = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "NGUOIDUNG_GIAOVIEN", joinColumns = @JoinColumn(name = "nguoidung_id"), inverseJoinColumns = @JoinColumn(name = "giaovien_id"))
	private Set<TeacherEntity> giaoVien = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "NGUOIDUNG_HOCSINH", joinColumns = @JoinColumn(name = "nguoidung_id"), inverseJoinColumns = @JoinColumn(name = "hocsinh_id"))
	private Set<StudentEntity> hocSinh = new HashSet<>();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.PHAN_QUYEN;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
	public StudentEntity getOrCreateStudent() {
		if(hocSinh.isEmpty()) {
			StudentEntity studentEntity = new StudentEntity(); //tạo 1 student rỗng
			hocSinh.add(studentEntity); //thêm vào field học sinh là student vừa được tạo
			studentEntity.getNguoiDung().add(this); //thêm vào student là thông tin user
			return studentEntity;
		}
		else {
			return hocSinh.iterator().next();
		}
	}
	
	public TeacherEntity getOrCreateTeacher() {
		if(giaoVien.isEmpty()) {
			TeacherEntity teacherEntity = new TeacherEntity(); //tạo 1 student rỗng
			giaoVien.add(teacherEntity); //thêm vào field học sinh là student vừa được tạo
			teacherEntity.getNguoiDung().add(this); //thêm vào student là thông tin user
			return teacherEntity;
		}
		else {
			return giaoVien.iterator().next();
		}
	}
	
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

}
