package com.gotoubun.weddingvendor.domain.vendor;

import java.util.Collection;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
@Table(name = "package_category")
public class PackageCategory{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="package_name", columnDefinition = "nvarchar(255)")
	private String packageName;

	@Column(name="description",columnDefinition = "nvarchar(255)")
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="packageCategory", cascade = CascadeType.ALL)
	private Collection<PackagePost> packagePosts;
	

}
