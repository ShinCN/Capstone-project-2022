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
public class Admin extends Auditable{


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="account_id")
	private Account account;
}