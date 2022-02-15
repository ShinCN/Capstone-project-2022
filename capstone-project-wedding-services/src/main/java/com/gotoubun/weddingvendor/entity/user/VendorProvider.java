package com.gotoubun.weddingvendor.entity.user;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gotoubun.weddingvendor.entity.vendor.SinglePost;

import lombok.Data;

@Data
@Entity
@Table(name = "vendor")
public class VendorProvider extends BaseEntity{

	@Column(name="company", columnDefinition = "TEXT")
	private String company;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="vendorProvider", cascade = CascadeType.ALL)
	private Collection<SinglePost> singlePosts;
}
