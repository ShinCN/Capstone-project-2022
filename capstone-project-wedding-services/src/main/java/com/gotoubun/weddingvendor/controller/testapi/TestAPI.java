package com.gotoubun.weddingvendor.controller.testapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAPI {

    @GetMapping("/")
    public String testAPI(){
        return "Ok luon";
    }

}
