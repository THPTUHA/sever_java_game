package com.example.demo.controller;

import com.example.demo.model.Chat;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.repository.ChatReposity;
import com.example.demo.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gameplay")
public class ChatController {
   @Autowired
   private SimpMessagingTemplate simpMessagingTemplate;
   @Autowired
   private ChatReposity chatReposity;
   @Autowired
   private UserRepository userRepository;
   private List<Chat> chat;

   @GetMapping("/chat")
   public List<Chat> getStartChat(){
     chat =chatReposity.getChat();
     Collections.reverse(chat);
     return  chat;
   }
   @PostMapping("/chat/loading")
   public List<Chat> getAddChat(@RequestBody Message message){
     List<Chat> chat = chatReposity.loading(message.getPos());
     Collections.reverse(chat);
     return  chat;
   }
   @MessageMapping("/chat/**")
   public void chatting(Message message){
        System.out.println(message);
        User user = userRepository.findById(message.getUser_id());
        if(user!=null)
        {
          if(chat.size()>20) chat.remove(0);
          chat.add(new Chat(user,message.getMessage(),0));
          simpMessagingTemplate.convertAndSend("/topic/chat" , chat);
          System.out.println(message);
          chatReposity.addChat(user.getId(), message.getMessage(), new Date(), 0);
        }
   }
}
