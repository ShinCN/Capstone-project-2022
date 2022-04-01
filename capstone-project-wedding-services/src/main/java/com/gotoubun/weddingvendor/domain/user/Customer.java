package com.gotoubun.weddingvendor.domain.user;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import com.gotoubun.weddingvendor.domain.vendor.Feedback;
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
public class Customer extends Auditable{
	@Column(name="planning_date")
	private Date planningDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="customer", cascade = CascadeType.ALL)
	private Collection<GuestList> guestLists;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="customer", cascade = CascadeType.ALL)
	private Collection<SeatChart> seatCharts;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="customer", cascade = CascadeType.ALL)
	private Collection<CheckList> checkLists;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="customer", cascade = CascadeType.ALL)
	private Collection<Feedback> feedbacks;

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

}