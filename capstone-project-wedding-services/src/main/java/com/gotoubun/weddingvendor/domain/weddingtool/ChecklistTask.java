package com.gotoubun.weddingvendor.domain.weddingtool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import lombok.Data;

@Getter
@Setter
@Entity
@Table(name = "checklist_task")
public class ChecklistTask {
	@Id
	@Column(unique=true,columnDefinition="NVARCHAR(255)")
	private String id;
	
	@Column(name="task_name", columnDefinition = "TEXT")
	private String taskName;
	
	@ManyToOne
	@JoinColumn(name = "check_list_id", nullable = false)
	private CheckList checkList;

	@Column(name="due_date")
	private LocalDate dueDate;


	@Column(name="status")
	private boolean status;
	
	@Column(name="created_date")
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name="modified_date")
	@LastModifiedDate
	private LocalDateTime modifiedDate;
}
