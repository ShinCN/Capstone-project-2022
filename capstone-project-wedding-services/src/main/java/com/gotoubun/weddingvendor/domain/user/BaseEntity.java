package com.gotoubun.weddingvendor.domain.user;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="full_name", columnDefinition = "TEXT")
	private String fullName;
	
	@Size(max=9, message="so dien thoai chi duoc 9 chu so")
	@Column(name="phone")
	private String phone;
	
	@Column(name="mail")
	private String mail;
	
	@Column(name="address", columnDefinition = "TEXT")
	private String address;

}
