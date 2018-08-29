package com.alex.controller;

import com.alex.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        userService.saveStuAndPerson();
        return "hello";
    }
}
