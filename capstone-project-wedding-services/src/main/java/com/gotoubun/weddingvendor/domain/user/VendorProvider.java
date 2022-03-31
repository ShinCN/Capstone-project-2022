package com.gotoubun.weddingvendor.domain.user;

import java.util.Collection;

import javax.persistence.*;

import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vendor")
public class VendorProvider extends BaseEntity{

	@Column(name="company", columnDefinition = "TEXT")
	private String company;

//	@Column(name="single_category_id", columnDefinition = "TEXT")
//	private long singleCategoryId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="vendorProvider", cascade = CascadeType.ALL)
	private Collection<SinglePost> singlePosts;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "single_category_id")
	private SingleCategory singleCategory;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="account_id")
	private Account account;

	@Column(name="nano_password", columnDefinition = "TEXT")
	private String nanoPassword;
	
}
