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

import com.gotoubun.weddingvendor.domain.user.Account;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Data
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//noi dung comment
	@Column(name="content")
	private String content;

	//so luot like
	@Column(name="likes")
	private int likes;

	//1 blog co nhieu comment
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blog_id")
	private Blog blog;

	//1 user co the tao ra nhieu comments
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Account account;

	@Column(name="createdby")
	@CreatedBy
	private String createdBy;

	@Column(name="modifiedby")
	@LastModifiedBy
	private String modifiedBy;
}