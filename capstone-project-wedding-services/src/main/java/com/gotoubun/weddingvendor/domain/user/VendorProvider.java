package com.gotoubun.weddingvendor.domain.user;

import java.util.Collection;

import javax.persistence.*;

import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vendor")
public class VendorProvider extends BaseEntity{

	@Column(name="company", columnDefinition = "TEXT")
	private String company;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="vendorProvider", cascade = CascadeType.ALL)
	private Collection<SinglePost> singlePosts;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "single_category_id")
	private SingleCategory singleCategory;

	@OneToOne
	@JoinColumn(name="account_id")
	private Account account;

//	public VendorProvider(Long id, String fullName, String username, String password, String phone, String mail,
//			String address, Date createdDate, Date modifiedDate) {
//		super(id, fullName, username, password, phone, mail, address, 2, createdDate, modifiedDate);
//		// TODO Auto-generated constructor stub
//	}
	
	
}
