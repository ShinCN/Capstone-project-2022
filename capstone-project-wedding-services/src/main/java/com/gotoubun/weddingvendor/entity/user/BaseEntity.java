package com.gotoubun.weddingvendor.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
	
	
	@Column(name="fullname", columnDefinition = "TEXT")
	private String fullName;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Size(max=9, message="so dien thoai chi duoc 9 chu so")
	@Column(name="phone")
	private String phone;
	
	@Column(name="mail")
	private String mail;
	
	@Column(name="address", columnDefinition = "TEXT")
	private String address;
	
	//admin=1,vendor=2,customer=3
	@Column(name="role")
	private int role;
	
	@Column(name="created_date")
	@CreatedDate
	private Date createdDate;
	
	@Column(name="modified_date")
	@LastModifiedDate
	private Date modifiedDate;
}
