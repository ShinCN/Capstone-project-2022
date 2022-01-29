package com.gotoubun.weddingvendor.entity.post;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gotoubun.weddingvendor.entity.account.Vendor;

import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@Table(name = "single_service_post")
public class Post extends BasePost{
	
	@Id
	@Column(name = "post_id")
	@NonNull
	private String postID;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;

	@OneToOne
	@JoinColumn(name = "category_id", nullable = false)
	private ServiceCategory serviceCategory;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="post", cascade = CascadeType.ALL)
	private Collection<Photo> photos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="post", cascade = CascadeType.ALL)
	private Collection<Comment> comments;
	
	
}
