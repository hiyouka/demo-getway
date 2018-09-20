package com.jy.gateway.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jianglei on 18/6/18.
 */
@Controller
public class HomeController {

    @Value("${front.server.url}")
    private String frontUrl;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/main")
    public String index(){
        return restTemplate.getForObject(frontUrl+"/index",String.class);
    }

//    @RequestMapping("/login")
//    public String login(Model model){
//        return "redirect:/index";
//    }



//    @RequestMapping("/")
//    public String toLogin(Model model){
//        return "home";
//    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }


    @RequestMapping("/websocket")
    public String websocket(Model model){
        return "websocket";
    }

}
