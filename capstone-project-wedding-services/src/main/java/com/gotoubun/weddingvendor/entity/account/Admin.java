package com.gotoubun.weddingvendor.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class Admin extends BaseEntity{
	@Column(name="duty")
	private String duty;
}
