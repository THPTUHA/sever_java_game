package com.example.demo.controller.News;

public class Comment {
    private String content;
    private int status;
    private int news_id;
    private String avatar;
    private String name;

    public String getContent() {
        return this.content;
    }

    public int getStatus() {
        return this.status;
    }
    
    public int getNews_id() {
        return this.news_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "{" +
            " conent='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            ", news_id='" + getNews_id() + "'" +
            "}";
    }

}
