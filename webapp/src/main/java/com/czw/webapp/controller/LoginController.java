package com.czw.webapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @RequestMapping("/securitylogin")
    public String securitylogin() {
        return "这是Securitylogin";
    }
}
