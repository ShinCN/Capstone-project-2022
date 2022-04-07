package com.gotoubun.weddingvendor.domain.vendor;

import java.util.Collection;

import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

@Data
@Entity
@Table(name = "package_category")
public class PackageCategory{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="package_name")
	private String packageName;

	@Lob
	@Nationalized
	@Column(name="description")
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="packageCategory", cascade = CascadeType.ALL)
	private Collection<PackagePost> packagePosts;
	

}
