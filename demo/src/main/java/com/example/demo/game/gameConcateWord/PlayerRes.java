package com.example.demo.game.gameConcateWord;

import java.util.ArrayList;

public class PlayerRes {
    private ArrayList<PlayerCW> player;
    private int status;
    private int turn;

    public PlayerRes(ArrayList<PlayerCW> player, int status,int turn) {
        this.player = player;
        this.status = status;
        this.turn = turn;
    }


    public ArrayList<PlayerCW> getPlayer() {
        return this.player;
    }

    public int getStatus() {
        return this.status;
    }

    public int getTurn() {
        return this.turn;
    }
}
