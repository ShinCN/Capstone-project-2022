package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.customer.CustomerRequest;
import com.gotoubun.weddingvendor.data.customer.CustomerRequestOAuth;
import com.gotoubun.weddingvendor.data.customer.CustomerResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.domain.weddingtool.Budget;
import com.gotoubun.weddingvendor.domain.weddingtool.BudgetCategory;
import com.gotoubun.weddingvendor.domain.weddingtool.CheckList;
import com.gotoubun.weddingvendor.exception.*;
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

    @Autowired
    private PackagePostRepository packagePostRepository;

    @Transactional
    @Override
    public void save(CustomerRequest customerRequest) {
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
        account.setStatus(true);
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
        guest.setPhone(customerRequest.getPhone());
        guest.setAddress(customerRequest.getAddress());
        guest.setPlanningDate(LocalDate.parse(customerRequest.getWeddingDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        guest.setCheckList(createCheckList(guest));
        guest.setPlanningBudget(0L);
        guest.setBudget(createBudget(guest));
        customerRepository.save(guest);

    }

    @Override
    public void oauthSave(CustomerRequestOAuth customerRequest) {
        // TODO Auto-generated method stub
        Account account = new Account();
        Customer fbGuest = new Customer();

        //check username existed
        if (checkUserNameExisted(customerRequest.getEmail())) {
            throw new UsernameAlreadyExistsException("email: " + customerRequest.getEmail() + " already exist");
        }


        //save account
        account.setUsername(customerRequest.getEmail());
        account.setPassword(bCryptPasswordEncoder.encode(customerRequest.getPassword()));
        account.setRole(3);
        account.setStatus(true);
        account.setCreatedDate(getCurrentDate.now());
        account.setModifiedDate(getCurrentDate.now());
        accountRepository.save(account);
        //save customer

        fbGuest.setAccount(account);
        fbGuest.setEmail(customerRequest.getEmail());
        fbGuest.setFullName(customerRequest.getFullName());
        fbGuest.setCheckList(createCheckList(fbGuest));
        fbGuest.setBudget(createBudget(fbGuest));
        fbGuest.setPlanningBudget(0L);

        customerRepository.save(fbGuest);
    }

    @Override
    public Customer findByAccount(Account account) {
        return customerRepository.findByAccount(account);
    }

    @Override
    public void addService(Long id, String username) {
        Account account = accountRepository.findByUsername(username);
        Customer customer = findByAccount(account);
        Optional<SinglePost> singlePost = singlePostRepository.findById(id);

        if(!singlePost.isPresent()){
            throw new SingleServicePostNotFoundException("This service does not exist");
        }
        customer.getSinglePosts().add(singlePost.get());
        singlePost.get().getCustomers().add(customer);
        customerRepository.save(customer);
        singlePostRepository.save(singlePost.get());
    }

    @Override
    public void deleteService(Long id, String username) {
        Account account = accountRepository.findByUsername(username);
        Customer customer = findByAccount(account);
        Optional<SinglePost> singlePost = singlePostRepository.findById(id);

        customer.getSinglePosts().remove(singlePost);
        singlePost.get().getCustomers().remove(customer);
        customerRepository.save(customer);
        singlePostRepository.save(singlePost.get());
    }

    @Override
    public void addPackageService(Long id, String username) {
        Account account = accountRepository.findByUsername(username);
        Customer customer = findByAccount(account);
        PackagePost packagePost = packagePostRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Service Pack not found"));

        packagePost.getCustomers().add(customer);
        customer.getPackagePosts().add(packagePost);

        packagePostRepository.save(packagePost);
    }

    boolean checkServicePostExistedInPackagePost(PackagePost packagePost, Customer customer) {
        return customer.getPackagePosts().contains(packagePost);
    }

    @Override
    public void update(CustomerRequest customerRequest, String username) {

        Customer customer = customerRepository.findByAccount(accountRepository.findByUsername(username));

        //check phone existed
        if(customer.getPhone().equals(customerRequest.getPhone()))
        {
            customerRepository.save(mapToEntity(customerRequest,customer));
        }
        else if (checkPhoneExisted(customerRequest.getPhone())) {
            throw new PhoneAlreadyExistException("phone: " + customerRequest.getPhone() + " already exist");
        }
    }

    @Override
    public CustomerResponse load(String username) {
        Customer customer = customerRepository.findByAccount(accountRepository.findByUsername(username));
        return convertToResponse(customer);
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

    Budget createBudget(Customer customer){
        Budget budget = new Budget();
        List<SingleCategory> singleCategoryList = singleCategoryRepository.findAll();
        budget.setCustomer(customer);
        budget.setBudgetCategories(budgetCategoryRepository.findAll());
        budgetRepository.save(budget);
        for(SingleCategory s : singleCategoryList){
            BudgetCategory budgetCategory = new BudgetCategory();
            budgetCategory.setCategoryName(s.getCategoryName());
            budgetCategory.setCost(0L);
            budgetCategory.setBudget(budget);
            budgetCategoryRepository.save(budgetCategory);
        }
        return  budget;
    }

    private Customer mapToEntity (CustomerRequest customerRequest, Customer customer)
    {
        customer.setFullName(customerRequest.getFullName());
        customer.setAddress(customerRequest.getAddress());
        customer.setPlanningDate(LocalDate.parse(customerRequest.getWeddingDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        customer.getAccount().setModifiedDate(getCurrentDate.now());
        return customer;
    }

    private CustomerResponse convertToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .username(customer.getEmail())
                .fullName(customer.getFullName())
                .phone(customer.getPhone())
                .weddingDate(customer.getPlanningDate())
                .address(customer.getAddress())
                .password(customer.getAccount().getPassword())
                .createdDate(customer.getAccount().getCreatedDate())
                .modifiedDate(customer.getAccount().getModifiedDate())
                .build();
    }

    boolean checkPhoneExisted(String phone) {
        return customerRepository.findByPhone(phone) != null;
    }
}
