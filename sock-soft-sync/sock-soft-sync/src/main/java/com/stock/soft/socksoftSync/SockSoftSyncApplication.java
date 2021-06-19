package com.stock.soft.socksoftSync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SockSoftSyncApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(SockSoftSyncApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(SockSoftSyncApplication.class);
	}

}
