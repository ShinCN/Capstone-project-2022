package com.gotoubun.weddingvendor.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Entity
@Table(name = "account")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="password")
	private String password;

	@Column(name="authorize")
	private String authorize;

	public Account( String userName, String password, String authorize) {
		super();
		this.userName = userName;
		this.password = password;
		this.authorize = authorize;
	}

	public Account() {
		super();
	}
	
	
}
