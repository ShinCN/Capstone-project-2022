package com.gotoubun.weddingvendor.domain.user;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.domain.weddingtool.Budget;
import com.gotoubun.weddingvendor.domain.weddingtool.CheckList;
import com.gotoubun.weddingvendor.domain.weddingtool.GuestList;
import com.gotoubun.weddingvendor.domain.weddingtool.SeatChart;

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
	private Date planningDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="customer", cascade = CascadeType.ALL)
	private Collection<GuestList> guestLists;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="customer", cascade = CascadeType.ALL)
	private Collection<SeatChart> seatCharts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="customer", cascade = CascadeType.ALL)
	private Collection<CheckList> checkLists;
	
	@OneToOne(mappedBy = "customer")
    private Budget budget;
	
	@ManyToMany(mappedBy = "customers")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<SinglePost> singlePosts;
	
	@ManyToMany(mappedBy = "customers")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<PackagePost> packagePosts;

	public Customer(Long id, String fullName, String username, String password, String phone, String mail,
			String address, Date createdDate, Date modifiedDate) {
		super(id, fullName, username, password, phone, mail, address, 3, createdDate, modifiedDate);
	
	}
	
	
}
