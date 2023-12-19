package com.cs790.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String test(){
        return "Welcome to tasks app";
    }

    @GetMapping("/second")
    public String test2(){
        return "Next page working";
    }
}
