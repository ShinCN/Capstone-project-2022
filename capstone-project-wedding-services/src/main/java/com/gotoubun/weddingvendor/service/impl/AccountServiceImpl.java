package com.gotoubun.weddingvendor.service.impl;

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
	
	@Override
	public List<Account> findAll() {
		List<Account> accounts= accountRepository.findAll();
		return accounts;
	}

}
