package com.gotoubun.weddingvendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gotoubun.weddingvendor.entity.user.Admin;
import com.gotoubun.weddingvendor.repository.AdminRepository;
import com.gotoubun.weddingvendor.service.AdminService;


@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public List<Admin> findAll() {
		List<Admin> admins= adminRepository.findAll();
		return admins;
	}
	
}
