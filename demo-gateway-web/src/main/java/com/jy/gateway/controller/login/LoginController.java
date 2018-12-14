package com.jy.gateway.controller.login;

import com.jy.common.result.Result;
import com.jy.common.sso.model.User;
import com.jy.common.utils.ReflectUtils;
import com.jy.gateway.properties.SecurityProperties;
import com.jy.gateway.service.user.UserService;
import com.jy.gateway.utils.EncodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
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

    @Autowired
    private SecurityProperties securityProperties;

    @Value("${encrypt.salt}")
    private String salt;

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
    public Callable<String> testCallable(HttpServletRequest request) throws InterruptedException {
        HttpSession session = request.getSession(false);
        System.out.println(session);
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

    @ResponseBody
    @PostMapping("/logoutProcess")
    public Result logout(HttpServletRequest request){
        String token = getRealToken(request);
        userService.logout(token);
        return Result.ok("ok");
    }


    @RequestMapping("/loginFailure")
    @ResponseBody
    public String error(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        Object o = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
       return o.getClass()+"";
    }

    @RequestMapping("/handError")
    public Object handError(){
        LockedException lockedException = new LockedException("");
        return lockedException.getClass();
    }

    @ResponseBody
    @RequestMapping("/checkToken")
    public Result check(HttpServletRequest request){
        String token = getRealToken(request);
        Map<String, Object> result = userService.checkToken(token);
        return Result.error(Integer.parseInt(result.get("code")+""),result.get("auth")+"");
    }

    @ResponseBody
    @GetMapping("/userInfo")
    public Result getUserName(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> result = ReflectUtils.getForMap(user, new String[]{"password"}, ReflectUtils.IGNORE, false);
        return Result.ok(result);
    }

    private String getRealToken(HttpServletRequest request){
        String header = request.getHeader(securityProperties.getToken().getAccessToken());
        if(header == null){
            return null;
        }
        return EncodeUtil.decrypt(header,salt);
    }

}