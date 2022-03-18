package com.gotoubun.weddingvendor.entity.user;

import java.util.Date;

import javax.persistence.*;
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
	
	@Size(max=9, message="so dien thoai chi duoc 9 chu so")
	@Column(name="phone")
	private String phone;
	
	@Column(name="mail")
	private String mail;
	
	@Column(name="address", columnDefinition = "TEXT")
	private String address;

}
