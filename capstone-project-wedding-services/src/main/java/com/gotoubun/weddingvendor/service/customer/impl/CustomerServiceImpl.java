package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.customer.CustomerRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.domain.weddingtool.Budget;
import com.gotoubun.weddingvendor.domain.weddingtool.BudgetCategory;
import com.gotoubun.weddingvendor.exception.PhoneAlreadyExistException;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.exception.SingleServicePostNotFoundException;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.*;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.customer.BudgetCategoryService;
import com.gotoubun.weddingvendor.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SingleCategoryRepository singleCategoryRepository;

    @Autowired
    GetCurrentDate getCurrentDate;

    @Autowired
    BudgetCategoryService budgetCategoryService;

    @Autowired
    BudgetCategoryRepository budgetCategoryRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    private SinglePostRepository singlePostRepository;

    @Override
    @Transactional
    public Customer save(CustomerRequest customerRequest) {
        // TODO Auto-generated method stub
        Account account = new Account();
        Customer guest = new Customer();


        //check username existed
        if (checkUserNameExisted(customerRequest.getEmail())) {
            throw new UsernameAlreadyExistsException("email: " + customerRequest.getEmail() + " already exist");
        }

        Budget budget = new Budget();
        List<SingleCategory> singleCategoryList = singleCategoryRepository.findAll();




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

        budget.setCustomer(guest);
        budget.setBudgetCategories(budgetCategoryRepository.findAll());
        budgetRepository.save(budget);
        for(SingleCategory s : singleCategoryList){
            BudgetCategory budgetCategory = new BudgetCategory();
            budgetCategory.setCategoryName(s.getCategoryName());
            budgetCategory.setCost(0L);
            budgetCategory.setBudget(budget);
            budgetCategoryRepository.save(budgetCategory);
        }

        //save customer
        guest.setAccount(account);
        guest.setEmail(customerRequest.getEmail());
        guest.setFullName(customerRequest.getFullName());
        guest.setEmail(customerRequest.getEmail());
        guest.setPhone(customerRequest.getPhone());
        guest.setAddress(customerRequest.getAddress());
        guest.setPlanningDate(LocalDate.parse(customerRequest.getWeddingDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        guest.setPlanningBudget(0L);
        guest.setBudget(budget);
        customerRepository.save(guest);

        return guest;
    }

    @Override
    public Optional<Customer> findByAccount(Optional<Account> account) {
        return Optional.of(customerRepository.findByAccount(account).orElseThrow(() -> new ResourceNotFoundException("Category does not exist")));
    }

    @Override
    public void addService(Long id, String username) {
        Account account = accountRepository.findByUsername(username);
        Optional<Customer> customer = findByAccount(Optional.ofNullable(account));
        Optional<SinglePost> singlePost = singlePostRepository.findById(id);

        if(!singlePost.isPresent()){
            throw new SingleServicePostNotFoundException("This service does not exist");
        }
        customer.get().getSinglePosts().add(singlePost.get());
        singlePost.get().getCustomers().add(customer.get());
        customerRepository.save(customer.get());
        singlePostRepository.save(singlePost.get());
    }

    @Override
    public void deleteService(Long id, String username) {
        Account account = accountRepository.findByUsername(username);
        Optional<Customer> customer = findByAccount(Optional.ofNullable(account));
        Optional<SinglePost> singlePost = singlePostRepository.findById(id);

        customer.get().getSinglePosts().remove(singlePost);
        singlePost.get().getCustomers().remove(customer);
        customerRepository.save(customer.get());
        singlePostRepository.save(singlePost.get());
    }


    boolean checkUserNameExisted(String username){
        return accountRepository.findByUsername(username) != null;
    }


    boolean checkPhoneExisted(String phone) {
        return customerRepository.findByPhone(phone) != null;
    }
}
