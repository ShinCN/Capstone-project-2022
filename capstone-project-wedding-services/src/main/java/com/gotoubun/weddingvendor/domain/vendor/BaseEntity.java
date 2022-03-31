package com.gotoubun.weddingvendor.domain.vendor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	
	@Column(name="created_date")
	@CreatedDate
	@JsonIgnore
	private Date createdDate;
	
	@Column(name="modified_date")
	@LastModifiedDate
	@JsonIgnore
	private Date modifiedDate;
	
	@Column(name="createdby")
	@CreatedBy
	@JsonIgnore
	private String createdBy;
	
	@Column(name="modifiedby")
	@LastModifiedBy
	@JsonIgnore
	private String modifiedBy;
	
}
