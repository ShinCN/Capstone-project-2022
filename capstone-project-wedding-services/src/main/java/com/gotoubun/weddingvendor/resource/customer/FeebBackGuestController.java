package com.gotoubun.weddingvendor.resource.customer;

import com.gotoubun.weddingvendor.service.customer.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/feedback")
public class FeebBackGuestController {

    @Autowired
    private FeedBackService feedBackService;

    @GetMapping
    public ResponseEntity<?> getAllFeedBack(){
        return new ResponseEntity<>(feedBackService.findGoodRateFeedBack(), HttpStatus.CREATED);
    }
}