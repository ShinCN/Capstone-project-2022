package com.gotoubun.weddingvendor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "hello-controller")
public class TestController {

@GetMapping(value="hello")
public String hello() {
    return "Hello";
}
}
