package com.gotoubun.weddingvendor.entity.user;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gotoubun.weddingvendor.entity.vendor.PackagePost;

import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class Admin extends BaseEntity {
	public Admin(Long id, String fullName, String username, String password, String phone, String mail, String address,
			Date createdDate, Date modifiedDate) {
		super(id, fullName, username, password, phone, mail, address, 1, createdDate, modifiedDate);
		// TODO Auto-generated constructor stub
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "admin", cascade = CascadeType.ALL)
	private Collection<PackagePost> packagePosts;

}