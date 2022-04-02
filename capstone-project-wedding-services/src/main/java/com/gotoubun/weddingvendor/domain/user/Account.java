package com.gotoubun.weddingvendor.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gotoubun.weddingvendor.domain.vendor.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
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
    @JsonIgnore
    private Date createdDate;

    @Column(name="modified_date")
    @JsonIgnore
    private Date modifiedDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "account")
    @JsonIgnore
    private Admin admin;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "account")
    @JsonIgnore
    private VendorProvider vendorProvider;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "account")
    @JsonIgnore
    private KOL kol;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "account")
    @JsonIgnore
    private Customer customer;

    @PrePersist
    protected void onCreate(){
        this.createdDate = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.modifiedDate = new Date();
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy ="account", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<Comment> comments;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}