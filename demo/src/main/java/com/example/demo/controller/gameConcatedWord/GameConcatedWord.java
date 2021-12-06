package com.example.demo.controller.gameConcatedWord;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cloudinary.Cloudinary;
import com.example.demo.model.Game;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.repository.DictionaryRepo;
import com.example.demo.repository.GamePlayReposity;
import com.example.demo.repository.GameReposity;
import com.example.demo.repository.NewsRespository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.GameConcatedWord.GameCWService;
import com.example.demo.game.gameConcateWord.*;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/cw")
@EnableScheduling
public class GameConcatedWord {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private GameReposity gameReposity;
    @Autowired
    private GamePlayReposity gamePlayReposity;
    @Autowired
    private GameCWService gameCWService;
    @Autowired
    private DictionaryRepo dictionaryRepo;
    
    private ArrayList<PlayerCW> playerCWs = new ArrayList<>();

    private final int START = 1;
    private final int CANCEL = 2;
    private final int PLAY_AGAIN = 3;
    private final int PLAY = 4;
    private final int MESSAGE = 5;
    private final int READY = 6;
    private final int END_GAME = 7;
    private final int RELOAD = 8;
    private final int LOSE = 13;

    private final int CORRECT = 1;
    private final int WRONG = 0; 
    private final int UNCHECK =-1;

    @PostMapping("/start")
    public String start(@RequestAttribute("id")int user_id){
        System.out.println("START"+user_id);
        User user = userRepository.findById(user_id);
        playerCWs.add(new PlayerCW(user, 0, START));
        return "Success";
    }

    @Scheduled(fixedDelay = 3000)
    public void setUpMatch(){
        if(playerCWs.size()>=4){
            Game game = gameReposity.findById(2);
            int match_id = gamePlayReposity.getMatchId()+1;
            try {
                while(playerCWs.size()>=4){
                    Date now =new Date();
                    gamePlayReposity.addRecord(game.getId(),(int)(now.getTime()/1000 ), playerCWs.get(0).getUser_id(), playerCWs.get(1).getUser_id(), playerCWs.get(2).getUser_id(),playerCWs.get(3).getUser_id(),0,0,0,0 ,1,0);
                    GameCWPlaying gameCWPlaying =new GameCWPlaying(match_id, playerCWs.get(0), playerCWs.get(1), playerCWs.get(2),playerCWs.get(3), START);
                    gameCWService.addMatch(gameCWPlaying);
                    for(int i =0 ;i<4;++i){
                        simpMessagingTemplate.convertAndSend("/topic/cw/wating/" + playerCWs.get(0).getUser_id(), new CWResponse(gameCWPlaying));
                        playerCWs.remove(0);
                    }
                    
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @MessageMapping("/cw/2/**")
    public void play(@RequestBody CWRequest cwRequest){
        System.out.println(cwRequest);
        int match_id = cwRequest.getMatch_id();
        int type = cwRequest.getType();
        GameCWPlaying gameCWPlaying = gameCWService.getMatch(match_id);
        if(gameCWPlaying == null)return;
        int status = cwRequest.getStatus();

        if(status == READY){
            PlayerCW player = gameCWPlaying.pickPlayerByType(cwRequest.getType());
            System.out.println(player);
            if(player!=null){
                player.setStatus(READY);
            }else return;
            if(!gameCWPlaying.isReady(READY))
                simpMessagingTemplate.convertAndSend("/topic/cw/2/" + match_id, new PlayerRes(gameCWPlaying.getPlayer(), READY,gameCWPlaying.getTurn()));
            else
                {
                    gameCWPlaying.setPlay();
                    gameCWPlaying.initWord();
                    simpMessagingTemplate.convertAndSend("/topic/cw/2/" + match_id, new CWResponse(gameCWPlaying));
                }
            return;
        }

        if(status == PLAY){
            PlayerCW player = gameCWPlaying.pickPlayerByType(type);
            if(player==null) return ;
            System.out.println("Turn"+gameCWPlaying.getTurn() +"Status user:"+player.getStatus());
            if(gameCWPlaying.getTurn() == type && player.getStatus() == PLAY){
                String word  = cwRequest.getMessage();
                Answer answer  = new Answer(word,type, UNCHECK);
                System.out.println("MAU Dau"+player.getBlood());
                // simpMessagingTemplate.convertAndSend("/topic/cw/2/" + match_id, new CWPlay(gameCWPlaying.getPlayer(),PLAY,answer));
                boolean check = gameCWPlaying.checkSimple(answer, type);
                gameCWPlaying.setUnCheck();
                System.out.println(check);
                if(!check){
                    player.setBlood(-1);
                    answer.setStatus(WRONG);
                    System.out.println("Wrong simple");
                }  
                else
                if(dictionaryRepo.checkWord(word) == 1){
                    player.setPoint(1);
                    answer.setStatus(CORRECT);
                    gameCWPlaying.setWord(word.charAt(word.length()-1));
                    gameCWPlaying.addAnswer(answer);
                    System.out.println("Correct");
                }else{
                    player.setBlood(-1);
                    answer.setStatus(WRONG);
                    gameCWPlaying.addAnswer(answer);
                    System.out.println("Wrong.......");
                }
                System.out.println("MAU"+player.getBlood());
                if(player.getBlood()<=0){
                    player.setStatus(LOSE);
                    System.out.println("Wrong.......");
                    if(gameCWPlaying.checkEndGame()){
                        System.out.println("END GAME");
                        simpMessagingTemplate.convertAndSend("/topic/cw/2/" + match_id, new CWPlay(gameCWPlaying.getPlayer(),END_GAME,answer));
                        return ;
                    }
                }
                gameCWPlaying.setPlay();
                gameCWPlaying.setTurn();
                simpMessagingTemplate.convertAndSend("/topic/cw/2/" + match_id, new CWPlay(gameCWPlaying.getPlayer(),PLAY,answer,gameCWPlaying.getTurn()));
            }
            return;
        }

        if(status == CANCEL){
            PlayerCW player = gameCWPlaying.pickPlayerByType(type);
            if(player==null)return;
            player.setStatus(CANCEL);
            if(gameCWPlaying.isCancel())gameCWService.deleteMatch(match_id);
            else{
                if(type==gameCWPlaying.getTurn())gameCWPlaying.setTurn();
                System.out.println("NEW TURN "+ gameCWPlaying.getTurn());
                simpMessagingTemplate.convertAndSend("/topic/cw/2/" + match_id, new PlayerRes(gameCWPlaying.getPlayer(), CANCEL,gameCWPlaying.getTurn()));
            }
            return;
        }

        if(status == PLAY_AGAIN){
            PlayerCW player = gameCWPlaying.pickPlayerByType(type);
            if(player==null)return;
            player.setStatus(PLAY_AGAIN);
            if(gameCWPlaying.isPlayAgain()){
                gameCWPlaying.setStatus(START);
                gameCWPlaying.reset();
                System.out.println("GAAAAA");
                simpMessagingTemplate.convertAndSend("/topic/cw/2/" + match_id, new CWResponse(gameCWPlaying));
            }
            else{
                simpMessagingTemplate.convertAndSend("/topic/cw/2/" + match_id, new PlayerRes(gameCWPlaying.getPlayer(), PLAY_AGAIN,gameCWPlaying.getTurn()));
            }
            return;
        }
    }
}
