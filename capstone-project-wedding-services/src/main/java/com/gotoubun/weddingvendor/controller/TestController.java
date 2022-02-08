package com.gotoubun.weddingvendor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gotoubun.weddingvendor.service.AccountService;

@Controller
@RequestMapping(value = "hello-controller")
public class TestController {
	@Autowired
	private AccountService accountService;

	@GetMapping(value = "hello")
	public void hello() {
		accountService.findAll();
	}
}
