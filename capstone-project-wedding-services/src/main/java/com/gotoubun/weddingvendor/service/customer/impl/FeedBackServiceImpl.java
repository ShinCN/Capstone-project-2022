package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.feedback.FeedBackRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.Feedback;
import com.gotoubun.weddingvendor.domain.weddingtool.PaymentHistory;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.FeedBackRepository;
import com.gotoubun.weddingvendor.repository.PaymentHistoryRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.customer.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public void save(Long receiptId, Long serviceId, FeedBackRequest request, String username) {
        Account account = accountRepository.findByUsername(username);

        if(account.getUsername() == ""){
            throw new UsernameNotFoundException("This user does not exist");
        }

        Optional<PaymentHistory> paymentHistory = paymentHistoryRepository.findById(receiptId);
//        SinglePost

        Feedback feedback = new Feedback();
        paymentHistory.get().getSinglePosts().forEach(c -> {
            if(c.getId() == serviceId){
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
        feedBackRepository.save(feedback);
    }
}
