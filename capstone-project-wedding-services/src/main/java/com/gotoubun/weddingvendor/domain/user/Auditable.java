package com.gotoubun.weddingvendor.domain.user;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Nationalized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Nationalized
	@Column(name="full_name")
	private String fullName;

	@Column(name="phone")
	private String phone;
	
	@Column(name="email")
	private String email;

	@Nationalized
	@Column(name="address")
	private String address;

}