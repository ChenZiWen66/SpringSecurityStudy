package com.czw.webapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @RequestMapping("/admin")
    public String adminpage(){
        return "这是管理员界面，只有管理员才能登陆";
    }
}
