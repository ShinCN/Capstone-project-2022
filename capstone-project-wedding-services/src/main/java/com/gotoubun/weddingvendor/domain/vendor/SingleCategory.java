package com.gotoubun.weddingvendor.domain.vendor;

import java.util.Collection;

import javax.persistence.*;

import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import lombok.Data;

@Data
@Entity
@Table(name = "single_category")
public class SingleCategory extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="single_category_id")
	private Long id;
	
	@Column(name="category_name")
	private String categoryName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="singleCategory", cascade = CascadeType.ALL)
	private Collection<SinglePost> singlePosts;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="singleCategory", cascade = CascadeType.ALL)
	private Collection<VendorProvider> vendorProviders;
}
