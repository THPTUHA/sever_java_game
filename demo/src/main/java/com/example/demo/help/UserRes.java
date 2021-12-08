package com.example.demo.help;

import com.example.demo.model.User;

public class UserRes {
    private String name;
    private long last_login;
    private String email;
    private String role;
    private boolean locked;

    public UserRes(User user) {
        this.name = user.getFirst_name()+" "+user.getLast_name();
        this.last_login = user.getLast_login();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.locked = user.getLocked();
    }

    public String getName() {
        return this.name;
    }

    public long getLast_login() {
        return this.last_login;
    }

    public String getEmail() {
        return this.email;
    }

    public String getRole() {
        return this.role;
    }

    public boolean getLocked() {
        return this.locked;
    }

    public boolean isLocked() {
        return this.locked;
    }

}
