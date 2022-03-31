package com.gotoubun.weddingvendor.domain.user;

import java.util.Collection;

import javax.persistence.*;

import com.gotoubun.weddingvendor.domain.vendor.PackagePost;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "admin")
public class Admin extends BaseEntity {


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "admin", cascade = CascadeType.ALL)
	private Collection<PackagePost> packagePosts;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="account_id")
	private Account account;
}
