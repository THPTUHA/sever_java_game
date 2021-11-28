package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.GamePlay;
import com.example.demo.model.User;
import com.example.demo.repository.GamePlayReposity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CloudinaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GamePlayReposity gamePlayReposity;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CloudinaryService cloudinaryService;
    ArrayList<Integer>a=new ArrayList<>();
    @GetMapping("/home")
    public String home(){
        System.out.println("III");
       return "Home";
    }
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
    @GetMapping("")
    public User user(@RequestAttribute int id){
        User data_user = userRepository.findById(id);
        return data_user;
    }
    @GetMapping("/403")
    public String accessDenied() {
        a.add(2);
        while(true){
            if(a.size()>=2){
                return "OK";
            }
        }
       
    }

    @PostMapping("/update_avatar")
    public String update(@RequestParam("new_avatar")MultipartFile avatar ,@RequestAttribute int id ) {
        String link = cloudinaryService.uploadImage(avatar);
        userRepository.updateAvatar(link, id);
        return link;
    }
    
    @PostMapping("/gameplay/history")
    public List<GamePlay> historyPlay(@RequestAttribute("id") int user_id){
        List<GamePlay> game_play = gamePlayReposity.getHistory(user_id);
           return game_play;
      
    }
}
