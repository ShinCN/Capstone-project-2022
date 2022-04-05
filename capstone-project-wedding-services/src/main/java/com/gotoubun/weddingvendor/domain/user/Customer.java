package com.gotoubun.weddingvendor.domain.user;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.domain.weddingtool.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity{

	@Column(name="planning_date")
	private LocalDate planningDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="customer", cascade = CascadeType.ALL)
	private Collection<GuestList> guestLists;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="customer", cascade = CascadeType.ALL)
	private Collection<SeatChart> seatCharts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="customer", cascade = CascadeType.ALL)
	private Collection<CheckList> checkLists;
	
	@OneToOne(mappedBy = "customer")
    private Budget budget;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="customer", cascade = CascadeType.ALL)
	private Collection<PaymentHistory> paymentHistorys;

	@ManyToMany(mappedBy = "customers")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<SinglePost> singlePosts;
	
	@ManyToMany(mappedBy = "customers")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<PackagePost> packagePosts;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="account_id")
	private Account account;


//	public Customer(Long id, String fullName, String username, String password, String phone, String mail,
//			String address, Date createdDate, Date modifiedDate) {
//		super(id, fullName, username, password, phone, mail, address, 3, createdDate, modifiedDate);
//
//	}
	
	
}
