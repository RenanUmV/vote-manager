package com.manager.votemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VoteManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoteManagerApplication.class, args);
	}

}
