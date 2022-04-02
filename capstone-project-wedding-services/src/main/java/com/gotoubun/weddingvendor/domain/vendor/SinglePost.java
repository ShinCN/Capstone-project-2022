package com.gotoubun.weddingvendor.domain.vendor;

import java.util.Collection;

import javax.persistence.*;

import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "single_service_post")
public class SinglePost extends BasePost{

	@Id
	@Column(name = "single_post_id")
	@NonNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendor_id")
	private VendorProvider vendorProvider;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private SingleCategory singleCategory;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="singlePost", cascade = CascadeType.ALL)
	private Collection<Photo> photos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="singlePost", cascade = CascadeType.ALL)
	private Collection<Feedback> feedbacks;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "single_chosen_list",
            joinColumns = @JoinColumn(name = "single_post_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Collection<Customer> customers;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "single_post_list_in_pack",
            joinColumns = @JoinColumn(name = "package_post_id"),
            inverseJoinColumns = @JoinColumn(name = "single_post_id")
    )
    private Collection<PackagePost> packagePosts;


	@Column(name="createdby")
	@CreatedBy
	private String createdBy;

	@Column(name="modifiedby")
	@LastModifiedBy
	private String modifiedBy;
}
