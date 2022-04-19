package com.gotoubun.weddingvendor.service.vendor.impl;

import java.util.Optional;


import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderRequest;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;

import com.gotoubun.weddingvendor.exception.PhoneAlreadyExistException;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.exception.VendorNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.service.category.SingleCategoryService;
import com.gotoubun.weddingvendor.service.vendor.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.repository.VendorRepository;
import org.springframework.transaction.annotation.Transactional;

import static com.gotoubun.weddingvendor.service.common.GenerateRandomPasswordService.GenerateRandomPassword.generateRandomString;


@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    SingleCategoryService singleCategoryService;
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
        Optional<SingleCategory> singleCategory = singleCategoryService.findById(vendor.getCategoryId());
        String password = generateRandomString(10);
        if (checkUserNameExisted(vendor.getUsername())) {
            throw new UsernameAlreadyExistsException("username: " + vendor.getUsername() + "already exist");
        }
        if (vendorRepository.findByPhone(vendor.getPhone()) != null) {
            throw new PhoneAlreadyExistException("Phone number already existed");
        }
        //save account
        account.setUsername(vendor.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(password));
        account.setRole(2);
        accountRepository.save(account);
        //save vendor
        vendorProvider.setAccount(account);
        vendorProvider.setNanoPassword(password);
        vendorProvider.setCompany(vendor.getCompanyName());
        vendorProvider.setFullName(vendor.getRepresentative());
        vendorProvider.setPhone(vendor.getPhone());
        vendorProvider.setEmail(vendor.getUsername());
        vendorProvider.setSingleCategory(singleCategory.get());
        vendorProvider.setAddress(vendor.getAddress());
        vendorRepository.save(vendorProvider);

        return vendorProvider;
    }

    boolean checkUserNameExisted(String username) {
        return accountRepository.findByUsername(username) != null;
    }

    @Override
    public VendorProvider update(VendorProviderRequest vendorProviderRequest) {
        return null;
    }


    @Override
    public VendorProviderResponse getByUsername(String username) {

        Account account = accountRepository.findByUsername(username);
        VendorProvider vendorProvider = vendorRepository.findByAccount(account);
        VendorProviderResponse vendorResponse = VendorProviderResponse.builder()
                .username(vendorProvider.getAccount().getUsername())
                .status(vendorProvider.getAccount().isStatus())
                .createdDate(vendorProvider.getAccount().getCreatedDate())
                .modifiedDate(vendorProvider.getAccount().getModifiedDate())
                .fullName(vendorProvider.getFullName())
                .phone(vendorProvider.getPhone())
                .address(vendorProvider.getAddress())
                .company(vendorProvider.getCompany())
                .nanoPassword(vendorProvider.getNanoPassword())
                .build();
       if(vendorResponse == null)
       {
           throw new VendorNotFoundException("vendor is not found");
       }
        return vendorResponse;
    }
}
