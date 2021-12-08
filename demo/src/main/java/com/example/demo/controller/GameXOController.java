package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;

import com.example.demo.game.gamexo.GameXOPlayer;
import com.example.demo.game.gamexo.GameXOPlaying;
import com.example.demo.game.gamexo.GameXORequest;
import com.example.demo.game.gamexo.GameXORes;
import com.example.demo.game.gamexo.InfoGame;
import com.example.demo.game.gamexo.Play;
import com.example.demo.game.gamexo.ReponsePlayer;
import com.example.demo.model.Game;
import com.example.demo.model.GamePlay;
import com.example.demo.model.IndexGame;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.repository.GamePlayReposity;
import com.example.demo.repository.GameReposity;
import com.example.demo.repository.IndexGameRepo;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.GameXOService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
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
  private GameReposity gameReposity;
  @Autowired
  private IndexGameRepo indexGameRepo;

  private ArrayList<User>user_waiting = new ArrayList<>();

  private final int START = 1;
  private final int CANCEL = 2;
  private final int PLAY_AGAIN = 3;
  private final int PLAY = 4;
  private final int MESSAGE = 5;
  private final int READY = 6;
  private final int END_GAME = 7;
  private final int RELOAD = 8;
  private final int TIME_CHECK =11;
  private final int TIME_SET =12;


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
    if(user_waiting.size()>1){
      Game game = gameReposity.findById(1);
      IndexGame indexGame = indexGameRepo.save(new IndexGame(2));
      int match_id = indexGame.getId();
    System.out.println("RUN");
    while(user_waiting.size()>1){
      Date now =new Date();
      gamePlayReposity.addRecord(match_id,game.getId(),now.getTime()/1000 , user_waiting.get(0).getId(), user_waiting.get(1).getId(), 1,1,0,0,0,0 ,1,0);
      userRepository.updateStatus(match_id,user_waiting.get(0).getId());
      userRepository.updateStatus(match_id,user_waiting.get(1).getId());
      GameXOPlaying gameXOPlaying = new GameXOPlaying(match_id, user_waiting.get(0), user_waiting.get(1), START);
      gameXOService.addMatch(gameXOPlaying);
      for(int i =0 ;i<2;++i){
        simpMessagingTemplate.convertAndSend("/topic/xo/wating/" + user_waiting.get(0).getId(), new GameXORes(gameXOPlaying));
        user_waiting.remove(0);
      }
      match_id++;
    }
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
    System.out.print(gameXORequest);
    int match_id = gameXORequest.getMatch_id();
    int status = gameXORequest.getStatus();
    GameXOPlaying gameXOPlaying = gameXOService.matchPlaying(match_id);

    if (gameXOPlaying == null)
      return;
    
    if(status == TIME_CHECK ){
      int check = gameXOPlaying.checkTime(gameXORequest.getTime());
      if(check == 0){
        simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id, new GameXORes(gameXOPlaying.getTime_run(),TIME_SET));
      }else if(check == -1){
        gameXOPlaying.randomPlay();
        gameXOPlaying.setTurn();
        int winner = gameXOPlaying.winner();
        if(winner!=0){
          gameXOPlaying.undateAfterGame(winner);
          gameXOPlaying.playAgain(END_GAME);
        }
        gameXOPlaying.setTime();
        simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id,new Play(gameXOPlaying));
        if(winner!=0){
          GameXOPlayer player1=gameXOPlaying.getPlayer1();
          GameXOPlayer player2=gameXOPlaying.getPlayer2();
          gamePlayReposity.updatePlayGame(match_id, player1.getPoint(), player2.getPoint(), 0, 0, START);
          userRepository.updateGoldExp(player1.getGold(), player1.getExp(),player1.getId());
          userRepository.updateGoldExp(player2.getGold(), player2.getExp(),player2.getId());
        }
      }
      return;
    }

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
      gameXOPlaying.setTime();
      return;
    }

    if(status == MESSAGE){
      GameXOPlayer player = gameXOPlaying.getPlayerById(gameXORequest.getUser_id());
      gameXOPlaying.setStatus(MESSAGE);
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id, new Message(player.getId(),player.getName(),player.getAvatar(),gameXORequest.getMessage(),MESSAGE));
      return ;
    }

    if (status== PLAY&& gameXORequest.getType() == gameXOPlaying.getTurn()) {
      gameXOPlaying.play(gameXORequest.getCoordinateX(), gameXORequest.getCoordinateY());
      gameXOPlaying.setStatus(PLAY);
      gameXOPlaying.setTurn();
      int winner = gameXOPlaying.winner();
      if(winner!=0){
        gameXOPlaying.undateAfterGame(winner);
        gameXOPlaying.playAgain(END_GAME);
      }
      gameXOPlaying.setTime();
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id,new Play(gameXOPlaying));
      if(winner!=0){
        GameXOPlayer player1=gameXOPlaying.getPlayer1();
        GameXOPlayer player2=gameXOPlaying.getPlayer2();
        gamePlayReposity.updatePlayGame(match_id, player1.getPoint(), player2.getPoint(), 0, 0, START);
        userRepository.updateGoldExp(player1.getGold(), player1.getExp(),player1.getId());
        userRepository.updateGoldExp(player2.getGold(), player2.getExp(),player2.getId());
      }
      return;

    }

     if (status == CANCEL) {
      GameXOPlayer player = gameXOPlaying.getPlayerByType(gameXORequest.getType());
      if(gameXOPlaying.getStatus() ==PLAY){
        gameXOPlaying.undateAfterGame(3 - player.getType());
      }
      player.setStatus(CANCEL);
      if(gameXOPlaying.getStatus()!= CANCEL){
        gameXOPlaying.setStatus(CANCEL);
        gamePlayReposity.updatePlayGame(match_id, gameXOPlaying.getPlayer1().getPoint(), gameXOPlaying.getPlayer2().getPoint(), 0, 0,CANCEL);
      }else{
         gameXOService.deletePlaying(match_id);
      }
      simpMessagingTemplate.convertAndSend("/topic/xo/1/" + match_id, new ReponsePlayer(gameXOPlaying));
      userRepository.updateStatus(0, player.getId());
      return;
    }

    
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
