package com.example.demo.gamexo;

public class InfoGame {
    private int match_id;
    private int turn ;
    private int type;
    private int status;
    
    public InfoGame(int match_id,int turn,int type,int status){
        this.match_id = match_id;
        this.turn = turn;
        this.type = type;
        this.status = status;
    }

    public int getId_match(){return this.match_id;}
    public int getType(){return this.type;}
    public int getTurn(){return this.turn;}
    public int getStatus(){return this.status;}
}
