package com.gotoubun.weddingvendor.service.impl.account;

import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KOL;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.AdminRepository;
import com.gotoubun.weddingvendor.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Service
public class AccountServiceImpl implements IService<Account> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Collection<Account> findAll() {
        return null;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Account saveOrUpdate(Account account) {
        try{
            account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
            //Username has to be unique (exception)
            account.setUsername(account.getUsername());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword

            return accountRepository.save(account);

        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+account.getUsername()+"' already exists");
        }
    }

    @Override
    public void deleteById(Long id) {

    }

}
