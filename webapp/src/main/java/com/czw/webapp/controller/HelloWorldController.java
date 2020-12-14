package com.czw.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping("/hello")
    public String sayHello(){
        return "hello,czw";
    }
}
