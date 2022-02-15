package com.gotoubun.weddingvendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotoubun.weddingvendor.entity.user.Admin;
import com.gotoubun.weddingvendor.service.AdminService;

@RestController
@RequestMapping("/api")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/admins")
	public List<Admin> getAll() {
		return adminService.findAll();
	}

}
