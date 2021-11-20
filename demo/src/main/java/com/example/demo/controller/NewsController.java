package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.example.demo.model.News;
import com.example.demo.repository.NewsRespository;
import com.example.demo.service.CloudinaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsRespository newRespository;
    @Autowired
    private CloudinaryService cloudinaryService ;

    private final int ERR_TITLE = 1;
    private final int ERR_CONTENT = 2;
    private final int ERR_DESCRIBES = 3;
    private final int SUCCESS = 0;
    @PostMapping("/create")
    public int creatNews(@RequestParam("image") MultipartFile image){
        System.out.println(cloudinaryService.uploadImage(image));
        // if(news.getTitle() =="") return ERR_TITLE;
        // if(news.getContent() =="") return ERR_CONTENT;
        // if(news.getDescribes() =="") return ERR_DESCRIBES;
        // newRespository.save(news);
        return SUCCESS;
    } 
    
    
}
