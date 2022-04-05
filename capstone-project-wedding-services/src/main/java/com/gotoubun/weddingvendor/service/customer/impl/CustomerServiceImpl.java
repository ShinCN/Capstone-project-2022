package com.gotoubun.weddingvendor.service.customerRequest.impl;

import com.gotoubun.weddingvendor.data.CustomerRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Override
    @Transactional
    public Customer save(CustomerRequest customerRequest) {
        // TODO Auto-generated method stub
        Account account = new Account();
        Customer guest = new Customer();

       //check username existed
        if(checkUserNameExisted(customerRequest.getEmail()))
        {
            throw new UsernameAlreadyExistsException("email: "+customerRequest.getEmail()+" already exist");
        }
        //save account
        account.setUsername(customerRequest.getEmail());
        account.setPassword(bCryptPasswordEncoder.encode(customerRequest.getPassword()));
        account.setRole(3);
        accountRepository.save(account);
        //save vendor
        guest.setAccount(account);
        guest.setAddress(customerRequest.getAddress());
        guest.setPlanningDate(LocalDate.parse(customerRequest.getWeddingDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        
        return guest;
    }
    boolean checkUserNameExisted(String username){
        return accountRepository.findByUsername(username) != null;
    }

}
