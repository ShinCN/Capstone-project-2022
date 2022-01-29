package com.gotoubun.weddingvendor.entity.account;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gotoubun.weddingvendor.entity.post.Post;

import lombok.Data;

@Data
@Entity
@Table(name = "vendor")
public class Vendor extends BaseEntity{

	@Column(name="company", columnDefinition = "TEXT")
	private String company;
	
	@Column(name="category_id")
	private String categoryID;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="vendor", cascade = CascadeType.ALL)
	private Collection<Post> posts;
}
