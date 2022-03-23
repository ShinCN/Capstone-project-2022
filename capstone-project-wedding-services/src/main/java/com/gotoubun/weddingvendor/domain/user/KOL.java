package com.gotoubun.weddingvendor.domain.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "kol")
public class KOL extends BaseEntity{
    @OneToOne
    @JoinColumn(name="account_id")
    private Account account;
}
