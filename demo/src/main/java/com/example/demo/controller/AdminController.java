package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.cloudinary.Cloudinary;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.repository.NewsRespository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CloudinaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private NewsRespository newsRespository;
    @Autowired
    private CloudinaryService cloudinaryService ;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list_user")
    public List<User> listUser(){
        return userRepository.findAll();
    }

    @PostMapping("/list_user/update")
    public String updateListUser(@RequestBody List<User> list_user){
        for(User a:list_user){
            try {
                userRepository.save(a);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return "FUCK";
    }

    @PostMapping("/news/image")
    public String getLink(@RequestParam("image") MultipartFile background_image){
        try {
            String link = cloudinaryService.uploadImage(background_image);
            return link;
        } catch (Exception e) {
            return "Some thing worng!!";
        }
    }
    @PostMapping("/news/create")
    public String creatNews(@RequestParam("background_image") MultipartFile background_image,@RequestParam("title")String title,
    @RequestParam("describes")String describes,@RequestParam("content")String content,@RequestAttribute("id")int user_id){
        if(background_image == null) return "Thiếu background image!!";
        if(title =="") return "Thiếu title!!";
        if(describes=="") return "Thiếu mô tả";
        if(content=="") return "Thiếu nội dung";
        try {
            User user = userRepository.findById(user_id);
            String link = cloudinaryService.uploadImage(background_image);
            newsRespository.save(new News(title,describes,link,content,user_id,user.getFirst_name()+" "+user.getLast_name()));
        } catch (Exception e) {
            return "Some thing worng!!";
        }
        return "SUCCESS";
    } 
    
    
}
