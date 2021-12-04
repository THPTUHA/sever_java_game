package com.example.demo.game.gameConcateWord;

import java.util.ArrayList;

public class CWResponse {
    private int match_id;
    private int time ;
    private int turn ;
    private char word;
    private int status;
    private ArrayList<PlayerCW> player;

    public CWResponse(GameCWPlaying gameCWPlaying) {
        this.match_id = gameCWPlaying.getMatch_id();
        this.time = gameCWPlaying.getTime();
        this.turn = gameCWPlaying.getTurn();
        this.word = gameCWPlaying.getWord();
        this.status = gameCWPlaying.getStatus();
        this.player = gameCWPlaying.getPlayer();
    }


    public int getMatch_id() {
        return this.match_id;
    }

    public int getTime() {
        return this.time;
    }

    public int getTurn() {
        return this.turn;
    }

    public char getWord() {
        return this.word;
    }

    public int getStatus() {
        return this.status;
    }

    public ArrayList<PlayerCW> getPlayer() {
        return player;
    }
}
