package com.gotoubun.weddingvendor.domain.vendor;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BasePost extends BaseEntity{

	@Column(name = "service_name")
	private String serviceName;

	@Column(name="about")
	private String about;

	@Column(name="price")
	private Float price;

	@Column(name="rate")
	private Integer rate;
}