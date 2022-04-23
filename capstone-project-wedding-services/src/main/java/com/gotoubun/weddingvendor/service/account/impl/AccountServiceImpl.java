package com.gotoubun.weddingvendor.service.account.impl;

import com.gotoubun.weddingvendor.data.account.AccountPasswordRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.exception.PasswordNotMatchException;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.KolRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.repository.VendorRepository;
import com.gotoubun.weddingvendor.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private SinglePostRepository singlePostRepository;

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
            account.setStatus(true);
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword

            return accountRepository.save(account);

        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + account.getUsername() + "' already exists");
        }
    }

    public Account findByUserName(String username) {
        return Optional.ofNullable(accountRepository.findByUsername(username)).orElseThrow(() -> new ResourceNotFoundException("username is not found"));
    }

    public Optional<Account> findByUserNameForFaceBook(String username) {
        return Optional.ofNullable(accountRepository.findByUsername(username));
    }

    public int getRole(String username) {
        return findByUserName(username).getRole();
    }

    @Override
    public String getFullName(Account account) {
        int role = account.getRole();
        String fullName;
        if (role == 1) {
            fullName = Optional.ofNullable(account.getAdmin().getFullName()).orElseThrow(()->new ResourceNotFoundException(""));
        } else if (role == 2) {
            fullName = Optional.ofNullable(account.getVendorProvider().getFullName()).orElseThrow(()->new ResourceNotFoundException(""));
        } else if (role == 3) {
            fullName = Optional.ofNullable(account.getCustomer().getFullName()).orElseThrow(()->new ResourceNotFoundException(""));
        } else if (role == 4) {
            fullName = Optional.ofNullable(account.getKeyOpinionLeader().getFullName()).orElseThrow(()->new ResourceNotFoundException(""));
        }  else {
            fullName = "????";
        }
        return fullName;
    }

    public boolean getStatus(String username) {
        return findByUserName(username).isStatus();
    }

    @Override
    public void updatePassword(AccountPasswordRequest password, String username) {
        Account account = accountRepository.findByUsername(username);
        boolean isPasswordMatch = bCryptPasswordEncoder.matches(password.getOldPassword(), account.getPassword());
        if (!isPasswordMatch) {
            throw new PasswordNotMatchException("Password does not match");
        }
        account.setPassword(bCryptPasswordEncoder.encode(password.getNewPassword()));
        accountRepository.save(account);
    }

}
