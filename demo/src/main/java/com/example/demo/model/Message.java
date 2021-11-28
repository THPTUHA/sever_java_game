package com.example.demo.model;

public class Message {
    private int user_id;
    private String user_name;
    private String avatar;
    private String message;
    private int status;

    public Message(int user_id,String user_name, String avatar, String message,int status) {
        this.user_id =user_id;
        this.user_name = user_name;
        this.avatar = avatar;
        this.message = message;
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return this.user_name;
    }


    public String getAvatar() {
        return this.avatar;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return status;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    @Override
    public String toString() {
        return "{" +
            " user_name='" + getUser_name() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
    
}   
