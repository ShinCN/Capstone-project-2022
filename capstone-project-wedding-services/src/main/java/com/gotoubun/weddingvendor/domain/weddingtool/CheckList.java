package com.gotoubun.weddingvendor.domain.weddingtool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import com.gotoubun.weddingvendor.domain.vendor.Feedback;
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
	
	@Column(name="checklist_name", columnDefinition = "NVARCHAR(255)")
	private String checkListName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="checkList", cascade = CascadeType.ALL)
	private Collection<ChecklistTask> checklistTasks;

	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Column(name="modified_date")
	private LocalDateTime modifiedDate;
}
