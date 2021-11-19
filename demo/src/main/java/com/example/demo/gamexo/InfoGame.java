package com.example.demo.gamexo;

public class InfoGame {
    private int id_match;
    private int turn ;
    private int type;
    private int status;
    
    public InfoGame(int id_match,int turn,int type,int status){
        this.id_match = id_match;
        this.turn = turn;
        this.type = type;
        this.status = status;
    }

    public int getId_match(){return this.id_match;}
    public int getType(){return this.type;}
    public int getTurn(){return this.turn;}
    public int getStatus(){return this.status;}
}
