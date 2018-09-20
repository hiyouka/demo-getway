package com.jy.gateway.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * create by jianglei on 2018/9/17
 * @since 1.0.0
 */

@Controller
public class LoginController {

//    @GetMapping("/login")
//    public String login(){
//
//        return "";
//    }

    @GetMapping("/doRedirect")
    public String doDirect(){
        return "home";
    }

}