package com.myschool.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.myschool.entity.base.AbstractBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PHAN_QUYEN")
public class RoleEntity extends AbstractBase implements Serializable, GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8463309207252947585L;

	@Column(name = "ten_quyen")
	@NotEmpty
	private String tenQuyen;

	@ManyToMany(mappedBy = "PHAN_QUYEN", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserEntity> NGUOI_DUNG = new HashSet<>();

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.tenQuyen;
	}

}
