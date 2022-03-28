package com.gotoubun.weddingvendor.domain.user;

import com.gotoubun.weddingvendor.domain.vendor.Comment;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "account")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    //admin=1,vendor=2,customer=3,kol=4
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

