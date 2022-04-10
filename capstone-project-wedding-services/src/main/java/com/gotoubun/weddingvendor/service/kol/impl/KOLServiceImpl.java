package com.gotoubun.weddingvendor.service.kol.impl;

import com.gotoubun.weddingvendor.data.kol.KOLRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.exception.PhoneAlreadyExistException;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.KolRepository;
import com.gotoubun.weddingvendor.service.kol.KOLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.gotoubun.weddingvendor.service.common.GenerateRandomPasswordService.GenerateRandomPassword.generateRandomPassword;


@Service
public class KOLServiceImpl implements KOLService {

    @Autowired
    KolRepository kolRepository;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AccountRepository accountRepository;
    @Override
    public KeyOpinionLeader save(KOLRequest kolRequest) {
        // TODO Auto-generated method stub
        Account account = new Account();
        KeyOpinionLeader keyOpinionLeader = new KeyOpinionLeader();
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
        //save kol

        keyOpinionLeader.setAccount(account);
        keyOpinionLeader.setNanoPassword(kolRequest.getPassword());
        keyOpinionLeader.setEmail(kolRequest.getEmail());
        keyOpinionLeader.setFullName(kolRequest.getFullName());
        keyOpinionLeader.setPhone(kolRequest.getAddress());
        keyOpinionLeader.setAddress(kolRequest.getPhone());
        keyOpinionLeader.setDescription(kolRequest.getDescription());
        kolRepository.save(keyOpinionLeader);
        return keyOpinionLeader;

    }

    @Override
    public KeyOpinionLeader update(KOLRequest kolRequest, String username) {
        Account account = accountRepository.findByUsername(username);

        //check username existed
        if(checkUserNameExisted(kolRequest.getEmail()))
        {
            throw new UsernameAlreadyExistsException("email: "+kolRequest.getEmail()+" already exist");
        }
        KeyOpinionLeader keyOpinionLeader = kolRepository.findByAccount(account);
        keyOpinionLeader.getAccount().setUsername(kolRequest.getEmail());
        keyOpinionLeader.getAccount().setPassword(bCryptPasswordEncoder.encode(kolRequest.getPassword()));
        keyOpinionLeader.setEmail(kolRequest.getEmail());
        keyOpinionLeader.setFullName(kolRequest.getFullName());
        for(Account acc : accountRepository.findAll()){
            if(acc.getKeyOpinionLeader().getPhone() != kolRequest.getPhone()
                    && acc.getVendorProvider().getPhone() != kolRequest.getPhone()
                    && acc.getCustomer().getPhone() != kolRequest.getPhone()){
                keyOpinionLeader.setPhone(kolRequest.getPhone());
            }else{
                throw new PhoneAlreadyExistException("This phone was registered! Input another one.");
            }
        }
        keyOpinionLeader.setAddress(kolRequest.getAddress());
        keyOpinionLeader.setDescription(kolRequest.getDescription());
        kolRepository.save(keyOpinionLeader);
        return keyOpinionLeader;
    }

    boolean checkUserNameExisted(String username){
        return accountRepository.findByUsername(username) != null;
    }
}
