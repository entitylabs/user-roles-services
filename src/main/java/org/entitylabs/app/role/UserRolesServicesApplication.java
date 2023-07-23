package org.entitylabs.app.role;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RefreshScope
@SpringBootApplication
public class UserRolesServicesApplication {

	public static void main(String[] args) {
		
		log.info("UserRolesServicesApplication.main started");
		
		SpringApplication.run(UserRolesServicesApplication.class, args);
	}

}
