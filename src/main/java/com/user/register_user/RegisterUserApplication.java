package com.user.register_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

@EnableAspectJAutoProxy
@SpringBootApplication
public class RegisterUserApplication{

//	@Bean
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}

	public static void main(String[] args) {

		SpringApplication.run(RegisterUserApplication.class, args);
	}
}


