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
@ToString
@Table(name = "single_service_post")
public class SinglePost extends BasePost{

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "single_service_name")
	private String serviceName;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendor_id")
	private VendorProvider vendorProvider;


	@ManyToOne
	@JoinColumn(name = "category_id")
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
            joinColumns = @JoinColumn(name = "single_post_id"),
            inverseJoinColumns = @JoinColumn(name = "package_post_id")
    )
    private Collection<PackagePost> packagePosts;

	//0 = not available, 1 = available
	@Column(name="status")
	private Integer status;
}
