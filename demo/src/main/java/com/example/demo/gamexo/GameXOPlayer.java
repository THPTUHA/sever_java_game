package com.example.demo.gamexo;

import com.example.demo.model.User;

public class GameXOPlayer {

    private int id;
    private int type;
    private long exp;
    private String name;
    private String nick_name;
    
    public GameXOPlayer(){}
    public GameXOPlayer(User user,int type){
       
        this.id=user.getId();
        this.type=type;
        this.name =user.getFirst_name()+" "+user.getLast_name();
        this.exp = user.getExp();
    }

    public int getId(){return this.id;}
    public int getType(){return this.type;}
    public long getExp(){return this.exp;}
    public String getName(){return this.name;}
    public String getNick_name(){return this.nick_name;}

}
