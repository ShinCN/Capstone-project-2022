package com.gotoubun.weddingvendor.domain.user;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gotoubun.weddingvendor.domain.vendor.SinglePost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vendor")
public class VendorProvider extends BaseEntity{

	@Column(name="company", columnDefinition = "TEXT")
	private String company;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="vendorProvider", cascade = CascadeType.ALL)
	private Collection<SinglePost> singlePosts;

	public VendorProvider(Long id, String fullName, String username, String password, String phone, String mail,
			String address, Date createdDate, Date modifiedDate) {
		super(id, fullName, username, password, phone, mail, address, 2, createdDate, modifiedDate);
		// TODO Auto-generated constructor stub
	}
	
	
}
