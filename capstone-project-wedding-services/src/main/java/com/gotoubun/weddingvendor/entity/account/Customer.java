package com.gotoubun.weddingvendor.entity.account;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity{

}
