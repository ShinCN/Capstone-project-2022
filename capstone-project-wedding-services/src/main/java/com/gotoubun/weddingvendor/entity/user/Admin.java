package com.gotoubun.weddingvendor.entity.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class Admin extends BaseEntity{
	
}
