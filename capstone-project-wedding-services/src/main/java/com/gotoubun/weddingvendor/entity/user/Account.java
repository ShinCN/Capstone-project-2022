package com.gotoubun.weddingvendor.entity.user;

import com.gotoubun.weddingvendor.entity.vendor.Comment;
import com.gotoubun.weddingvendor.entity.vendor.SinglePost;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    //admin=1,vendor=2,customer=3
    @Column(name="role")
    private int role;

    @Column(name="created_date")
    @CreatedDate
    private Date createdDate;

    @Column(name="modified_date")
    @LastModifiedDate
    private Date modifiedDate;

    @OneToOne(mappedBy = "account")
    private Admin admin;

    @OneToOne(mappedBy = "account")
    private VendorProvider vendorProvider;

    @OneToOne(mappedBy = "account")
    private KOL kol;

    @OneToOne(mappedBy = "account")
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="account", cascade = CascadeType.ALL)
    private Collection<Comment> comments;
}

