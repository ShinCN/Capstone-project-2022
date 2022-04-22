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
	private LocalDateTime createdDate;

	@Column(name="modified_date")
	private LocalDateTime modifiedDate;

	@Column(name="discard_date")
	private LocalDateTime discardedDate;
	
	@Column(name="created_by")
	private String createdBy;

	@Column(name="modified_by")
	private String modifiedBy;
}
