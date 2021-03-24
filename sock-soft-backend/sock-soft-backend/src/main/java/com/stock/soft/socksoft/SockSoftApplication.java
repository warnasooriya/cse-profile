package com.stock.soft.socksoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SockSoftApplication {

	public static void main(String[] args) {
		SpringApplication.run(SockSoftApplication.class, args);
	}

}
