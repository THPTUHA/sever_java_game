package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.gamexo.GameXOPlaying;
import com.example.demo.gamexo.GameXORequest;
import com.example.demo.gamexo.InfoGame;
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

    public void addMatch(GameXOPlaying gameXOPlaying){
        playing.put(gameXOPlaying.getMatch_id(), gameXOPlaying);
    }

    public GameXOPlaying matchPlaying(int match_id) {
        return playing.get(match_id);
    }

    public void deletePlaying(int match_id) {
        playing.remove(match_id);
    }
   
    public void printPlaying() {
        for (Map.Entry<Integer, GameXOPlaying> g : playing.entrySet()) {
            System.out.println(g);
        }
    }
}
