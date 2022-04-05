package com.gotoubun.weddingvendor.service.impl.account;

import com.gotoubun.weddingvendor.data.admin.AccountStatusRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public Account save(Account account) {
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
    public Account updateStatus(AccountStatusRequest accountStatusRequest, String username) {
        Account account = accountRepository.findByUsername(username);
        account.setStatus(accountStatusRequest.getStatus());
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findByUserName(String username){
        return Optional.ofNullable(accountRepository.findByUsername(username)) ;
    }
    public int getRole(String username)
    {
        return  findByUserName(username).get().getRole();
    }


}