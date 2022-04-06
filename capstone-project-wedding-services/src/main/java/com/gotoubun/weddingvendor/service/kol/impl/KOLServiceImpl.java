package com.gotoubun.weddingvendor.service.kol.impl;

import com.gotoubun.weddingvendor.data.kol.KOLRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KOL;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.KolRepository;
import com.gotoubun.weddingvendor.service.kol.KOLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class KOLServiceImpl implements KOLService {

    @Autowired
    KolRepository kolRepository;

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
        account.setRole(4);
        account.setStatus(0 );
        accountRepository.save(account);
        //save vendor
        kol.setAccount(account);
        kol.setEmail(kolRequest.getEmail());
        kol.setFullName(kolRequest.getFullName());
        kol.setAddress(kolRequest.getAddress());
        kol.setDescription(kolRequest.getDescription());
        kolRepository.save(kol);
        return kol;
    }
    boolean checkUserNameExisted(String username){
        return accountRepository.findByUsername(username) != null;
    }
}
