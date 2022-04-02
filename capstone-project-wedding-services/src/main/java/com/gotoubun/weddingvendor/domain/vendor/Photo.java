package com.gotoubun.weddingvendor.domain.vendor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Data
@Entity
@Table(name = "photo")
public class Photo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="url")
	private String url;

	@Column(name="caption")
	private String caption;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "single_post_id")
	private SinglePost singlePost;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_id")
	private PackagePost packagePost;

	@Column(name="createdby")
	@CreatedBy
	private String createdBy;

	@Column(name="modifiedby")
	@LastModifiedBy
	private String modifiedBy;
}