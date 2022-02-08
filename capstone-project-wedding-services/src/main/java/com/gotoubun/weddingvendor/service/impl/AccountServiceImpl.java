package com.gotoubun.weddingvendor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gotoubun.weddingvendor.entity.account.Account;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountRepository accountRepository;
	static List<Account> listAccount=new ArrayList<>();
	static {
	
		listAccount.add(new Account("huy","123456","admin"));
		listAccount.add(new Account("quan","123456","admin"));
	}
	
	@Override
	public List<Account> findAll() {
//		for (Account account : listAccount) {
//			accountRepository.save(account);
//		}
		return accountRepository.findAll();
	}

}
