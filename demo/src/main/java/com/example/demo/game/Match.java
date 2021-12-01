package com.example.demo.game;

import java.util.Date;

import com.example.demo.model.GamePlay;
import com.example.demo.model.User;

public class Match {
    private Player player_1;
    private Player player_2;
    private Player player_3;
    private Player player_4;
    private int game_id;
    private int winner;
    private int start_time;
    private String data;
    private int staus;
    private String game_name;

    public Match(GamePlay gameplay) {
        this.player_1 = new Player(gameplay.getUser_1(),gameplay.getPoint_1());
        this.player_2 = new Player(gameplay.getUser_2(),gameplay.getPoint_2());
        this.player_3 = new Player(gameplay.getUser_3(),gameplay.getPoint_3());
        this.player_4 = new Player(gameplay.getUser_4(),gameplay.getPoint_4());
        this.game_id = gameplay.getGame().getId();
        this.winner = gameplay.getId();
        this.start_time = gameplay.getStart_time();
        this.data = gameplay.getData();
        this.staus = gameplay.getStatus();
        this.game_name = gameplay.getGame().getName();
    }

    public Player getPlayer_1() {
        return player_1;
    }
    public Player getPlayer_2() {
        return player_2;
    }
    public Player getPlayer_3() {
        return player_3;
    }
    public Player getPlayer_4() {
        return player_4;
    }

    public int getWinner() {
        return this.winner;
    }

    public int getStart_time() {
        return this.start_time;
    }

    public String getData() {
        return this.data;
    }

    public int getStaus() {
        return this.staus;
    }

    public int getGame_id() {
        return game_id;
    }

    public String getGame_name() {
        return game_name;
    }
}
