package com.gotoubun.weddingvendor.domain.weddingtool;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@Column(name="checklist_name", columnDefinition = "TEXT")
	private String checkListName;
	
	@Column(name="created_date")
	@CreatedDate
	private Date createdDate;
	
	@Column(name="modified_date")
	@LastModifiedDate
	private Date modifiedDate;
}
