package com.gotoubun.weddingvendor.resource.testapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAPI {

    @GetMapping("/message")
    public String testAPI(){
        return "Ok luon";
    }

}
