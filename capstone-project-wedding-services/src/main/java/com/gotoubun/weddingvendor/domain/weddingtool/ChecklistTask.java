package com.gotoubun.weddingvendor.domain.weddingtool;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
@Entity
@Table(name = "checklist_task")
public class ChecklistTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="task_name", columnDefinition = "TEXT")
	private String taskName;
	
	@ManyToOne
	@JoinColumn(name = "check_list_id", nullable = false)
	private CheckList checkList;
	
	@Column(name="status")
	private boolean status;
	
	@Column(name="created_date")
	@CreatedDate
	private Date createdDate;
	
	@Column(name="modified_date")
	@LastModifiedDate
	private Date modifiedDate;
}
