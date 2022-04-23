package com.gotoubun.weddingvendor.domain.vendor;

import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "single_category")
public class SingleCategory{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="category_name")
	private String categoryName;

	@Column(name="description")
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="singleCategory", cascade = CascadeType.ALL)
	private Collection<SinglePost> singlePosts;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="singleCategory", cascade = CascadeType.ALL)
	private Collection<VendorProvider> vendorProviders;
}