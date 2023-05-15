package com.spring.security.security.security;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(100)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/user")
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers("/user/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");
                    authConfig.anyRequest().authenticated();
                })
                .formLogin(withDefaults())
                .oauth2Login(withDefaults());
        return http.build();
    }

    @Bean
    @Order(101)
    SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
        http.securityMatcher("/admin")
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN");
                    authConfig.anyRequest().authenticated();
                })
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    @Order(102)
    SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {

        http.securityMatcher("/")
                .authorizeHttpRequests(authConfig -> {
                    authConfig.anyRequest().permitAll();
                })

			.formLogin(withDefaults());
        return http.build();
    }

    @Bean
    @Order(103)
    SecurityFilterChain securityFilterChain3(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authConfig -> {
            authConfig.anyRequest().denyAll();
        })
        .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        return jdbcUserDetailsManager;
    }

    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

}
