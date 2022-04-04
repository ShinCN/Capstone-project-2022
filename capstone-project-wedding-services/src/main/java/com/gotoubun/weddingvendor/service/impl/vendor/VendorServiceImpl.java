package com.gotoubun.weddingvendor.service.impl.vendor;

import java.util.Optional;


import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;

import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.service.IService;
import com.gotoubun.weddingvendor.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.repository.VendorRepository;
import org.springframework.transaction.annotation.Transactional;

import static com.gotoubun.weddingvendor.service.common.GenerateRandomPasswordService.GenerateRandomPassword.generateRandomPassword;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	IService<SingleCategory> singleCategoryIService;
	@Autowired
	VendorRepository vendorRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	SinglePostRepository singlePostRepository;
	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;


	@Override
	@Transactional
	public VendorProvider save(VendorProviderRequest vendor) {
		// TODO Auto-generated method stub
			Account account = new Account();
			VendorProvider vendorProvider = new VendorProvider();
			String password=generateRandomPassword(10);

			Optional<SingleCategory> singleCategory = singleCategoryIService.findById(vendor.getCategoryId());
			//save account
			account.setUsername(vendor.getUsername());
			account.setPassword(bCryptPasswordEncoder.encode(password));
			account.setRole(2);
			accountRepository.save(account);
			//save vendor
			vendorProvider.setAccount(account);
			vendorProvider.setNanoPassword(password);
			vendorProvider.setFullName(vendor.getRepresentative());
			vendorProvider.setCompany(vendor.getCompanyName());
			vendorProvider.setMail(vendor.getEmail());
			vendorProvider.setPhone(vendor.getPhone());
			vendorProvider.setSingleCategory(singleCategory.get());
			vendorProvider.setAddress(vendor.getAddress());
			vendorRepository.save(vendorProvider);

			return vendorProvider;
	}

	@Override
	public VendorProvider update(VendorProviderRequest vendor) {
		Account account = accountRepository.findByUsername(vendor.getUsername());
		VendorProvider vendorProvider = vendorRepository.findByAccount(account);

			//save vendor
			vendorProvider.setFullName(vendor.getRepresentative());
			vendorProvider.setCompany(vendor.getCompanyName());
			vendorProvider.setMail(vendor.getEmail());
			vendorProvider.setPhone(vendor.getPhone());
			vendorProvider.setAddress(vendor.getAddress());

			vendorRepository.save(vendorProvider);

		return vendorProvider;
	}

}