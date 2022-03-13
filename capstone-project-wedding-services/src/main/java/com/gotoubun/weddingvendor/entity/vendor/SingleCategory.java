package com.gotoubun.weddingvendor.entity.vendor;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "single_category")
public class SingleCategory extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="category_name")
	private String categoryName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="singleCategory", cascade = CascadeType.ALL)
	private Collection<SinglePost> singlePosts;
}
