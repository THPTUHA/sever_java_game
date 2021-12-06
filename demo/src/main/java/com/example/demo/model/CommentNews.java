package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.controller.News.Owner;

@Entity
@Table(name="commentnews")
public class CommentNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="news_id")
    private News news;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
    @Column(name="content")
    private String content;
    @Column(name="since")
    private long since;
    @Column(name="status")
    private int status;

    public CommentNews(){}


    public int getId() {
        return this.id;
    }

    public News pickNews() {
        return this.news;
    }

    public Owner getOwner() {
        return new Owner(this.user);
    }

    public String getContent() {
        return this.content;
    }

    public long getSince() {
        return this.since;
    }

    public int getStatus() {
        return this.status;
    }
    
    
}
