package com.gotoubun.weddingvendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotoubun.weddingvendor.entity.account.Account;
import com.gotoubun.weddingvendor.service.AccountService;

@RestController
@RequestMapping("/api")
public class AccountController {
	@Autowired
	private AccountService accountService;

	@GetMapping("/accounts")
	public List<Account> getAll() {
		return accountService.findAll();
	}
}
