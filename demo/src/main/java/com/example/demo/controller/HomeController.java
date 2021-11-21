package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.LoginRequest;
import com.example.demo.LoginResponse;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.CustomUserDetails;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailSenderService;
import com.example.demo.service.UserService;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        userRepository.updateLastLogin(new Date(), loginRequest.getEmail());
        return new LoginResponse(jwt);
    }

    @GetMapping("/verify")
    public String verify(@RequestAttribute int id){
        System.out.println(id);
        User user = userRepository.getById(id);
        if(user==null)return "FUCK";
        return "OK";
    }
    @PostMapping("/register")
    public String signUp(@RequestBody User user) {
        System.out.println(user);
        User user_exist = userRepository.findByEmail(user.getEmail());
        if(user_exist != null)return "Email đã tồn tại";
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User new_user = userRepository.save(user);
        String jwt = tokenProvider.generateToken(new_user);
        String link ="http://localhost:8080/verify?token="+jwt;
        emailSenderService.sendSimpleEmail(user.getEmail(), "Click here to verify : "+link, "TEST");
        return "Success";
    }
}
