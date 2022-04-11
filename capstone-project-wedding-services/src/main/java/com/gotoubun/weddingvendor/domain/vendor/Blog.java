package com.gotoubun.weddingvendor.domain.vendor;

import com.gotoubun.weddingvendor.domain.user.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "blog")
public class Blog extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(name="title")
    private String title;

    @Lob
    @Nationalized
    @Column(name="content")
    private String content;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account account;

    @Column(name="createdby")
    @CreatedBy
    private String createdBy;

    @Column(name="modifiedby")
    @LastModifiedBy
    private String modifiedBy;
}