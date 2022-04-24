package com.gotoubun.weddingvendor.service.vendor.impl;

import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderRequest;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import com.gotoubun.weddingvendor.exception.PhoneAlreadyExistException;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.repository.VendorRepository;
import com.gotoubun.weddingvendor.service.category.SingleCategoryService;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.vendor.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
    @Autowired
    GetCurrentDate getCurrentDate;
    @Override
    @Transactional
    public void save(VendorProviderRequest vendor) {
        // TODO Auto-generated method stub
        Account account = new Account();
        VendorProvider vendorProvider = new VendorProvider();
        SingleCategory singleCategory =singleCategoryService.findById(vendor.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Single Category is not found"));
        String password = generateRandomString(10);

        if (checkUserNameExisted(vendor.getUsername())) {
            throw new UsernameAlreadyExistsException("username: " + vendor.getUsername() + "already exist");
        }

        //save account
        account.setUsername(vendor.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(password));
        account.setRole(2);
        accountRepository.save(account);

        if (checkPhoneExisted(vendor.getPhone())) {
            throw new PhoneAlreadyExistException("Phone number already existed");
        }
        //save vendor
        vendorProvider.setAccount(account);
        vendorProvider.setNanoPassword(password);
        vendorProvider.setCompany(vendor.getCompanyName());
        vendorProvider.setFullName(vendor.getRepresentative());
        vendorProvider.setPhone(vendor.getPhone());
        vendorProvider.setEmail(vendor.getUsername());
        vendorProvider.setSingleCategory(singleCategory);
        vendorProvider.setAddress(vendor.getAddress());
        vendorRepository.save(vendorProvider);


    }

    boolean checkUserNameExisted(String username) {
        return accountRepository.findByUsername(username) != null;
    }

    @Override
    public void update(VendorProviderRequest vendorProviderRequest, String username) {
        VendorProvider vendorProvider = vendorRepository
                .findByAccount(accountRepository.findByUsername(username));

        //check phone existed
        if (vendorProvider.getPhone().equals(vendorProviderRequest.getPhone()) ||
                !vendorProvider.getPhone().equals(vendorProviderRequest.getPhone())
                        && !checkPhoneExisted(vendorProviderRequest.getPhone())) {
            vendorRepository.save(mapToEntity(vendorProviderRequest, vendorProvider));

        } else if (checkPhoneExisted(vendorProvider.getPhone())) {
            throw new PhoneAlreadyExistException("phone: " + vendorProvider.getPhone() + " already existed");
        }
    }

    private VendorProvider mapToEntity(VendorProviderRequest vendorProviderRequest, VendorProvider vendorProvider) {

        vendorProvider.setFullName(vendorProviderRequest.getRepresentative());
        vendorProvider.setPhone(vendorProviderRequest.getPhone());
        vendorProvider.setAddress(vendorProviderRequest.getAddress());
        vendorProvider.setCompany(vendorProviderRequest.getCompanyName());
        vendorProvider.getAccount().setModifiedDate(getCurrentDate.now());

        return vendorProvider;
    }
    boolean checkPhoneExisted(String phone) {
        return vendorRepository.findByPhone(phone) != null;
    }

    @Override
    public VendorProviderResponse load(String username) {

        Account account = accountRepository.findByUsername(username);
        VendorProvider vendorProvider = vendorRepository.findByAccount(account);
        return VendorProviderResponse.builder()
                .id(vendorProvider.getId())
                .username(vendorProvider.getAccount().getUsername())
                .createdDate(vendorProvider.getAccount().getCreatedDate())
                .modifiedDate(vendorProvider.getAccount().getModifiedDate())
                .fullName(vendorProvider.getFullName())
                .phone(vendorProvider.getPhone())
                .address(vendorProvider.getAddress())
                .company(vendorProvider.getCompany())
                .build();

    }
}
