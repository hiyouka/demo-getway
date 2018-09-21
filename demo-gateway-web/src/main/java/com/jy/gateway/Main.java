package com.jy.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableZuulProxy//start zuul
@EnableEurekaClient//start eureka client to be find
@SpringBootApplication
@MapperScan("com.jy.gateway.mapper")
@ComponentScan({"com.jy.gateway.filter", "com.jy.gateway.controller", "com.jy.gateway.service", "com.jy.gateway.config"})
@EnableTransactionManagement
public class Main {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Value("${cors.origins}")
	private String origins;

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		String[] origin = origins.split(",");
		for(String o : origin) {
			config.addAllowedOrigin(o);
		}
		//自定义AllowedHeader
		config.addAllowedHeader("ACCESS-TOKEN");
		config.addAllowedHeader("USER-ID");
		config.addAllowedHeader("LOGIN-TYPE");
		config.addAllowedHeader("STORE-TOKEN");
//        PDF
		config.addAllowedHeader("Accept-Ranges");
//        config.addAllowedMethod("*");
//        config.addAllowedHeader("*");
//        //
//        config.addAllowedOrigin("*");
		config.addAllowedHeader("Origin");
		config.addAllowedHeader("Content-Type");
		config.addAllowedHeader("Accept");
		config.addAllowedHeader("Cache-Control");
		config.addAllowedHeader("Authorization");
		config.addAllowedHeader("token");
		config.addAllowedHeader("X-Requested-With");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("DELETE");
		config.addExposedHeader("x-auth-token");
		config.addExposedHeader("x-total-count");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}