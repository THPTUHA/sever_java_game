package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.LoginRequest;
import com.example.demo.game.Match;
import com.example.demo.model.GamePlay;
import com.example.demo.model.User;
import com.example.demo.repository.GamePlayReposity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CloudinaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    private PasswordEncoder passwordEncoder;
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

    @PostMapping("/change_password")
    public String changePassword(@RequestAttribute("id")int user_id,@RequestAttribute("email")String email, @RequestBody LoginRequest loginRequest){
        System.out.print(loginRequest);
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, loginRequest.getPassword()));
             SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            userRepository.updatePassword(passwordEncoder.encode(loginRequest.getNew_password()), user_id);
        } catch (Exception e) {
            return "Some thing wrong";
        }
        return "Success";
    }
    // @PostMapping("/change_password")
    // public String changePassword(@RequestAttribute("id")int user_id,@RequestBody User user){
    //     try {
    //         userRepository.updateInfo(user.getPhone(),user.getDescription(), user_id);
    //     } catch (Exception e) {
    //         return "Some thing wrong";
    //     }
    //     return "Success";
    // }
    @PostMapping("/update_avatar")
    public String update(@RequestParam("new_avatar")MultipartFile avatar ,@RequestAttribute int id ) {
        String link = cloudinaryService.uploadImage(avatar);
        userRepository.updateAvatar(link, id);
        return link;
    }
    
    @PostMapping("/gameplay/history")
    public List<List<Match>> historyPlay(@RequestAttribute("id") int user_id){
        User user = userRepository.getGame(user_id);
        List<List<Match>> match = new ArrayList<>();
        List<Match> macth_sub_1 = new ArrayList<>();
        List<GamePlay>game_play_1 = user.pickGameplay_1();
        for(GamePlay a:game_play_1){
            macth_sub_1.add(new Match(a));
        }
        List<Match> macth_sub_2 = new ArrayList<>();
        List<GamePlay>game_play_2 = user.pickGameplay_2();
        for(GamePlay a:game_play_2){
            macth_sub_2.add(new Match(a));
        }
        List<Match> macth_sub_3 = new ArrayList<>();
        List<GamePlay>game_play_3 = user.pickGameplay_3();
        for(GamePlay a:game_play_3){
            macth_sub_3.add(new Match(a));
        }
        List<Match> macth_sub_4 = new ArrayList<>();
        List<GamePlay>game_play_4 = user.pickGameplay_4();
        for(GamePlay a:game_play_4){
            macth_sub_4.add(new Match(a));
        }
        match.add(macth_sub_1);
        match.add(macth_sub_2);
        match.add(macth_sub_3);
        match.add(macth_sub_4);
        return match;
      
    }
}
