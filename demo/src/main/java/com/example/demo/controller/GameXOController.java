package com.example.demo.controller;

import com.example.demo.gamexo.GameXOPlayer;
import com.example.demo.gamexo.GameXOPlaying;
import com.example.demo.gamexo.GameXORequest;
import com.example.demo.gamexo.GameXORes;
import com.example.demo.repository.GamePlayReposity;
import com.example.demo.repository.UserRepository;
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
  @Autowired
  private GamePlayReposity gamePlayReposity;
  @Autowired
  private UserRepository userRepository;

  private final int START = 1;
  private final int CANCEL = 2;
  private final int PLAY_AGAIN = 3;
  private final int PLAY = 4;
  private final int MESSAGE = 5;

  @PostMapping("/start")
  public GameXORes startGame(@RequestBody GameXORequest gameXORequest) {
    return gameXOService.connectGame(gameXORequest);
  }

  @MessageMapping("/xo/1/**")
  public void playing(GameXORequest gameXORequest) throws Exception {
    System.out.println(gameXORequest);
    int id_match = gameXORequest.getId_match();
    GameXOPlaying gameXOPlaying = gameXOService.matchPlaying(id_match);

    if (gameXOPlaying == null)
      return;

    if(gameXORequest.getStatus()== MESSAGE){
      System.out.println("Messs");
      GameXOPlayer player = gameXOPlaying.getPlayer(gameXORequest.getType());
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(player,gameXORequest.getMessage(),MESSAGE));
      return ;
    }

    if (gameXORequest.getStatus() == START) {
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(gameXOPlaying));
      return;
    }

    // khi 1 player huỷ trận
    if (gameXORequest.getStatus() == CANCEL) {
      if(gameXOPlaying.getStatus()==1){
        gameXOPlaying.setStatus(CANCEL);
        gamePlayReposity.finishPlayGame(id_match, CANCEL);
      }else{
         gameXOService.deletePlaying(id_match);
      }
      GameXOPlayer player = gameXOPlaying.getPlayer(gameXORequest.getType());
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(player, gameXORequest.getStatus()));
      return;
    }

    // khi user sẵn sàng trận mới
    if (gameXORequest.getStatus() == PLAY_AGAIN) {
      gameXOPlaying.setNumber_user_play_again();
      if(gameXOPlaying.getNumber_user_play_again()==1){
        GameXOPlayer player = gameXOPlaying.getPlayer(gameXORequest.getType());
       simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(player, gameXORequest.getStatus()));
      }else{
        simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(
          id_match,new int[3][3],0,gameXOPlaying.getPlayer1(),gameXOPlaying.getPlayer2(),START
        ));
      }
      return;
    }


    if (gameXORequest.getStatus()== PLAY&& gameXORequest.getType() == gameXOPlaying.getTurn()) {
      gameXOPlaying.play(gameXORequest.getCoordinateX(), gameXORequest.getCoordinateY());
      gameXOPlaying.setTurn();
      int winner = gameXOPlaying.winner();
      if(winner!=0){
        gameXOPlaying.undateAfterGame(winner);
        GameXOPlayer player1=gameXOPlaying.getPlayer1();
        GameXOPlayer player2=gameXOPlaying.getPlayer2();
        userRepository.updateGoldExp(player1.getGold(), player1.getExp(),player1.getId());
        userRepository.updateGoldExp(player2.getGold(), player2.getExp(),player2.getId());
        simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match,
          new GameXORes(id_match, gameXOPlaying.getBoard(), winner,player1, player2,PLAY));
        return;
      }
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match,
          new GameXORes(id_match, gameXOPlaying.getBoard(), winner,PLAY));
      return;

    }

  }
}
