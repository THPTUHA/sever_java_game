package com.example.demo.game.gameConcateWord;

public class Answer {
    private String word;
    private int type;
    private int status;

    public Answer(String word, int type, int status) {
        this.word = word;
        this.type = type;
        this.status = status;
    }


    public String getWord() {
        return this.word;
    }

    public int getType() {
        return this.type;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}   
