package com.spring.security.security;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
    public CommandLineRunner commandLineRunner(JdbcUserDetailsManager detailsManager, DataSource dataSource) {
        return args -> {
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin")
                .roles("USER", "ADMIN")
                .build();
        UserDetails user = User.builder()
                .username("Marcos")
                .password("{noop}admin")
                .roles("USER")
                .build();
        JdbcUserDetailsManager detailsManage = new JdbcUserDetailsManager(dataSource);
        detailsManage.createUser(user);
        detailsManage.createUser(admin);
        };
    }

}
