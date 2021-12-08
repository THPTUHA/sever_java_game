package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

import com.cloudinary.Cloudinary;
import com.example.demo.help.ListUser;
import com.example.demo.help.Response;
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

    private final int ERROR = 14;
    private final int SUCCESS = 15;

    @PostMapping("/list_user")
    public ListUser listUser(@RequestParam("pos")int pos,@RequestParam("selection")String selection){
        try {
            if(selection==null||selection.compareTo("NEWEST")==0){
                int sz = userRepository.numberUser();
                List<User> users = userRepository.loading(pos*5);
                return new ListUser(users,sz);
            }else {
                int sz = userRepository.numberUserByRole(selection);
                List<User> users = userRepository.loadingByRole(pos*5,selection);
                return new ListUser(users,sz);
            }
        } catch (Exception e) {
           System.out.println(e);
        }
        return null;
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

    @PostMapping("/news/update")
    public Response udpateNews(@RequestParam("background_image") MultipartFile background_image,@RequestParam("title")String title,
    @RequestParam("describes")String describes,@RequestParam("content")String content,@RequestParam("id")int id,@RequestAttribute("id")int user_id){
        if(background_image == null) return new Response(ERROR,"Thiếu ảnh");
        if(title =="") return new Response(ERROR,"Thiếu tiêu đề!!");
        if(describes=="")  return new Response(ERROR,"Thiếu mô tả!!");
        if(content=="") return new Response(ERROR,"Thiếu nội dung!!");
        try {
            String link = cloudinaryService.uploadImage(background_image);
            Date now = new Date();
            long  time =(now.getTime()/1000);
            newsRespository.updateNews(id,user_id, content, title, describes,time, 1, link);
        } catch (Exception e) {
            System.out.println(e);
            return new Response(ERROR,"Something wrong!!");
        }
        return new Response(SUCCESS,"Cập nhật thành công!!");
    } 

    @PostMapping("/news/delete")
    public Response deleteNews(@RequestParam("id")int id){
        try {
            newsRespository.deleteComment(id);
            newsRespository.deleteNews(id);
        } catch (Exception e) {
            System.out.println(e);
            return new Response(ERROR,"Something wrong!!");
        }
        return new Response(SUCCESS,"Xóa thành công!!");
    }

    @PostMapping("/news/create")
    public Response creatNews(@RequestParam("background_image") MultipartFile background_image,@RequestParam("title")String title,
    @RequestParam("describes")String describes,@RequestParam("content")String content,@RequestAttribute("id")int user_id){
        if(background_image == null) return new Response(ERROR,"Thiếu ảnh");
        if(title =="") return new Response(ERROR,"Thiếu tiêu đề!!");
        if(describes=="")  return new Response(ERROR,"Thiếu mô tả!!");
        if(content=="") return new Response(ERROR,"Thiếu nội dung!!");
        try {
            String link = cloudinaryService.uploadImage(background_image);
            Date now = new Date();
            long  time =(now.getTime()/1000);
            newsRespository.addNews(user_id, content, title, describes,time,time, 1, link, 0, 0);
        } catch (Exception e) {
            System.out.println(e);
            return new Response(ERROR,"Something wrong!!");
        }
        return new Response(SUCCESS,"Đăng bài thành công");
    } 
    
    
}
