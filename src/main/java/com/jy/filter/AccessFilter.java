package com.jy.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author jianglei
 * @create 2018/6/13
 * @since 1.0.0
 */
@Component
public class AccessFilter extends ZuulFilter {

    @Autowired
    private HttpServletRequest httpServletRequest;

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
    }
}