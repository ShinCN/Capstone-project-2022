package com.gotoubun.weddingvendor.domain.user;

import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "kol")
public class KOL extends Auditable{
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    @Column(name="nano_password", columnDefinition = "TEXT")
    private String nanoPassword;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="kol", cascade = CascadeType.ALL)
    private Collection<PackagePost> packagePosts;
}