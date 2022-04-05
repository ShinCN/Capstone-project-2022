package com.gotoubun.weddingvendor.domain.vendor;

import java.util.Collection;
import javax.persistence.*;
import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.user.KOL;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "package_service_post")

@Accessors(fluent = true)
public class PackagePost extends BasePost{
	@Id
	@Column(name = "package_post_id")
	@NonNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private PackageCategory packageCategory;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kol_id")
	private KOL kol;
	
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

	@Column(name="createdby")
	@CreatedBy
	private String createdBy;

	@Column(name="modifiedby")
	@LastModifiedBy
	private String modifiedBy;
}
