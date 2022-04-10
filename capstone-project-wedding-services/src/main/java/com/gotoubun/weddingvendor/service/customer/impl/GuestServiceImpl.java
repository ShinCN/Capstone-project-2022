package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.guest.GuestRequest;
import com.gotoubun.weddingvendor.data.kol.KOLRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.CustomerRepository;
import com.gotoubun.weddingvendor.service.customer.GuestService;
import org.springframework.beans.factory.annotation.Autowired;

import static com.gotoubun.weddingvendor.service.common.GenerateRandomPasswordService.GenerateRandomPassword.generateRandomPassword;

public class GuestServiceImpl implements GuestService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void save(GuestRequest guestRequest) {
//        // TODO Auto-generated method stub
//        Account account = new Account();
//
//        KeyOpinionLeader keyOpinionLeader = new KeyOpinionLeader();
//        String password = generateRandomPassword(10);
//        //check username existed
//        if (checkUserNameExisted(kolRequest.getEmail())) {
//            throw new UsernameAlreadyExistsException("email: " + kolRequest.getEmail() + " already exist");
//        }
//        //save account
//        account.setUsername(kolRequest.getEmail());
//        account.setPassword(bCryptPasswordEncoder.encode(password));
//        account.setRole(4);
//        account.setStatus(0);
//        accountRepository.save(account);
//        //save kol
//        keyOpinionLeader.setAccount(account);
//        keyOpinionLeader.setEmail(kolRequest.getEmail());
//        keyOpinionLeader.setFullName(kolRequest.getFullName());
//        keyOpinionLeader.setPhone(kolRequest.getAddress());
//        keyOpinionLeader.setAddress(kolRequest.getPhone());
//        keyOpinionLeader.setNanoPassword(password);
//        keyOpinionLeader.setDescription(kolRequest.getDescription());
//        kolRepository.save(keyOpinionLeader);
//        return keyOpinionLeader;

    }

    @Override
    public void update(GuestRequest guestRequest) {

    }

    @Override
    public void delete(Long id) {

    }
}
