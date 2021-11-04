package com.example.demo.gamexo;

public class GameXOPlayer {
    private int id;
    private int type;
    public GameXOPlayer(int id,int type){
        this.id=id;
        this.type=type;
    }
    public int getId(){return this.id;}
    public int getType(){return this.type;}
}
