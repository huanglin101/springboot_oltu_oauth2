package com.demo.oauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
@MapperScan(basePackages = "com.demo.oauth2.*.mapper")
public class OauthserverApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OauthserverApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(OauthserverApplication.class, args);
	}
}
