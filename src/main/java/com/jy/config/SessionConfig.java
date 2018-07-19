package com.jy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 〈〉
 *
 * @author jianglei
 * @create 2018/6/13
 * @since 1.0.0
 */
@Configuration
//管理session maxInactiveIntervalInSeconds:配置session的过期时间,redisFlushMode: redis session 的刷新模式 IMMEDIATE 确保zuul存储到redis的session对象在请求到service时就被立即获取 在实际的开发过程中不配置可能无法立即获取session
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 20000,redisFlushMode = RedisFlushMode.IMMEDIATE)
public class SessionConfig {


}