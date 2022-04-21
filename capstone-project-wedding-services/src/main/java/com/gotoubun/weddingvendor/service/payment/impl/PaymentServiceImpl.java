package com.gotoubun.weddingvendor.service.payment.impl;

import com.gotoubun.weddingvendor.data.payment.PaymentHistoryResponse;
import com.gotoubun.weddingvendor.data.payment.ReceiptDetailResponse;
import com.gotoubun.weddingvendor.data.payment.SinglePostPaymentResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.domain.weddingtool.PaymentHistory;
import com.gotoubun.weddingvendor.exception.EmptyPaymentHistoryException;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.PaymentHistoryRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
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


    @Override
    public void save(String amount, String txnRef, String bankCode, String bankTransNo,
                     String cardType, String orderInfo, String responseCode,
                     String tmnCode, String transNo, String transStatus,
                     String secureHash, String username, List<Long> serviceId) {

        try {
            Account account = accountRepository.findByUsername(username);

            if (account.getUsername().equals("")) {
                throw new UsernameNotFoundException("This user does not exist");
            }

            Collection<SinglePost> singlePosts = new ArrayList<>();
            PaymentHistory paymentHistory = new PaymentHistory();
            serviceId.forEach(c -> {
                Long id = Long.valueOf(c.toString().trim());
                Optional<SinglePost> singlePost  = Optional.ofNullable(singlePostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("This service does not exist in your payment")));
                singlePost.get().getPaymentHistories().add(paymentHistory);
                singlePosts.add(singlePost.get());
            });

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
