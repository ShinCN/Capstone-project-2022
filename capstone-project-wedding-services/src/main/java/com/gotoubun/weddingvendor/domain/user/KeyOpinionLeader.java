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

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="keyOpinionLeader", cascade = CascadeType.ALL)
    private Collection<PackagePost> packagePosts;

    @Column(name="description")
    private String description;
}


