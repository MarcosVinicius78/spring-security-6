package com.spring.security.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Teste {
    
    @GetMapping("/")
    public String home() {
        return "Ola mundo";
    }

    @GetMapping("/user")
    public String user(Authentication authentication){
        return "Ola " + authentication.getName();
    }

    @GetMapping("/admin")
    public String admin(Authentication authentication) {
        return "Ola " + authentication.getName();
    }
}
