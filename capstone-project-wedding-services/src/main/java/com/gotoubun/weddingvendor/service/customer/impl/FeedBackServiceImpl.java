package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.feedback.FeedBackRequest;
import com.gotoubun.weddingvendor.data.feedback.FeedBackResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.Feedback;
import com.gotoubun.weddingvendor.domain.weddingtool.PaymentHistory;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.FeedBackRepository;
import com.gotoubun.weddingvendor.repository.PaymentHistoryRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.customer.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private GetCurrentDate getCurrentDate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private SinglePostRepository singlePostRepository;

    @Autowired
    private FeedBackRepository feedBackRepository;

    private boolean check = false;
    @Override
    public void save(Long receiptId, Long serviceId, FeedBackRequest request, String username) {
        Account account = accountRepository.findByUsername(username);

        if(account.getUsername().equals("")){
            throw new UsernameNotFoundException("This user does not exist");
        }

        Optional<PaymentHistory> paymentHistory = Optional.ofNullable(paymentHistoryRepository
                .findById(receiptId).orElseThrow(() -> new ResourceNotFoundException("Receipt does not exist")));

        Collection<PaymentHistory> paymentHistories = paymentHistoryRepository.findAllByCustomer_Account(account);

        paymentHistories.forEach(c->{
            if(c.getCustomer() == paymentHistory.get().getCustomer()){
                check = true;
            }
        });
        if(!check){
            throw new ResourceNotFoundException("You can not give feedback to services cause " +
                    "this receipt does not exist in your account");
        }

        check = false;
        Feedback feedback = new Feedback();
        paymentHistory.get().getSinglePosts().forEach(c -> {
            if(c.getId() == serviceId){
                check = true;
                int countRate = c.getCountRate();
                feedback.setCustomer(account.getCustomer());
                feedback.setSinglePost(c);

                feedback.setContent(request.getContent());
                feedback.setCreatedBy(username);
                feedback.setCreatedDate(getCurrentDate.now());
                if(request.getRate()!=0){
                    countRate += 1;
                    c.setCountRate(countRate);
                    c.setRate((request.getRate() + c.getRate())/countRate);
                }
                singlePostRepository.save(c);
            }
        });
        if(!check){
            throw new ResourceNotFoundException("You can not give feedback to services cause " +
                    "this service does not exist in your receipt");
        }

        feedBackRepository.save(feedback);
    }

    @Override
    public Collection<FeedBackResponse> findGoodRateFeedBack() {

        Collection<Feedback> feedbacks = feedBackRepository.findAll();
        Collection<FeedBackResponse> feedBackResponses = new ArrayList<>();
        feedbacks.forEach(c -> {
            FeedBackResponse feedBackResponse = FeedBackResponse.builder()
                    .id(c.getId())
                    .content(c.getContent())
                    .createdBy(c.getCreatedBy())
                    .build();
            feedBackResponses.add(feedBackResponse);
        });
        return feedBackResponses.stream().limit(5L).collect(Collectors.toList());
    }
}
