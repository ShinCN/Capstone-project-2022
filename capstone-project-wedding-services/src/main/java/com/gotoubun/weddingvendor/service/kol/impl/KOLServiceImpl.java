package com.gotoubun.weddingvendor.service.kol.impl;

import com.gotoubun.weddingvendor.data.KOLRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.user.KOL;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.service.kol.KOLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class KOLServiceImpl implements KOLService {

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AccountRepository accountRepository;
    @Override
    public KOL save(KOLRequest kolRequest) {
        // TODO Auto-generated method stub
        Account account = new Account();
        KOL kol = new KOL();

        //check username existed
        if(checkUserNameExisted(kolRequest.getEmail()))
        {
            throw new UsernameAlreadyExistsException("email: "+kolRequest.getEmail()+" already exist");
        }
        //save account
        account.setUsername(kolRequest.getEmail());
        account.setPassword(bCryptPasswordEncoder.encode(kolRequest.getPassword()));
        account.setRole(3);
        accountRepository.save(account);
        //save vendor
        kol.setAccount(account);
        kol.setAddress(kolRequest.getAddress());
        kol.setDescription(kolRequest.getDescription());

        return kol;
    }
    boolean checkUserNameExisted(String username){
        return accountRepository.findByUsername(username) != null;
    }
}
