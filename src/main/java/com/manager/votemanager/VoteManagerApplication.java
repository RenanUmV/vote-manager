package com.manager.votemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableRetry
public class VoteManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoteManagerApplication.class, args);
	}

}
