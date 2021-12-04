package com.example.demo.game.gameConcateWord;

import java.util.ArrayList;

public class CWPlay {
    private ArrayList<PlayerCW> player;
    private int status;
    private Answer answer;
    private int turn;
    private char word;

    public CWPlay(ArrayList<PlayerCW> player, int status,Answer answer) {
        this.player = player;
        this.status = status;
        this.answer = answer;
        if(answer.getStatus() == 1){
            String w = answer.getWord();
            this.word = w.charAt(w.length()-1);
        }
    }

    public CWPlay(ArrayList<PlayerCW> player, int status,Answer answer,int turn) {
        this.player = player;
        this.status = status;
        this.answer = answer;
        this.turn = turn;
        if(answer.getStatus() == 1){
            String w = answer.getWord();
            this.word = w.charAt(w.length()-1);
        }
    }

    public ArrayList<PlayerCW> getPlayer() {
        return this.player;
    }

    public int getStatus() {
        return this.status;
    }

    public Answer getAnswer() {
        return this.answer;
    }

    public int getTurn() {
        return turn;
    }

    public char getWord() {
        return word;
    }
}
