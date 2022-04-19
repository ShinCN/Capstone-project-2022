package com.gotoubun.weddingvendor.domain.vendor;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@Column(name="created_date")
	@JsonIgnore
	private Date createdDate;

	@Column(name="modified_date")
	@JsonIgnore
	private Date modifiedDate;

	@PrePersist
	protected void onCreate(){
		this.createdDate = new Date();
	}

	@PreUpdate
	protected void onUpdate(){
		this.modifiedDate = new Date();
	}
	
	@Column(name="created_by")
	private String createdBy;


	@Column(name="modified_by")
	private String modifiedBy;
}
