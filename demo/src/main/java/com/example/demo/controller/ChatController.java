package com.example.demo.controller;

import com.example.demo.model.Chat;
import com.example.demo.repository.ChatReposity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gameplay")
public class ChatController {
   @Autowired
   private SimpMessagingTemplate simpMessagingTemplate;
   @Autowired
   private ChatReposity chatReposity;
   @GetMapping("/chat")
   public List<Chat> getStartChat(){
     return  chatReposity.getChat();
   }
   @MessageMapping("/chat/req")
   public void chatting(Chat chat){
        simpMessagingTemplate.convertAndSend("/chat/res" , chat);
   }
}
