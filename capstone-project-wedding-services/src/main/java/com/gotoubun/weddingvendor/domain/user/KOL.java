package com.gotoubun.weddingvendor.domain.user;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "kol")
public class KOL extends BaseEntity{
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

}
