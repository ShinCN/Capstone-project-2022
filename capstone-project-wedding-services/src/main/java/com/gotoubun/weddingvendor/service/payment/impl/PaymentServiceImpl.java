package com.gotoubun.weddingvendor.service.payment.impl;

import com.gotoubun.weddingvendor.data.payment.PaymentHistoryResponse;
import com.gotoubun.weddingvendor.data.payment.ReceiptDetailResponse;
import com.gotoubun.weddingvendor.data.payment.SinglePostPaymentResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.domain.weddingtool.Budget;
import com.gotoubun.weddingvendor.domain.weddingtool.BudgetCategory;
import com.gotoubun.weddingvendor.domain.weddingtool.PaymentHistory;
import com.gotoubun.weddingvendor.exception.EmptyPaymentHistoryException;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.*;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.customer.CustomerService;
import com.gotoubun.weddingvendor.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private GetCurrentDate currentDate;

    @Autowired
    private SinglePostRepository singlePostRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BudgetCategoryRepository budgetCategoryRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void save(String amount, String txnRef, String bankCode, String bankTransNo,
                     String cardType, String orderInfo, String responseCode,
                     String tmnCode, String transNo, String transStatus,
                     String secureHash, String username, List serviceId) {

        try {
            Account account = accountRepository.findByUsername(username);
            Customer customer = customerRepository.findByAccount(account);
            if (account.getUsername().equals("")) {
                throw new UsernameNotFoundException("This user does not exist");
            }

            Collection<SinglePost> singlePosts = new ArrayList<>();
            PaymentHistory paymentHistory = new PaymentHistory();
            Budget budget = budgetRepository.findByCustomerAccount(account);
            Collection<BudgetCategory> budgetCategories = budgetCategoryRepository.findAllByBudget_Customer_Account(account);
            serviceId.forEach(c -> {
                Long id = Long.valueOf(c.toString().trim());
                Optional<SinglePost> singlePost  = singlePostRepository.findById(id);
                singlePost.get().getPaymentHistories().add(paymentHistory);
                singlePosts.add(singlePost.get());
                budgetCategories.forEach(d -> {
                    if(d.getCategoryName().equalsIgnoreCase(singlePost.get().getSingleCategory().getCategoryName())){
                        float newPrice = d.getCost() + singlePost.get().getPrice();
                        d.setCost((long) newPrice);
                    }
                });
            });

            budget.setBudgetCategories(budgetCategories);
            customer.setBudget(budget);
            budgetRepository.save(budget);
            customerRepository.save(customer);

            paymentHistory.setAmount(Float.parseFloat(amount));
            paymentHistory.setTmnCode(tmnCode);
            paymentHistory.setTxnRef(txnRef);
            paymentHistory.setResponseCode(responseCode);
            paymentHistory.setBankCode(bankCode);
            paymentHistory.setBankTransNo(bankTransNo);
            paymentHistory.setCardType(cardType);
            paymentHistory.setOrderInfo(orderInfo);
            paymentHistory.setPayDate(currentDate.now());
            paymentHistory.setTransNo(transNo);
            paymentHistory.setSecureHashType("hmacSHA512");
            paymentHistory.setSecureHash(secureHash);
            paymentHistory.setCustomer(account.getCustomer());
            paymentHistory.setSinglePosts(singlePosts);
            paymentHistory.setTransStatus("success");

            paymentHistoryRepository.save(paymentHistory);

        }catch (Exception e){
            e.getCause().printStackTrace();
        }
    }

    @Override
    public Collection<PaymentHistoryResponse> findAllReceipt(String username) {
        Account account = accountRepository.findByUsername(username);
        if(account.getUsername().equalsIgnoreCase("")){
            throw new UsernameNotFoundException("This user does not exist");
        }
        Collection<PaymentHistory> paymentHistories = paymentHistoryRepository.findAllByCustomer_Account(account);

        if(paymentHistories.isEmpty()){
            throw new EmptyPaymentHistoryException("This user has not made any payment yet");
        }
        Collection<PaymentHistoryResponse> paymentHistoryResponses = new ArrayList<>();

        paymentHistories.forEach(c -> {
            PaymentHistoryResponse paymentHistoryResponse = PaymentHistoryResponse.builder()
                    .id(c.getId())
                    .createdDate(c.getPayDate())
                    .posts(c.getSinglePosts().stream().map(d ->
                            new SinglePostPaymentResponse(d.getId(), d.getServiceName(),
                                    d.getPrice())).collect(Collectors.toList()))
                    .build();
            paymentHistoryResponses.add(paymentHistoryResponse);
        });
        return paymentHistoryResponses;
    }

    @Override
    public ReceiptDetailResponse getReceiptDetail(Long id, String username) {
        Account account = accountRepository.findByUsername(username);

        if(account.getUsername().equalsIgnoreCase("")){
            throw new UsernameNotFoundException("This user does not exist");
        }

        Optional<PaymentHistory> paymentHistory = Optional.ofNullable(paymentHistoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Receipt does not exist")));

        float amount = 0;
        for(SinglePost sp : paymentHistory.get().getSinglePosts()){
            amount += sp.getPrice();
        }

        ReceiptDetailResponse receiptDetailResponse = new ReceiptDetailResponse();
        receiptDetailResponse.setCreatedDate(paymentHistory.get().getPayDate());
        receiptDetailResponse.setTotalPrice(amount);
        receiptDetailResponse.setPaymentMethod(paymentHistory.get().getCardType());
        receiptDetailResponse.setTxnRef(paymentHistory.get().getTxnRef());
        receiptDetailResponse.setSinglePostPaymentResponses(paymentHistory.get().getSinglePosts().stream().map(d ->
                new SinglePostPaymentResponse(d.getId(), d.getServiceName(),
                        d.getPrice())).collect(Collectors.toList()));
        return  receiptDetailResponse;
    }
}