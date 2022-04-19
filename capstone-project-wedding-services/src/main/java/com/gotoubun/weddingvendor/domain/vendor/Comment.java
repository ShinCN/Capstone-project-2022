package com.gotoubun.weddingvendor.domain.vendor;

import com.gotoubun.weddingvendor.domain.user.Account;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//noi dung comment
	@Nationalized
	@Column(name="content")
	private String content;

//	//so luot like
//	@Column(name="likes")
//	private int likes;

	//1 blog co nhieu comment
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "blog_id")
	private Blog blog;

	//1 user co the tao ra nhieu comments
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private Account account;

	@Column(name="createdby")
	@CreatedBy
	private String createdBy;

	@Column(name="modifiedby")
	@LastModifiedBy
	private String modifiedBy;
}