package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.GamePlayReposity;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GamePlayReposity gamePlayReposity;
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
        System.out.println(email);
        User data_user = userRepository.findByEmail(email);
        return data_user;
    }
    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
    
    // @PostMapping("/play_public")
    // public String test(@RequestBody GamePlay gamePlay){
    //     // System.out.println(gamePlay);
    //     if(gamePlayReposity.getOneGamePlay()!=null)
    //    {
    //     GamePlay gamePlay2=gamePlayReposity.getOneGamePlay() ;
    //     System.out.println(gamePlay2);
    //    }
    //     return "ok";
    // }
}
