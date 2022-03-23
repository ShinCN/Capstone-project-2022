package com.gotoubun.weddingvendor.domain.weddingtool;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "guest")
public class Guest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="fullname", columnDefinition = "TEXT")
	private String fullName;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="mail")
	private String mail;
	
	@Column(name="address", columnDefinition = "TEXT")
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "guest_list_id", nullable = false)
	private GuestList guestList;
}
