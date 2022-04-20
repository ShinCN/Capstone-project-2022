package com.gotoubun.weddingvendor.domain.weddingtool;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.gotoubun.weddingvendor.domain.user.Customer;

import lombok.Data;

@Getter
@Setter
@Entity
@Table(name = "guest_list")
public class GuestList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@Column(name="guest_list_name", columnDefinition = "TEXT")
	private String guestListName;
	
	@Column(name="created_date")
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name="modified_date")
	@LastModifiedDate
	private LocalDateTime modifiedDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="guestList", cascade = CascadeType.ALL)
	private Collection<Guest> guests;
}
