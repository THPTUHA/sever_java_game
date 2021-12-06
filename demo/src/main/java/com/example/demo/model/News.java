package com.example.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.controller.News.Owner;

@Entity
@Table(name="news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="title")
    private String title;
    @Column(name="describes")
    private String describes;
    @Column(name="content")
    private String content;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
    @Column(name="last_update")
    private long last_update;
    @Column(name="time_create")
    private long time_create;
    @Column(name="status")
    private int status;
    @Column(name="view")
    private int view;
    @Column(name="like_num")
    private int like_num;
    @OneToMany(mappedBy = "news",cascade = CascadeType.ALL)
    private List<CommentNews> comment;
    @Column(name="background_image")
    private String background_image;

    public News(){};
    public News(String title,String describes, String background_image,String content, int user_id){
        this.title = title;
        this.describes =describes;
        this.background_image = background_image;
        this.content = content;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescribes() {
        return this.describes;
    }

    public String getContent() {
        return this.content;
    }

    public Owner getOwner() {
        return new Owner(this.user);
    }

    public long getLast_update() {
        return this.last_update;
    }

    public long getTime_create() {
        return this.time_create;
    }

    public int getStatus() {
        return this.status;
    }

    public int getView() {
        return this.view;
    }

    public int getLike_num() {
        return this.like_num;
    }

    public List<CommentNews> getComment() {
        return this.comment;
    }

    public String getBackground_image() {
        return this.background_image;
    }
    

    @Override
    public String toString() {
        return "id: "+this.id+"\nfile:"+this.background_image;
    }
}
