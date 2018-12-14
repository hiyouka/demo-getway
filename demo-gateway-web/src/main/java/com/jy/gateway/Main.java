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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EnableZuulProxy//start zuul
@EnableEurekaClient//start eureka client to be find
@SpringBootApplication
@MapperScan("com.jy.gateway.mapper")
@ComponentScan({"com.jy.gateway","com.jy.common.sso.config"})
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

	private static Field[] getAllField(Class<?> clazz){
		List<Field> fieldList = new ArrayList<>();
		List<Class> interfaces = new ArrayList<>();
		boolean isSerlize = false;

		while (clazz != null){
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		if(isSerlize)
			fieldList = fieldList.stream().filter(field -> !field.getName().equals("serialVersionUID")).collect(Collectors.toList());
		Field[] fields = new Field[fieldList.size()];
		return fieldList.toArray(fields);
	}

	private static List<Class<?>> getAllInterfaces(Class<?> clazz){
		List<Class<?>> interfaces = new ArrayList<>();
		while (clazz != null){
			interfaces.addAll(Arrays.asList(clazz.getInterfaces()));
			clazz = clazz.getSuperclass();
		}
		return interfaces;
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
//		//自定义AllowedHeader
//		config.addAllowedHeader("ACCESS-TOKEN");
//		config.addAllowedHeader("USER-ID");
//		config.addAllowedHeader("LOGIN-TYPE");
//		config.addAllowedHeader("STORE-TOKEN");
////        PDF
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
		config.addAllowedHeader(CorsConfiguration.ALL);
		config.addAllowedMethod(CorsConfiguration.ALL);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}