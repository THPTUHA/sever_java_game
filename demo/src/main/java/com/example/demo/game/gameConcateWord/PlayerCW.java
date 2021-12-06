package com.example.demo.game.gameConcateWord;

import com.example.demo.game.Player;
import com.example.demo.model.User;

public class PlayerCW extends Player{
    private int type;
    private int status;
    private int blood = 5 ;

    public PlayerCW(User user,int point,int status){
        super(user,point);
        this.status = status;
    }
    public int getType() {
        return type;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getBlood() {
        return this.blood;
    }
    public void setBlood(int blood) {
        this.blood += blood;
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", blood='" + getBlood() + "'" +
            "}";
    }
    
}
