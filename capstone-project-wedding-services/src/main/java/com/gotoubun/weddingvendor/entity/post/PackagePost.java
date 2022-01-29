package com.gotoubun.weddingvendor.entity.post;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@Table(name = "package_service_post")
public class PackagePost extends BasePost{
	@Id
	@Column(name = "package_post_id")
	@NonNull
	private String packageID;

	@OneToOne
	@JoinColumn(name = "category_id", nullable = false)
	private PackageServiceCategory packageCategory;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="packagePost", cascade = CascadeType.ALL)
	private Collection<Photo> photos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="packagePost", cascade = CascadeType.ALL)
	private Collection<Comment> comments;
}
