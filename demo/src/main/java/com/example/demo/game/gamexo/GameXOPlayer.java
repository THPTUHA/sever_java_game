package com.example.demo.game.gamexo;

import com.example.demo.model.User;

public class GameXOPlayer {

    private int id;
    private int type;
    private long exp;
    private long gold;
    private String name;
    private String nick_name;
    private int status = 1;
    private String avatar;
    private int point = 0;

    public GameXOPlayer(){}
    public GameXOPlayer(User user,int type){
        this.id=user.getId();
        this.type=type;
        this.name =user.getFirst_name()+" "+user.getLast_name();
        this.exp = user.getExp();
        this.gold = user.getGold();
        this.avatar = user.getAvatar();
    }

    public int getId(){return this.id;}
    public int getType(){return this.type;}
    public long getExp(){return this.exp;}
    public long getGold(){return this.gold;}
    public String getName(){return this.name;}
    public String getNick_name(){return this.nick_name;}
    public void setExp(long m){this.exp+= m*50;}
    public void setGold(long m){this.gold+= m*50;}
    public int getStatus(){return this.status;}
    public void setStatus(int status){this.status=status;}
    public int getPoint() {
        return point;
    }
    public void setPoint(int m) {
        this.point += m;
        if(this.point < 0)this.point=0;
    }
    public String getAvatar() {
        return avatar;
    }
    @Override
    public String toString(){
        return this.name+" "+this.exp;
    } 
}
