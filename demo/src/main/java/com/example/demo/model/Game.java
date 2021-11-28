package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="game")
public class Game implements Serializable {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="content")
    private String content;
    @Column(name="view")
    private int view;
    @Column(name="release_time")
    private Date release_time;
    @Column(name="status")
    private int status;
    @OneToMany(mappedBy = "game",cascade = CascadeType.ALL)
    private List<GamePlay>gameplay;

    public String getContent() {
        return content;
    }
    public List<GamePlay> getGameplay() {
        return gameplay;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Date getRelease_time() {
        return release_time;
    }
    public int getStatus() {
        return status;
    }
    public int getView() {
        return view;
    }
    
    @Override
    public String toString(){
        return "Game:........"+"\nName:"+this.name;
    }
}
