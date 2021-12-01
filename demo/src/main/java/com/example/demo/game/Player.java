package com.example.demo.game;

import com.example.demo.model.User;

public class Player {
    private int user_id;
    private String name;
    private String avatar;
    private int point;
    private long exp;

    public Player(User user,int point) {
        this.user_id = user.getId();
        this.name = user.getFirst_name()+" "+user.getLast_name();
        this.avatar = user.getAvatar();
        this.point = point;
        this.exp = user.getExp();
    }

    public int getUser_id() {
        return this.user_id;
    }

    public String getName() {
        return this.name;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public int getPoint() {
        return this.point;
    }

    public long getExp() {
        return this.exp;
    }
    
}
