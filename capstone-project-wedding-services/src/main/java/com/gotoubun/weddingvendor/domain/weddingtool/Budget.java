package com.gotoubun.weddingvendor.domain.weddingtool;

import com.gotoubun.weddingvendor.domain.user.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "budget")
public class Budget {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="budget", cascade = CascadeType.ALL)
	private Collection<BudgetCategory> budgetCategories;
}
