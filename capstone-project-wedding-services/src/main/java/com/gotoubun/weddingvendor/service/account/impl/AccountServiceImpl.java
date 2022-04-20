package com.gotoubun.weddingvendor.service.account.impl;

import com.gotoubun.weddingvendor.data.account.AccountPasswordRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.exception.PasswordNotMatchException;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.KolRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.repository.VendorRepository;
import com.gotoubun.weddingvendor.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            account.setStatus(1);
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword

            return accountRepository.save(account);

        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + account.getUsername() + "' already exists");
        }
    }

    public Optional<Account> findByUserName(String username) {
        return Optional.ofNullable(accountRepository.findByUsername(username));
    }

    public int getRole(String username) {
        if(!findByUserName(username).isPresent())
        {
            throw new UsernameNotFoundException("Username"+username+"is not found ");
        }
        return findByUserName(username).get().getRole();
    }

    public int getStatus(String username) {
        if(!findByUserName(username).isPresent())
        {
            throw new UsernameNotFoundException("Username"+username+"is not found ");
        }
        return findByUserName(username).get().getStatus();
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
