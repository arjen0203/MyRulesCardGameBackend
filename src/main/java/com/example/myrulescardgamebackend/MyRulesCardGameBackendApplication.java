package com.example.myrulescardgamebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MyRulesCardGameBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRulesCardGameBackendApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCRyptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
