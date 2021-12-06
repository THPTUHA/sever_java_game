package com.example.demo.controller.News;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

import com.cloudinary.Cloudinary;
import com.example.demo.model.CommentNews;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.help.*;
import com.example.demo.repository.CommentNewsRepo;
import com.example.demo.repository.NewsRespository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CloudinaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    @Autowired
    private CommentNewsRepo commentNewsRepo;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private final int ERROR = 14;
    private final int SUCCESS = 15;

    @GetMapping("")
    public List<News> allNews(){
        List<News> list_news = newsRespository.getNews(0);
        return list_news;
    }

    @PostMapping("/detail")
    public News getNews(@RequestBody News req){
        try {
            News news = newsRespository.findById(req.getId());
            return news;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PostMapping("/comment")
    public void comment(@RequestBody Comment comment,@RequestAttribute("id")int user_id){
        System.out.println(comment);
        try {
            Date  now = new Date();
            long since = (now.getTime()/1000);
            comment.setSince(since);
            simpMessagingTemplate.convertAndSend("/topic/news/comment/" + comment.getNews_id(),comment);
            commentNewsRepo.addComment(comment.getNews_id(),user_id,comment.getContent(), since,comment.getStatus());
        } catch (Exception e) {
            simpMessagingTemplate.convertAndSend("/topic/news/comment/" + comment.getNews_id(),new Response(ERROR,"Some thing wrong!!"));
        }
    }
    
    
}
