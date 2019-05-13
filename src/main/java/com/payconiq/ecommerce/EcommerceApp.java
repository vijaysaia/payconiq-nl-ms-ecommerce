package com.payconiq.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.payconiq.ecommerce"}) 
public class EcommerceApp {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApp.class, args);
	}
}