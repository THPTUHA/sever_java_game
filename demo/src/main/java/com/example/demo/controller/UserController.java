package com.example.demo.controller;


import javax.validation.Valid;

import com.example.demo.LoginRequest;
import com.example.demo.LoginResponse;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.CustomUserDetails;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    
    @GetMapping("/home")
    public String home(){
        System.out.println("OK");
        return "Home";
    }
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
    @GetMapping("/admin")
    public String admin(){
        return "Admin";
    }
    @GetMapping("/user")
    public User user(@RequestParam String email){
        User data_user = userRepository.findByEmail(email);
        return data_user;
    }
    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
    
   
}
