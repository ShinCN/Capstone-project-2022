package com.gotoubun.weddingvendor.domain.vendor;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "package_category")
public class PackageCategory{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="package_name")
	private String packageName;

	@Column(name="description")
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="packageCategory", cascade = CascadeType.ALL)
	private Collection<PackagePost> packagePosts;
	

}
