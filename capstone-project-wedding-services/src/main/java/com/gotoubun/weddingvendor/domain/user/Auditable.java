package com.gotoubun.weddingvendor.domain.user;

import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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