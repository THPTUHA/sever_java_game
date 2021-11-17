package com.example.demo.gamexo;

import com.example.demo.model.User;

public class GameXOPlayer {

    private int id;
    private int type;
    private long exp;
    private long gold;
    private String name;
    private String nick_name;
    
    public GameXOPlayer(){}
    public GameXOPlayer(User user,int type){
       
        this.id=user.getId();
        this.type=type;
        this.name =user.getFirst_name()+" "+user.getLast_name();
        this.exp = user.getExp();
        this.gold = user.getGold();
    }

    public int getId(){return this.id;}
    public int getType(){return this.type;}
    public long getExp(){return this.exp;}
    public long getGold(){return this.gold;}
    public String getName(){return this.name;}
    public String getNick_name(){return this.nick_name;}
    public void setExp(long m){this.exp+=m*50;}
    public void setGold(long m){this.gold+=gold*50;}
}
