package com.example.demo.controller;

import com.example.demo.gamexo.GameXOPlaying;
import com.example.demo.gamexo.GameXORequest;
import com.example.demo.gamexo.GameXORes;
import com.example.demo.service.GameXOService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xo")
public class GameXOController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private GameXOService gameXOService;

    private int id_room_playing=1;
    @PostMapping("/start")
    public GameXORes startGame(@RequestBody GameXORequest gameXORequest){
        System.out.println(gameXORequest);
        gameXOService.printWait();
        gameXOService.printPlaying();
        if(!gameXOService.isWating()){
            gameXOService.creatGame(this.id_room_playing, gameXORequest.getId_user());
            return new GameXORes(this.id_room_playing,1);
        }
       int id_match = gameXOService.connectGame(gameXORequest.getId_user());
       this.id_room_playing++;
       return new GameXORes(id_match,2);
    }


    @MessageMapping("/xo/1/**")
    public void playing(GameXORequest gameXORequest) throws Exception {
    System.out.println(gameXORequest);
    GameXOPlaying gameXOPlaying = gameXOService.matchPlaying(gameXORequest.getId_match());
    if(gameXORequest.getType()==gameXOPlaying.getTurn()){
      gameXOPlaying.play(gameXORequest.getCoordinateX(), gameXORequest.getCoordinateY());
      gameXOPlaying.setTurn();
      int winner=gameXOService.winner(gameXOPlaying);
      simpMessagingTemplate.convertAndSend("/topic/xo/1/"+gameXORequest.getId_match(),new GameXORes(gameXORequest.getId_match(),gameXOPlaying.getBoard(),winner));
    }
  }
}
