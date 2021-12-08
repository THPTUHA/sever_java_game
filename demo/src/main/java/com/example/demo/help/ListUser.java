package com.example.demo.help;

import java.util.List;

import com.example.demo.model.User;

public class ListUser {
    private List<User> users;
    private int page_size;

    public ListUser(List<User> users, int page_size) {
        this.users = users;
        this.page_size = page_size;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public int getPage_size() {
        return this.page_size;
    }

}
