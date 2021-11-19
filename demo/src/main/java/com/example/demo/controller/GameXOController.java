package com.example.demo.controller;

import com.example.demo.gamexo.GameXOMessage;
import com.example.demo.gamexo.GameXOPlayer;
import com.example.demo.gamexo.GameXOPlaying;
import com.example.demo.gamexo.GameXORequest;
import com.example.demo.gamexo.GameXORes;
import com.example.demo.gamexo.InfoGame;
import com.example.demo.gamexo.Play;
import com.example.demo.gamexo.ReponsePlayer;
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
  private final int READY = 6;
  private final int END_GAME = 7;
  private final int RELOAD = 8;

  @PostMapping("/start")
  public InfoGame startGame(@RequestBody GameXORequest gameXORequest) {
    System.out.println("FUCCCCCCK");
    return gameXOService.connectGame(gameXORequest);
  }

  @PostMapping("/reload")
  public GameXORes loadGame(@RequestBody GameXORequest gameXORequest) {
    System.out.println(gameXORequest);
    GameXOPlaying gameXOPlaying =gameXOService.matchPlaying(gameXORequest.getId_match());
    return new GameXORes(gameXOPlaying,gameXORequest.getId_user(),RELOAD);
  }

  @MessageMapping("/xo/1/**")
  public  void playing(GameXORequest gameXORequest) throws Exception {
    int id_match = gameXORequest.getId_match();
    int status = gameXORequest.getStatus();
    GameXOPlaying gameXOPlaying = gameXOService.matchPlaying(id_match);

    if (gameXOPlaying == null)
      return;

    if (status == START) {
      gameXOPlaying.startPlay(START);
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(gameXOPlaying));
      return;
    }

    if(status == READY){
      GameXOPlayer player = gameXOPlaying.getPlayerByType(gameXORequest.getType());
      player.setStatus(READY);
      gameXOPlaying.setStatus(READY);
      if(!gameXOPlaying.isGameXOReady(READY)){
        System.out.println(gameXOPlaying);
        simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new ReponsePlayer(gameXOPlaying));
        return;
      }
      gameXOPlaying.startPlay(PLAY);
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(gameXOPlaying));
      return;
    }

    if(status == MESSAGE){
      System.out.println("Messs");
      GameXOPlayer player = gameXOPlaying.getPlayerByType(gameXORequest.getType());
      gameXOPlaying.setStatus(MESSAGE);
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXOMessage(player, gameXORequest.getMessage(), MESSAGE));
      return ;
    }

    if (status== PLAY&& gameXORequest.getType() == gameXOPlaying.getTurn()) {
      if(!gameXORequest.getRandom())
        gameXOPlaying.play(gameXORequest.getCoordinateX(), gameXORequest.getCoordinateY());
      else {
        gameXOPlaying.randomPlay();
      }
      gameXOPlaying.setTurn();
      int winner = gameXOPlaying.winner();
      if(winner!=0){
        gameXOPlaying.undateAfterGame(winner);
        GameXOPlayer player1=gameXOPlaying.getPlayer1();
        GameXOPlayer player2=gameXOPlaying.getPlayer2();
        userRepository.updateGoldExp(player1.getGold(), player1.getExp(),player1.getId());
        userRepository.updateGoldExp(player2.getGold(), player2.getExp(),player2.getId());
        gameXOPlaying.playAgain(END_GAME);
      }
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match,new Play(gameXOPlaying));
      return;

    }

     // khi 1 player huỷ trận
     if (status == CANCEL) {
      gameXOPlaying.getPlayerByType(gameXORequest.getType()).setStatus(CANCEL);
      if(gameXOPlaying.getStatus()!= CANCEL){
        gameXOPlaying.setStatus(CANCEL);
        gamePlayReposity.finishPlayGame(id_match, CANCEL);
        userRepository.updateStatus(0, gameXOPlaying.getPlayer1().getId());
        userRepository.updateStatus(0, gameXOPlaying.getPlayer2().getId());
      }else{
         gameXOService.deletePlaying(id_match);
      }
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new ReponsePlayer(gameXOPlaying));
      return;
    }

    // khi user sẵn sàng trận mới
    if (status == PLAY_AGAIN) {
      GameXOPlayer player = gameXOPlaying.getPlayerByType(gameXORequest.getType());
      player.setStatus(PLAY_AGAIN);
      if(!gameXOPlaying.isGameXOPlayAgain(PLAY_AGAIN)){
        gameXOPlaying.setStatus(PLAY_AGAIN);
        simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new ReponsePlayer(gameXOPlaying));
        return;
      }
        gameXOPlaying.setBoard();
        gameXOPlaying.playAgain(START);
        simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(gameXOPlaying));
      return;
    }

  }
}
