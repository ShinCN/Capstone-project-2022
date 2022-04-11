package com.gotoubun.weddingvendor.service.account.impl;

import com.gotoubun.weddingvendor.data.account.AccountPasswordRequest;
import com.gotoubun.weddingvendor.data.admin.AccountStatusRequest;
import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.exception.PasswordNotMatchException;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.KolRepository;
import com.gotoubun.weddingvendor.repository.VendorRepository;
import com.gotoubun.weddingvendor.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private KolRepository kolRepository;

    @Override
    public Account save(Account account) {
        try {
            account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
            //Username has to be unique (exception)
            account.setUsername(account.getUsername());
            account.setRole(1);
            account.setStatus(1);
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword

            return accountRepository.save(account);

        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + account.getUsername() + "' already exists");
        }
    }

    @Override
    public Account updateStatus(Long id, AccountStatusRequest accountStatusRequest) {
        Account account = accountRepository.getById(id);
        account.setStatus(accountStatusRequest.getStatus());
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findByUserName(String username) {
        return Optional.ofNullable(accountRepository.findByUsername(username));
    }

    public int getRole(String username) {
        return findByUserName(username).get().getRole();
    }

    public int getStatus(String username) {
        return findByUserName(username).get().getStatus();
    }

    @Override
    public Collection<VendorProviderResponse> findAllVendor() {
        List<Account> vendorAccounts = (List<Account>) accountRepository.findByRole(2);
        List<VendorProvider> vendors = new ArrayList<>();
        vendorAccounts.forEach(c -> {
            vendors.add(vendorRepository.findByAccount(c));
        });
        List<VendorProviderResponse> vendorResponses = new ArrayList<>();
        vendors.forEach(c -> {
                    VendorProviderResponse vendorResponse = VendorProviderResponse.builder()
                            .id(c.getId())
                            .username(c.getAccount().getUsername())
                            .status(c.getAccount().getStatus())
                            .createdDate(c.getAccount().getCreatedDate())
                            .modifiedDate(c.getAccount().getModifiedDate())
                            .fullName(c.getFullName())
                            .phone(c.getPhone())
                            .address(c.getAddress())
                            .company(c.getCompany())
                            .nanoPassword(c.getNanoPassword())
                            .build();
                    vendorResponses.add(vendorResponse);
                }
        );

        return vendorResponses;
    }

    @Override
    public Collection<KOLResponse> findAllKOL() {

        List<KeyOpinionLeader> keyOpinionLeaders = kolRepository.findAll();

        return keyOpinionLeaders.stream().map(c -> KOLResponse.builder()
                        .id(c.getId())
                        .username(c.getAccount().getUsername())
                        .status(c.getAccount().getStatus())
                        .createdDate(c.getAccount().getCreatedDate())
                        .modifiedDate(c.getAccount().getModifiedDate())
                        .fullName(c.getFullName())
                        .phone(c.getPhone())
                        .address(c.getAddress())
                        .description(c.getDescription())
                        .nanoPassword(c.getNanoPassword())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void updatePassword(AccountPasswordRequest passWord, String username) {
        Account account = accountRepository.findByUsername(username);
        if (!account.getPassword().equals(passWord.getOldPassword())) {
            throw new PasswordNotMatchException("Password does not match");
        }
        account.setPassword(passWord.getNewPassword());
    }


}
