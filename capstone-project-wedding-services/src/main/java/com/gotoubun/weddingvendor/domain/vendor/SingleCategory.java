package com.gotoubun.weddingvendor.domain.vendor;

import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

@Data
@Entity
@Table(name = "single_category")
public class SingleCategory{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="single_category_id")
	private Long id;

	@Column(name="category_name")
	private String categoryName;

	@Lob
	@Nationalized
	@Column(name="description")
	private String description;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="singleCategory", cascade = CascadeType.ALL)
	private Collection<SinglePost> singlePosts;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="singleCategory", cascade = CascadeType.ALL)
	private Collection<VendorProvider> vendorProviders;
}