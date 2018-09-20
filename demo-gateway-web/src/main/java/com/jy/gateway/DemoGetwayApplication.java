package com.jy.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EnableZuulProxy//start zuul
@EnableEurekaClient//start eureka client to be find
@SpringBootApplication
@MapperScan("com.jy.gateway.mapper")
@ComponentScan({"com.jy.gateway.filter", "com.jy.gateway.controller", "com.jy.gateway.service", "com.jy.gateway.config"})
@EnableTransactionManagement
public class DemoGetwayApplication {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoGetwayApplication.class, args);
	}

}