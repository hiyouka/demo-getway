package com.jy.controller.user;

import com.jy.model.user.SysUser;
import com.jy.service.user.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @author jianglei
 * @create 2018/6/1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;



   @GetMapping("space")
    public String addmm(){
        return "ok";
    }


    @GetMapping("line")
    public SysUser addWatch(){
        return null;
    }


    @GetMapping("kk/{itemId}")
    public String getTest(@PathVariable String itemId){
//        DcsLineItem dcsLineItem = dcsLineItemMapper.selectByPrimaryKey(Long.parseLong(itemId));
//        cache.setValue("冷却机F1A#2风机进口阀门反馈",dcsLineItem.getItemVal());
        URL resource = Thread.currentThread().getContextClassLoader().getResource("");
        if (resource == null){
            return "what fuck !!";
        }else{
            String path2 = resource.getPath();
            return "resources path is : " + path2;
        }
    }

    @GetMapping("cc")
    public SysUser test(){

//        ApplicationContext applicationContext = ApplicationContextUtil.getApplicationContext();
//        Environment environment = applicationContext.getEnvironment();
//        String kid = environment.getProperty("kid");
//        System.out.println(kid+"====================");
//        AutowireDemo bean = applicationContext.getBean(AutowireDemo.class);
//        System.out.println("AutowireDemo get ==>" + bean.getUserService());
//        System.out.println("application get ==>" + applicationContext.getBean(UserService.class));
//        AutowireDemo2 au2 = (AutowireDemo2)applicationContext.getBean("au2");
//        System.out.println("AutowireDemo2 get ==>"+au2.getAutowireDemo());
//        System.out.println("application get ==>"+bean);
//        Object userDetails = applicationContext.getBean("userDetails");
//        UserDetailsService userDetailsService = new SercurityDemo().customUserService();
//        System.out.println(userDetails.getClass());
//        System.out.println("====>" + userDetailsService==userDetails);
        return userService.findUserByUserName("yinpin");
    }

    /**
     *单播
     */
    @GetMapping("rabbit/signSend")
    public String sendSingle(){
        Map<String,String> map = new HashMap<>();
        map.put("admin","14");
        map.put("message","rabbit");
        rabbitTemplate.convertAndSend("exchange.direct","exchange.queue",map);
        return map.get("message");
    }

    /**
     * 获取rabbitMQ消息
     */
    @GetMapping("rabbit/signGet")
    public String getSingle(){
        Object o = rabbitTemplate.receiveAndConvert("exchange.queue");
        System.out.println(o);
        return o==null?"":o.toString();
    }

    @GetMapping("/security/{username}/{password}")
    public String getAuthentication(@PathVariable String username,@PathVariable String password){
//        AuthenticationManager am = new SampleAuthenticationManager();
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUser principal = (SysUser) authentication.getPrincipal();

        return principal.getRoles().toString();
    }

    @GetMapping("session/get")
    public String getSession(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return
                session.getAttribute("sessionId")==null?"无session信息":(String) session.getAttribute("sessionId");
    }


}