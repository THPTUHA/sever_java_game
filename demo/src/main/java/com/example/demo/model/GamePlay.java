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
    @Column(name="start_time")
    private long start_time;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id_1")
    private User user_1;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id_2")
    private User user_2;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id_3")
    private User user_3;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id_4")
    private User user_4;
    @Column(name="point_1")
    private int point_1;
    @Column(name="point_2")
    private int point_2;
    @Column(name="point_3")
    private int point_3;
    @Column(name="point_4")
    private int point_4;
    @Column(name="status")
    private int status;
    @Column(name="winner")
    private int winner;
    @Column(name="data")
    private String data;

    public int getStatus(){return this.status;}
    public void setStatus(int status){ this.status=status;}
    public GamePlay() {}
    public GamePlay( Game game){
        this.game =game;
    }

    public int getId() {
        return this.id;
    }

    public Game getGame() {
        return this.game;
    }

    public User getUser_1() {
        return this.user_1;
    }

    public User getUser_2() {
        return this.user_2;
    }

    public User getUser_3() {
        return this.user_3;
    }

    public User getUser_4() {
        return this.user_4;
    }

    public int getPoint_1() {
        return this.point_1;
    }

    public int getPoint_2() {
        return this.point_2;
    }

    public int getPoint_3() {
        return this.point_3;
    }

    public int getPoint_4() {
        return this.point_4;
    }
   
   public long getStart_time() {
       return start_time;
   }

   public String getData() {
       return data;
   }
    @Override
    public String toString(){
        return "id:"+this.id+"\nuser_1........\n"+this.user_1
        +"\nuser_2........\n"+this.user_2+"\nuser_3........\n"+this.user_3
        +"\nuser_4........\n"+this.user_4;
    }
}
