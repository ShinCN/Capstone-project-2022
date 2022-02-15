package com.gotoubun.weddingvendor.entity.user;

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

import com.gotoubun.weddingvendor.entity.vendor.PackagePost;
import com.gotoubun.weddingvendor.entity.vendor.SinglePost;
import com.gotoubun.weddingvendor.entity.weddingtool.Budget;
import com.gotoubun.weddingvendor.entity.weddingtool.CheckList;
import com.gotoubun.weddingvendor.entity.weddingtool.GuestList;
import com.gotoubun.weddingvendor.entity.weddingtool.SeatChart;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
}
