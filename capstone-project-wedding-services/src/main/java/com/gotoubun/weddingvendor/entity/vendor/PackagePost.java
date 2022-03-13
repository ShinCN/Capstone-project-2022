package com.gotoubun.weddingvendor.entity.vendor;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gotoubun.weddingvendor.entity.user.Admin;
import com.gotoubun.weddingvendor.entity.user.Customer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "package_service_post")
@Getter
@Setter
@Accessors(fluent = true)
public class PackagePost extends BasePost{
	@Id
	@Column(name = "package_post_id")
	@NonNull
	private String packageID;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private PackageCategory packageCategory;
	
	@ManyToOne
	@JoinColumn(name = "admin_id",nullable = false)
	private Admin admin;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="packagePost", cascade = CascadeType.ALL)
	private Collection<Photo> photos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="packagePost", cascade = CascadeType.ALL)
	private Collection<Comment> comments;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "package_chosen_list",
            joinColumns = @JoinColumn(name = "package_post_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Collection<Customer> customers;

	
	@ManyToMany(mappedBy = "packagePosts")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<SinglePost> singlePosts;
}
