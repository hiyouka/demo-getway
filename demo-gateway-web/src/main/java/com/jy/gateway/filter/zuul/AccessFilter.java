package com.jy.gateway.filter.zuul;

import com.jy.common.sso.model.User;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Enumeration;

/**
 * @author jianglei
 * @create 2018/6/13
 * @since 1.0.0
 */
@Component
public class AccessFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(ZuulFilter.class);


    @Override
    public String filterType() {
        return "pre";//在请求被路由前调用
    }

    @Override
    public int filterOrder() {
        return 0;//数字代表过滤器优先级 执行顺序 0 最大
    }

    @Override
    public boolean shouldFilter() {
        return true;//过滤器是否执行
    }

    @Override
    //实现zuul的run方法来执行具体的拦截代码
    public Object run() {

        RequestContext context = RequestContext.getCurrentContext();

        //避免自动生成Session
        HttpSession httpSession = context.getRequest().getSession(false);
//        Session session = repository.getSession(httpSession.getId());

        if (httpSession != null) {
            boolean aNew = httpSession.isNew();
            Enumeration<String> attributeNames = httpSession.getAttributeNames();
            while (attributeNames.hasMoreElements()){
                logger.info("<<<<<<<<<<<<<<<>>>>>>>>>>>>" + attributeNames.nextElement());
            }
            context.addZuulRequestHeader("Cookie", "JSESSIONID=" + httpSession.getId());
            try {
                SecurityContext context1 = SecurityContextHolder.getContext();
                Authentication authentication = context1.getAuthentication();
                if(authentication != null){

                    Object principal = authentication.getPrincipal();
                    if(principal != null && principal.getClass() != String.class){ //当访问未进行登录拦截不需要登陆的页面
                        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                        authorities.forEach(grant ->{
                            System.out.println(grant.getAuthority());
                        });
                        System.out.println(authentication.getCredentials()+">>>>>>>>>>>>");
                        String id = ((User) principal).getId();
//                String userId = ((SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
                        logger.info("{} {}", httpSession.getId(), id);
                    }
                }
            } catch (Exception e) {
                //不要用log
                logger.error("get session userId mapping error!", e);
            }
        }

        return null;
    }/*

        RequestContext currentContext = RequestContext.getCurrentContext();
        //zuul添加对websocket的支持
        String upgrade = httpServletRequest.getHeader("Upgrade");
        if(upgrade == null){
            upgrade = httpServletRequest.getHeader("upgrade");
        }
        if (upgrade != null && "websocket".equalsIgnoreCase(upgrade)) {
            currentContext.addZuulRequestHeader("Connection","Upgrade");
        }

        HttpSession session = httpServletRequest.getSession(false);
        if (session != null){
            currentContext.addZuulRequestHeader("Cookie","JSESSIONID"+session.getId());
        }

        return null;
    }*/
}