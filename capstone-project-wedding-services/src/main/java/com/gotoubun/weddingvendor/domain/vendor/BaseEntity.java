package com.gotoubun.weddingvendor.domain.vendor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@Column(name="created_date")
	@JsonIgnore
	private LocalDateTime createdDate;

	@Column(name="modified_date")
	@JsonIgnore
	private LocalDateTime modifiedDate;
	
	@Column(name="createdby")
	@CreatedBy
	@JsonIgnore
	private String createdBy;
	
	@Column(name="modifiedby")
	@LastModifiedBy
	@JsonIgnore
	private String modifiedBy;
}
