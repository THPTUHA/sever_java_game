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
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsRespository newsRespository;
    @Autowired
    private CloudinaryService cloudinaryService ;
    @Autowired
    private UserRepository userRepository;

    private final int ERR_TITLE = 1;
    private final int ERR_CONTENT = 2;
    private final int ERR_DESCRIBES = 3;
    private final int SUCCESS = 0;

    @GetMapping("")
    public List<News> allNews(){
        List<News> list_news = newsRespository.findAll();
        return list_news;
    }

    @PostMapping("/detail")
    public News getNews(@RequestBody News news){
        System.out.println("OK");
        return newsRespository.findById(news.getId());
    }

    
    
}
