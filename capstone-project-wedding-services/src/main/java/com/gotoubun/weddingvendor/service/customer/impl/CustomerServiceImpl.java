package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.customer.CustomerRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.weddingtool.CheckList;
import com.gotoubun.weddingvendor.exception.PhoneAlreadyExistException;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.CustomerRepository;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.gotoubun.weddingvendor.service.common.GenerateRandomPasswordService.GenerateRandomPassword.generateRandomString;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    GetCurrentDate getCurrentDate;

    @Transactional
    @Override
    public Customer save(CustomerRequest customerRequest) {
        // TODO Auto-generated method stub
        Account account = new Account();
        Customer guest = new Customer();

        //check username existed
        if (checkUserNameExisted(customerRequest.getEmail())) {
            throw new UsernameAlreadyExistsException("email: " + customerRequest.getEmail() + " already exist");
        }

        //save account
        account.setUsername(customerRequest.getEmail());
        account.setPassword(bCryptPasswordEncoder.encode(customerRequest.getPassword()));
        account.setRole(3);
        account.setStatus(1);
        account.setCreatedDate(getCurrentDate.now());
        account.setModifiedDate(getCurrentDate.now());
        accountRepository.save(account);

        if (checkPhoneExisted(customerRequest.getPhone())) {
            throw new PhoneAlreadyExistException("phone: " + customerRequest.getPhone() + " already exist");
        }

        //save customer
        guest.setAccount(account);
        guest.setEmail(customerRequest.getEmail());
        guest.setFullName(customerRequest.getFullName());
        guest.setEmail(customerRequest.getEmail());
        guest.setPhone(customerRequest.getPhone());
        guest.setAddress(customerRequest.getAddress());
        guest.setPlanningDate(LocalDate.parse(customerRequest.getWeddingDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        guest.setCheckList(createCheckList(guest));
        customerRepository.save(guest);
        return guest;
    }

    boolean checkUserNameExisted(String username) {
        return accountRepository.findByUsername(username) != null;

    }

    CheckList createCheckList(Customer customer){
        String id= "cl" +generateRandomString(10);
        CheckList checkList= new CheckList();
        checkList.setId(id);
        checkList.setCheckListName(customer.getFullName());
        checkList.setCreatedDate(customer.getAccount().getCreatedDate());
        checkList.setModifiedDate(customer.getAccount().getModifiedDate());
        checkList.setCustomer(customer);
        return  checkList;
    }

    boolean checkPhoneExisted(String phone) {
        return customerRepository.findByPhone(phone) != null;
    }

}
