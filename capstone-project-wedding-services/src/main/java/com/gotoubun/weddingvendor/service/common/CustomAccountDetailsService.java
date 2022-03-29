package com.gotoubun.weddingvendor.service.common;

import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
public class CustomAccountDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) throw new UsernameNotFoundException("User not found");
        return account;
    }

    @Transactional
    public Account loadUserById(Long id) {
        Account account = accountRepository.getById(id);
        if (account == null) throw new UsernameNotFoundException("User not found");
        return account;

    }




}
