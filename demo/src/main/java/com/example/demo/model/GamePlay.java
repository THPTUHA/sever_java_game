package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="recordusergame")
public class GamePlay implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;
    @ManyToOne
    @JoinColumn(name="match_id")
    private RecordMatch record_match;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
    @Column(name="opponent")
    private String opponent;
    @Column(name="status")
    private int status;

    public int getStatus(){return this.status;}
    public void setStatus(int status){ this.status=status;}
    public GamePlay() {}
    public GamePlay(User user,String opponent, Game game,RecordMatch recordMatch){
        this.user =user;
        this.opponent =opponent;
        this.game =game;
        this.record_match =recordMatch;
    }

   
    public int getId() {
        return id;
    }
    public String getOpponent() {
        return opponent;
    }
    
    public String getData() {
        return record_match.getData();
    }

    public Date getStart_time() {
        return record_match.getStart_time();
    }

    public int getGame_id(){
        return this.game.getId();
    }
    public String getGame_name(){
        return game.getName();
    }
    @Override
    public String toString(){
        return "id:"+this.id+"\noppent:";
    }
}
