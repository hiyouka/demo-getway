package com.jy;

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
@MapperScan("com.jy.mapper")
@ComponentScan({"com.jy.filter","com.jy.controller","com.jy.service","com.jy.config"})
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