package com.bankofmagic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BankOfMagicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankOfMagicApplication.class, args);
		System.out.println("Start..");
		//System.out.println(new BCryptPasswordEncoder().encode("1234"));
	}

}
