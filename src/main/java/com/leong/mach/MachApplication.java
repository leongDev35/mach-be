package com.leong.mach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class MachApplication {

	public static void main(String[] args) {
		SpringApplication.run(MachApplication.class, args);
	}

}