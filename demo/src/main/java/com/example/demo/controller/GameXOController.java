package com.example.demo.controller;

import com.example.demo.gamexo.GameXOPlayer;
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
    if (gameXORequest.getStatus() == 1) {
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(gameXOPlaying));
      return;
    }

    // khi 1 player huỷ trận
    if (gameXORequest.getStatus() == 2) {
      GameXOPlayer player = gameXOPlaying.getPlayer(gameXORequest.getType());
      gameXOService.deletePlaying(id_match);
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(player, gameXORequest.getStatus()));
      return;
    }

    // khi 1 đổi thủ sẵn sàng trận mới
    if (gameXORequest.getStatus() == 3) {
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(3));
      return;
    }

    // khi người chơi chờ sau khi đối thủ hủy trận
    if (gameXORequest.getStatus() == 4) {
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match,
          new GameXORes(gameXOService.creatGame(gameXORequest.getId_user()), 1, 0));
      return;

    }

    // cả 2 người chơi sãn sàng cho trận mới
    if (gameXORequest.getStatus() == 5) {
      gameXOPlaying.setBoard();
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(gameXOPlaying));
      return;

    }

    // khi người chơi hủy trận sau khi đối thủ hủy trận
    if (gameXORequest.getStatus() == 6) {
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match, new GameXORes(6));
      return;

    }

    if (gameXOPlaying != null && gameXORequest.getType() == gameXOPlaying.getTurn()) {
      gameXOPlaying.play(gameXORequest.getCoordinateX(), gameXORequest.getCoordinateY());
      gameXOPlaying.setTurn();
      int winner = gameXOPlaying.winner();
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + id_match,
          new GameXORes(id_match, gameXOPlaying.getBoard(), winner));
      return;

    }

  }
}
