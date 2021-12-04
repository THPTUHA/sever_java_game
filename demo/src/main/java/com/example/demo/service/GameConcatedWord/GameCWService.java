package com.example.demo.service.GameConcatedWord;

import org.springframework.stereotype.Service;
import com.example.demo.game.gameConcateWord.*;
import java.util.*;

@Service
public class GameCWService {
    private Map<Integer,GameCWPlaying> playing = new HashMap<>();
    
    public void addMatch(GameCWPlaying gameCWPlaying){
        playing.put(gameCWPlaying.getMatch_id(),gameCWPlaying);
    }
    public void deleteMatch(int match_id){
        playing.remove(match_id);
    }

    public GameCWPlaying getMatch(int match_id){
        return playing.get(match_id);
    }
}
