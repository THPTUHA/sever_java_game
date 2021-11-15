package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="title")
    private String title;
    @Column(name="content")
    private String content;
    @Column(name="user_id")
    private int user_id;
    @Column(name="last_update")
    private Date last_update;
    @Column(name="time_create")
    private Date time_create;
    @Column(name="status")
    private int status;
    @Column(name="view")
    private int view;
    @Column(name="like_num")
    private int like_num;
    @Column(name="comment")
    private String comment;
    @Column(name="background_image")
    private String background_image;

    public int getId(){
        return this.id;
    }

    public String getContent(){
        return this.content;
    }

    public Date getLast_update(){
        return this.last_update;
    }

    public Date getTime_create(){
        return this.time_create;
    }

    public int getView(){
        return this.view;
    }

    public int getStatus(){
        return this.status;
    }

    public int getLike_num(){
        return this.like_num;
    }

    public String getComment(){
        return this.comment;
    }

    public String getBackground_image(){
        return this.background_image;
    }
}
