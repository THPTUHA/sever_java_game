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

    private Map<Integer, GameXOPlaying> playing = new HashMap<>();

    private GamePlay getMatch() {
        return gamePlayReposity.getOneGamePlay(gameXO.getUser_num(), gameXO.getId());
    }

    public int creatGame(int id_user1) {
        gamePlayReposity.save(new GamePlay(gameXO.getId(), id_user1));
        return gamePlayReposity.getOneMatch(id_user1).getId();
    }

    public GameXORes connectGame(GameXORequest gameXORequest) {
        GamePlay macth = getMatch();
        final int id_user = gameXORequest.getId_user();
        if (macth == null) {
            return new GameXORes(creatGame(gameXORequest.getId_user()), 1, 0);
        }
        final int id_match = macth.getId();
        User user1=userRepository.findById(macth.getStatus());
        User user2=userRepository.findById(id_user);

        playing.put(id_match, new GameXOPlaying(id_match, user1, user2,-1));
        macth.setPlayer(id_user);
        gamePlayReposity.updatePlayGame(id_match, macth.getPlayer(), -1, 2);
        return new GameXORes(id_match,2,1);
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
