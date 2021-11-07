package com.example.demo.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="play")
public class GamePlay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="id_game")
    private int id_game;
    @Column(name="player")
    private String player;
    @Column(name="user_num")
    private int user_num;
    @Column(name="winner")
    private int winner;
    @Column(name="status")
    private int status;
    @Column(name="start_time")
    private Date start_time;

    public int getId_game(){return this.id_game;}
    public int getId(){return this.id;}
    public String getPlayer(){return this.player;}
    public int getUser_num(){return this.user_num;}
    public int getWinner(){return this.winner;}
    public int getStatus(){return this.status;}
    public Date getStart_time(){return this.start_time;}

    public void setPlayer(int id_user){ this.player = this.player+"|"+String.format("%d", id_user);}
    public void setStatus(int status){ this.status=status;}
    public GamePlay() {}
    public GamePlay(int id_game,int id_user1){
        this.id_game=id_game;
        this.player=String.format("%d", id_user1);
        this.status=id_user1;
        this.user_num=1;
    }

    // public connectGame(int id_user2){

    // }
    @Override
    public String toString(){
        return "id:"+this.id+"\nid_game:"+this.id_game+"\nplayer:"+this.player+"\nnum_user:"+this.user_num;
    }
}
