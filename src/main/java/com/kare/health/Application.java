package com.kare.health;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@EntityScan({ "com.kare.health.model"})
@ComponentScan("com.kare.health")
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(Application.class, args);

	}

	@Bean
	public ObjectMapper objectMapper() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(dateFormat);
		System.out.print(java.util.TimeZone.getDefault().getID());
		return objectMapper;
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		System.out.println("Spring boot application running in UTC timezone :" + new Date());
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
