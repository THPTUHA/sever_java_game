package com.example.demo.controller;

import java.util.ArrayList;

import com.example.demo.gamexo.GameXOPlayer;
import com.example.demo.gamexo.GameXOPlaying;
import com.example.demo.gamexo.GameXORequest;
import com.example.demo.gamexo.GameXORes;
import com.example.demo.gamexo.InfoGame;
import com.example.demo.gamexo.Play;
import com.example.demo.gamexo.ReponsePlayer;
import com.example.demo.model.Game;
import com.example.demo.model.GamePlay;
import com.example.demo.model.Message;
import com.example.demo.model.RecordMatch;
import com.example.demo.model.User;
import com.example.demo.repository.GamePlayReposity;
import com.example.demo.repository.GameReposity;
import com.example.demo.repository.RecordMatchReposity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.GameXOService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xo")
@EnableScheduling
public class GameXOController {
  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;
  @Autowired
  private GameXOService gameXOService;
  @Autowired
  private GamePlayReposity gamePlayReposity;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RecordMatchReposity recordMatchReposity;
  @Autowired
  private GameReposity gameReposity;

  private ArrayList<User>user_waiting = new ArrayList<>();

  private final int START = 1;
  private final int CANCEL = 2;
  private final int PLAY_AGAIN = 3;
  private final int PLAY = 4;
  private final int MESSAGE = 5;
  private final int READY = 6;
  private final int END_GAME = 7;
  private final int RELOAD = 8;


  @PostMapping("/start")
  public String startGame(@RequestAttribute("id") int user_id) {
    System.out.println("FUCCCCCCK");
    try {
      user_waiting.add(userRepository.findById(user_id));
    } catch (Exception e) {
      return "ERR";
    }
    return "SUCCCESS";
  }

  @Scheduled(fixedDelay = 3000)
  public void handleMatch(){
    Game game = gameReposity.findById(1);
    System.out.println("RUN");
    while(user_waiting.size()>1){
      RecordMatch recordMatch =  recordMatchReposity.save(new RecordMatch());
      String[] opponent =new String[2];
      for(int i =0 ;i<2;++i){
        User user = user_waiting.get(1-i);
        opponent[i] ="id"+"@#$"+ user.getId()+"@#$first_name@#$"+user.getFirst_name()+"@#$last_name@#$"
        +user.getLast_name()+"@#$avatar@#$"+user.getAvatar();
      }
      for(int i =0 ;i<2;++i){
        gamePlayReposity.addRecord(game.getId(), recordMatch.getId(), user_waiting.get(i).getId(), opponent[i], 0);
        userRepository.updateStatus(recordMatch.getId(),user_waiting.get(i).getId());
      }
      GameXOPlaying gameXOPlaying = new GameXOPlaying(recordMatch.getId(), user_waiting.get(0), user_waiting.get(1), START);
      gameXOService.addMatch(gameXOPlaying);
      for(int i =0 ;i<2;++i){
        simpMessagingTemplate.convertAndSend("/topic/xo/wating/" + user_waiting.get(i).getId(), new GameXORes(gameXOPlaying));
      }
      System.out.print(recordMatch);
      user_waiting.remove(0);
      user_waiting.remove(0);
    }
  }
  @PostMapping("/reload")
  public GameXORes loadGame(@RequestBody GameXORequest gameXORequest) {
    System.out.println(gameXORequest);
    GameXOPlaying gameXOPlaying =gameXOService.matchPlaying(gameXORequest.getMatch_id());
    return new GameXORes(gameXOPlaying);
  }

  @MessageMapping("/xo/1/**")
  public  void playing(GameXORequest gameXORequest) throws Exception {
    int match_id = gameXORequest.getMatch_id();
    int status = gameXORequest.getStatus();
    GameXOPlaying gameXOPlaying = gameXOService.matchPlaying(match_id);

    if (gameXOPlaying == null)
      return;

    if (status == START) {
      gameXOPlaying.startPlay(START);
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id, new GameXORes(gameXOPlaying));
      return;
    }

    if(status == READY){
      GameXOPlayer player = gameXOPlaying.getPlayerByType(gameXORequest.getType());
      player.setStatus(READY);
      gameXOPlaying.setStatus(READY);
      if(!gameXOPlaying.isGameXOReady(READY)){
        System.out.println("READAY");
        simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id, new ReponsePlayer(gameXOPlaying));
        return;
      }
      gameXOPlaying.startPlay(PLAY);
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id, new GameXORes(gameXOPlaying));
      return;
    }

    if(status == MESSAGE){
      System.out.println("Messs");
      GameXOPlayer player = gameXOPlaying.getPlayerById(gameXORequest.getUser_id());
      gameXOPlaying.setStatus(MESSAGE);
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id, new Message(player.getId(),player.getName(),player.getAvatar(),gameXORequest.getMessage(),MESSAGE));
      return ;
    }

    if (status== PLAY&& gameXORequest.getType() == gameXOPlaying.getTurn()) {
      if(!gameXORequest.getRandom())
        gameXOPlaying.play(gameXORequest.getCoordinateX(), gameXORequest.getCoordinateY());
      else {
        gameXOPlaying.randomPlay();
      }
      gameXOPlaying.setStatus(PLAY);
      gameXOPlaying.setTurn();
      int winner = gameXOPlaying.winner();
      if(winner!=0){
        gameXOPlaying.undateAfterGame(winner);
        gameXOPlaying.playAgain(END_GAME);
      }
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id,new Play(gameXOPlaying));
      if(winner!=0){
        GameXOPlayer player1=gameXOPlaying.getPlayer1();
        GameXOPlayer player2=gameXOPlaying.getPlayer2();
        String data = player1.getId()+"@#$"+player1.getPoint()+"@#$"+player2.getId()+"@#$"+player2.getPoint();
        recordMatchReposity.updateData(data, match_id);
        userRepository.updateGoldExp(player1.getGold(), player1.getExp(),player1.getId());
        userRepository.updateGoldExp(player2.getGold(), player2.getExp(),player2.getId());
      }
      return;

    }

     // khi 1 player huỷ trận
     if (status == CANCEL) {
      GameXOPlayer player = gameXOPlaying.getPlayerByType(gameXORequest.getType());
      player.setStatus(CANCEL);
      if(gameXOPlaying.getStatus()!= CANCEL){
        gameXOPlaying.setStatus(CANCEL);
      }else{
         gameXOService.deletePlaying(match_id);
         recordMatchReposity.updateStatus(END_GAME, match_id);
      }
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id, new ReponsePlayer(gameXOPlaying));
      userRepository.updateStatus(0, player.getId());
      return;
    }

    // khi user sẵn sàng trận mới
    if (status == PLAY_AGAIN) {
      GameXOPlayer player = gameXOPlaying.getPlayerByType(gameXORequest.getType());
      player.setStatus(PLAY_AGAIN);
      if(!gameXOPlaying.isGameXOPlayAgain(PLAY_AGAIN)){
        gameXOPlaying.setStatus(PLAY_AGAIN);
        simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id, new ReponsePlayer(gameXOPlaying));
        return;
      }
        gameXOPlaying.setBoard();
        gameXOPlaying.playAgain(START);
        simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id, new GameXORes(gameXOPlaying));
      return;
    }

  }
}
