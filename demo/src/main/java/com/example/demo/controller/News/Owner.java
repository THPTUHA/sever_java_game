package com.example.demo.controller.News;

import com.example.demo.model.User;

public class Owner {
    private String name;
    private String avatar;
    private String role;

    public Owner(User user) {
        this.name = user.getFirst_name()+" "+user.getLast_name();
        this.avatar = user.getAvatar();
        this.role = user.getRole();
    }


    public String getName() {
        return this.name;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getRole() {
        return this.role;
    }

}
