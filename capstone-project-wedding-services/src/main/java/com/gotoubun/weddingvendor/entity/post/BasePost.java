package com.gotoubun.weddingvendor.entity.post;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BasePost extends BaseEntity{
	
	@Column(name="about")
	private String about;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="rate")
	private Integer rate;
}
