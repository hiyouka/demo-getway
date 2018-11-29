package com.jy.gateway.controller.login;

import com.jy.gateway.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.Callable;

/**
 * create by jianglei on 2018/9/17
 * @since 1.0.0
 */

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Autowired
    private UserService userService;


    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    //用户登录 返回

    @RequestMapping("/doRedirect")
    public void doRedirect(HttpServletRequest request, HttpServletResponse response) {
        boolean status = userService.doRedirect();

//        return "redirect:/front/login";
//        return "index";
    }

    @RequestMapping("/login")
    public String index(){
//        return "index";
        return "redirect:/front/login";
    }


    @RequestMapping("/showdown")
    public String showdown(){
        return "redirect:/front/showdown";
//        return "index";
    }

    @ResponseBody
    @GetMapping("/callable1")
    public String get(){
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始！");
                Thread.sleep(1000);
                logger.info("副线程结束！");
                return "SUCCESS";
            }
        };
        return "ok";
    }

    @ResponseBody
    @GetMapping("/callable2")
    public Callable<String> testCallable() throws InterruptedException {
        logger.info("主线程开始！");
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始！");
                Thread.sleep(1000);
                logger.info("副线程结束！");
                return "SUCCESS";
            }

        };
        logger.info("主线程结束！");
        return result;
    }





    @RequestMapping("/loginFailure")
    @ResponseBody
    public String error(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        Object o = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
       return o.getClass()+"";
    }

    @ResponseBody
    @RequestMapping("/handError")
    public Object handError(){
        LockedException lockedException = new LockedException("");
        return lockedException.getClass();
    }
}