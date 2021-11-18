package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.gamexo.GameXOPlaying;
import com.example.demo.gamexo.GameXORequest;
import com.example.demo.gamexo.GameXORes;
import com.example.demo.model.GamePlay;
import com.example.demo.model.GameXO;
import com.example.demo.model.User;
import com.example.demo.repository.GamePlayReposity;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameXOService {
    @Autowired
    private GamePlayReposity gamePlayReposity;
    @Autowired
    private GameXO gameXO;
    @Autowired
    private UserRepository userRepository;

    private final int START = 1;
    private final int WAIT = -1;

    private Map<Integer, GameXOPlaying> playing = new HashMap<>();

    private GamePlay getMatch() {
        return gamePlayReposity.getOneGamePlay(gameXO.getUser_num(), gameXO.getId());
    }

    public int creatGame(int id_user1) {
        GamePlay gamePlay= gamePlayReposity.save(new GamePlay(gameXO.getId(), id_user1,WAIT));
        return gamePlay.getId();
    }

    public GameXORes connectGame(GameXORequest gameXORequest) {
        GamePlay macth = getMatch();
        final int id_user = gameXORequest.getId_user();
        if (macth == null) {
            return new GameXORes(creatGame(gameXORequest.getId_user()), 1, WAIT);
        }
        final int id_match = macth.getId();
        User user1=userRepository.findById(Integer.parseInt(macth.getPlayer()));
        User user2=userRepository.findById(id_user);
        user1.setStatus(id_match); userRepository.save(user1);
        user2.setStatus(id_match); userRepository.save(user2);
        playing.put(id_match, new GameXOPlaying(id_match, user1, user2,START));
        macth.setPlayer(id_user);
        gamePlayReposity.updatePlayGame(id_match, macth.getPlayer(), START,gameXO.getUser_num());
        return new GameXORes(id_match,2,START);
    }

    public GameXOPlaying matchPlaying(int id_match) {
        return playing.get(id_match);
    }

    public void deletePlaying(int id_match) {
        playing.remove(id_match);
    }
    // public void printWait(){
    // for(GameXOPlaying g:wait){
    // System.out.println(g);
    // }
    // }
    public void printPlaying() {
        for (Map.Entry<Integer, GameXOPlaying> g : playing.entrySet()) {
            System.out.println(g);
        }
    }
}
