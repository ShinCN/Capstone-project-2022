package com.gotoubun.weddingvendor.domain.user;

import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "kol")
public class KeyOpinionLeader extends Auditable{
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    @Column(name="avatar_url", columnDefinition = "varchar(255)")
    private String avatarUrl;


    @Column(name="nano_password", columnDefinition = "TEXT")
    private String nanoPassword;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="keyOpinionLeader", cascade = CascadeType.ALL)
    private Collection<PackagePost> packagePosts;

    @Lob
    @Nationalized
    @Column(name="description")
    private String description;
}


