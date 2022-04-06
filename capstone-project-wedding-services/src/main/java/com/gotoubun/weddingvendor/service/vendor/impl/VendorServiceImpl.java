package com.gotoubun.weddingvendor.service.vendor.impl;

import java.util.Optional;


import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;

import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.service.singlecategory.SingleCategoryService;
import com.gotoubun.weddingvendor.service.vendor.VendorService;
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
        Optional<SingleCategory> singleCategory= singleCategoryService.findById(vendor.getCategoryId());
        String password=generateRandomPassword(10);
        if(checkUserNameExisted(vendor.getUsername()))
        {
            throw new UsernameAlreadyExistsException("username: "+vendor.getUsername()+"already exist");
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
    boolean checkUserNameExisted(String username){
        return accountRepository.findByUsername(username) != null;
    }

    @Override
    public VendorProvider update(VendorProviderRequest vendorProviderRequest) {
        return null;
    }








}
