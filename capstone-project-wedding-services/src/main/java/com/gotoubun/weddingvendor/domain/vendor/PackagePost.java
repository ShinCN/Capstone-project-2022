package com.gotoubun.weddingvendor.domain.vendor;

import java.util.Collection;

import javax.persistence.*;

import com.gotoubun.weddingvendor.domain.user.Admin;
import com.gotoubun.weddingvendor.domain.user.Customer;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postID;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private PackageCategory packageCategory;
	
	@ManyToOne
	@JoinColumn(name = "admin_id",nullable = false)
	private Admin admin;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="packagePost", cascade = CascadeType.ALL)
	private Collection<Photo> photos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="packagePost", cascade = CascadeType.ALL)
	private Collection<Feedback> feedbacks;
	
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
