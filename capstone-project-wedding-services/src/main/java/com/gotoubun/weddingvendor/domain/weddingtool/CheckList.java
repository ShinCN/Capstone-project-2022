package com.gotoubun.weddingvendor.domain.weddingtool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.gotoubun.weddingvendor.domain.user.Customer;

import lombok.Data;

@Getter
@Setter
@Entity
@Table(name = "checklist")
public class CheckList {

	@Id
	@Column(unique=true,columnDefinition="NVARCHAR(255)")
	private String id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@Column(name="checklist_name", columnDefinition = "TEXT")
	private String checkListName;


	@Column(name="created_date")
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name="modified_date")
	@LastModifiedDate
	private LocalDateTime modifiedDate;
}
